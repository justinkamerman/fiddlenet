/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.Set;


public class AttributeSet
{
    private static Logger log = Logger.getLogger (AttributeSet.class.getName());
    private static final String UNKNOWN = new String ("?");
    // Map key->value->Attribute
    private HashMap<Object, HashMap<Object, Attribute>> __keyMap;
    // Map key->Attribute (default)
    private HashMap<Object, Attribute> __defaultMap;


    public AttributeSet () 
    { 
        __keyMap = new HashMap<Object, HashMap<Object, Attribute>> ();  
        __defaultMap = new HashMap<Object, Attribute> ();
    }
  
    
    /**
     * Return attribute with given key and value, if a member of this
     * set. Otherwise, return null.
     */
    public Attribute getAttribute (Object key, Object val)
    {
        Attribute attr = null;
        HashMap<Object, Attribute> valueMap = __keyMap.get (key);
        if ( valueMap != null ) attr = valueMap.get (val);
        return attr;
    }


    public Attribute addAttribute (Object key, Object val)
    {
        log.finest (String.format ("getAttribute (%s, %s)", key.toString(), val.toString())); 
        HashMap<Object, Attribute> valueMap;
        Attribute attr  = null;

        // Replace missing values with defaults.
        if ( UNKNOWN.equals (val) )
        {
            attr = getDefault (key);
            log.fine (String.format ("getAttribute (%s, %s): value missing. Using default %s", 
                                     key.toString(), val.toString(), attr.toString())); 
        }
        else 
        {      
            if ( __keyMap.containsKey (key))
            {
                // We've seen this attribute before. Have we seen this particular value thereof ?
                valueMap = __keyMap.get (key);
                if ( valueMap.containsKey (val) )
                {
                    attr = valueMap.get (val);
                }
                else
                {
                    // Value is unseen, add.
                    attr = new Attribute (key, val);
                    valueMap.put (val, attr);
                    log.finest (String.format ("getAttribute (): added new value %s", attr.toString()));
                }
            }
            else
            {
                // Attribute is unseen, add.
                valueMap = new HashMap<Object,Attribute> ();
                attr = new Attribute (key, val);
                valueMap.put (val, attr);
                __keyMap.put (key, valueMap);
                log.finest (String.format ("getAttribute (): added new attribute %s", attr.toString()));
            }
            attr.incrRef ();
            updateDefault (attr.getKey());
        }

        return attr;
    }


    public int size ()
    {
        return __keyMap.size ();
    }


    public Set<Object> getKeys ()
    {
        return __keyMap.keySet ();
    }


    public Set<Object> getValues (Object key)
    {
        return __keyMap.get (key).keySet();
    }


    /**
     * Remove given attribute from instance set
     */
    public void removeAttribute (Object key)
    {
        __keyMap.remove (key);
    }

    
    /**
     * Return default attribute for given key
     */
    public Attribute getDefault (Object key)
    {
        Attribute defAttr;
        if ( __defaultMap.containsKey (key) )
        {
            defAttr = __defaultMap.get (key);
        }
        else
        {
            defAttr = new Attribute (key, UNKNOWN);
            __defaultMap.put (key, defAttr);
        }
        return defAttr;
    }

    
    /**
     * Update the value of the the default attribute for given key
     */
    public void updateDefault (Object key)
    {
        log.finest ("updateDefault (" + key.toString() + ")");

        // Find most frequently occurring value of this attribute
        int maxRef = 0;
        Attribute def = null;
        HashMap<Object, Attribute> valueMap = __keyMap.get (key);
        
        // Only update default value if we have seen value for this attribute before
        if ( valueMap != null )
        {
            for ( Attribute attr : valueMap.values() )
            {
                log.finest (String.format ("updateDefault (%s): processing attribute %s",
                                           key.toString(),
                                           attr.toString()));
                if ( attr.getRef() > maxRef )
                {
                    maxRef = attr.getRef();
                    def = attr;
                }
            }

            // Update default attribute's value
            getDefault (key).setValue (def.getValue());
        }
    }

    
    public String toString ()
    {
        StringBuffer sb = new StringBuffer ();
        for ( Object key : __keyMap.keySet() )
        {
            HashMap<Object, Attribute> valueMap = __keyMap.get (key);
            for ( Object val : valueMap.keySet() )
            {
                Attribute attr = valueMap.get (val);
                sb.append (String.format("[%s]", attr.toString()));
            }
        }

        return sb.toString ();
    }
}

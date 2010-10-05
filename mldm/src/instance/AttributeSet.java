/**
 * $Id: ClassSet.java 4 2010-09-27 20:14:47Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-09-27 17:14:47 -0300 (Mon, 27 Sep 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package instance;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.Set;


public class AttributeSet
{
    private static Logger log = Logger.getLogger (InstanceSet.class.getName());
    // Map key->value->Attribute
    private HashMap<Object, HashMap<Object, Attribute>> __keyMap;


    public AttributeSet () 
    { 
        __keyMap = new HashMap<Object, HashMap<Object, Attribute>> ();  
    }
  
    
    public Attribute getAttribute (Object key, Object val)
    {
        HashMap<Object, Attribute> valueMap;
        Attribute attr  = null;

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
            }
        }
        else
        {
            // Attribute is unseen, add.
            valueMap = new HashMap<Object,Attribute> ();
            attr = new Attribute (key, val);
            valueMap.put (val, attr);
            __keyMap.put (key, valueMap);
        }

        attr.incrRef ();
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


    public String toString ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.toString();
    }
}

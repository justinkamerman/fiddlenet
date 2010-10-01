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
import java.util.Set;


public class AttributeSet
{
    // Map key->value->Attribute
    private HashMap<Object, HashMap<Object, Attribute>> __keyMap;
    private HashMap<Object, Integer> __occurrence;


    public AttributeSet () 
    { 
        __keyMap = new HashMap<Object, HashMap<Object, Attribute>> ();  
        __occurrence = new HashMap<Object, Integer> ();  
    }
  
    
    public Attribute getAttribute (Object key, Object val)
    {
        HashMap<Object, Attribute> valueMap;
        Attribute attr  = null;

        if ( __keyMap.containsKey (key))
        {
            valueMap = __keyMap.get (key);
        }
        else
        {
            // Attribute is unseen, add.
            valueMap = new HashMap<Object,Attribute> ();
            attr = new Attribute (key, val);
            valueMap.put (val, attr);
            __keyMap.put (key, valueMap);
        }

        incrementOccurrence (attr);
        return attr;
    }


    public int getOccurence (Attribute attr)
    {
        return __occurrence.get (attr);
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

    
    private void incrementOccurrence (Attribute attr)
    {
 
    }
}

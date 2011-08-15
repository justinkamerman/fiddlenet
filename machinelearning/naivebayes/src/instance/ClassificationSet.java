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


public class ClassificationSet implements Iterable<Classification>
{
    private HashMap<Object, Classification> __classifications;
    private HashMap<Object, Integer> __occurrence;


    public ClassificationSet () 
    { 
        __classifications = new HashMap<Object, Classification> ();  
        __occurrence = new HashMap<Object, Integer> ();  
    }
  
    
    public Classification getClassification (Object key)
    {
        Classification clas;

        if ( __classifications.containsKey (key))
        {
            clas = __classifications.get(key);
        }
        else
        {
            // Classification is unseen, add to map.
            clas = new Classification (key);
            __classifications.put (key, clas);
        }

        incrementOccurrence (key);
        return clas;
    }


    public int getOccurence (Classification clas)
    {
        return __occurrence.get (clas.getValue());
    }

    
    /**
     * Iterable<Classification> interface
     */
    public Iterator<Classification> iterator ()
    {
        return __classifications.values().iterator();
    }


    /**
     * Return nunmber of classifications in this set
     */
    public int size ()
    {
        return __classifications.size ();
    }


    public String toString ()
    {
        StringBuffer sb = new StringBuffer ();
        sb.append (String.format ("[size=%d][classifications=[", size()));
        Iterator<Object> iter = __classifications.keySet().iterator();
        while ( iter.hasNext() )
        {
            Object key = iter.next ();
            sb.append (String.format ("[[%s][occurence=%d]]", __classifications.get (key), __occurrence.get(key)));
        }
        sb.append ("]]");
        return sb.toString();
    }

    
    private void incrementOccurrence (Object key)
    {
        if ( __occurrence.containsKey (key) )
        {
            __occurrence.put (key, (__occurrence.get(key) + 1));
        }
        else
        {
            __occurrence.put (key, 1);
        }
    }
}

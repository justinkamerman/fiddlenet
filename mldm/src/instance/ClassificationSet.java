/**
 * $Id: ClassSet.java 4 2010-09-27 20:14:47Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-09-27 17:14:47 -0300 (Mon, 27 Sep 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package instance;

import java.util.HashMap;


public class ClassificationSet 
{
    private HashMap<Object, Classification> __classifications;


    public ClassificationSet () 
    { 
        __classifications = new HashMap ();    
    }
  
    
    public Classification getClassification (Object key)
    {
        if ( __classifications.containsKey (key))
        {
            return __classifications.get (key);
        }
        else
        {
            // Classification is unseen, add to map.
            Classification c = new Classification (key);
            __classifications.put (key, c);
            return c;
        }
    }
}

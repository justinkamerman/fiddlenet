/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

import java.util.Iterator;
import java.util.logging.Logger;


public class Fold
{
    private static Logger log = Logger.getLogger (Fold.class.getName());
    private InstanceSet __trainingSet;
    private InstanceSet __testSet;
    private int __index = 0;

    private Fold() {}
    public Fold (int index, InstanceSet train, InstanceSet test)
    {
        __trainingSet = train;
        __testSet = test;
        __index = index;
    }


    public int getIndex ()
    {
        return __index;
    }

    
    public InstanceSet getTrainingSet ()
    {
        return __trainingSet;
    }


    public InstanceSet getTestSet ()
    {
        return __testSet;
    }

    
    public String toString ()
    {
        return String.format ("[training=%s][test=%s]", 
                              __trainingSet.toString(),
                              __testSet.toString());
    }
}


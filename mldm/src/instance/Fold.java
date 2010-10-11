/**
 * $Id: InstanceSet.java 14 2010-10-10 15:32:16Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-10 12:32:16 -0300 (Sun, 10 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package instance;

import java.util.Iterator;
import java.util.logging.Logger;


public class Fold
{
    private static Logger log = Logger.getLogger (Fold.class.getName());
    private InstanceSet __trainingSet;
    private InstanceSet __testSet;

    private Fold() {}
    public Fold (InstanceSet train, InstanceSet test)
    {
        __trainingSet = train;
        __testSet = test;
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


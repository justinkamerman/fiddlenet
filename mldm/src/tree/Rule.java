 /**
 * $Id: AttributeSet.java 15 2010-10-11 16:16:32Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-11 13:16:32 -0300 (Mon, 11 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;
import instance.Attribute;
import instance.Classification;
import instance.Classifier;
import instance.Instance;
import instance.InstanceSet;


public class Rule extends Classifier
{
    private static Logger log = Logger.getLogger (Rule.class.getName());
    // Preconditions are ordered from top of tree to leaf
    private ArrayList<Attribute> __preconditions;
    private Classification __postCondition;
    private double __accuracy;


    public Rule (Classification postcond)
    {
        __preconditions = new ArrayList<Attribute>();
        __postCondition = postcond;
        __accuracy = 0;
    };


    public Rule (ArrayList<Attribute> precond, Classification postcond) 
    { 
        __preconditions = precond;
        __postCondition = postcond;
    }


    public Classification getPostCondition ()
    {
        return __postCondition;
    }
    

    public double getAccuracy ()
    {
        return __accuracy;
    }


    public void addPrecondition (Attribute precond)
    {
        log.finest ("addPrecondition (" + precond + ")");
        __preconditions.add (0, precond);
    }
 
    
    public void prune (InstanceSet validationSet)
    {
        log.fine ("prune(): pruning rule " + toString());

        // Establish base accuracy
        __accuracy = evaluate (validationSet);
        log.fine ("prune(): base accuracy is " + __accuracy);

        // Repeat until no more pruning is done: 
        //     Loop through all preconditions, calculating accuracy if said rule is eliminated.  
        //     Prune precondition with highest accuracy and update base accuracy if said 
        //       accuracy is no worse than base accuracy
        // 
        boolean pruned = true;
        while (pruned)
        {
            int maxIndex = -1;
            for (int i = 0; i < __preconditions.size(); i++)
            {
                // Remove precondition temporarily to see what-if...
                Attribute precond = __preconditions.remove (i);
                double accuracy = evaluate (validationSet);
                if ( accuracy >= __accuracy )
                {
                    log.fine (String.format("prune(): removing precondition %s improves base accuracy to %f",
                                              precond.toString(), accuracy));
                    __accuracy = accuracy;
                    maxIndex = i;
                }
                else
                {
                    log.fine (String.format("prune(): removing precondition %s does not improve base accuracy",
                                              precond.toString()));
                }
                __preconditions.add (i, precond);
            }

            // If we found a precondition whose removal does not decrease accuracy,
            // permanently remove it and start the process again.
            if (maxIndex != -1)
            {
                log.fine ("prune(): pruning precondition " + __preconditions.get(maxIndex));
                __preconditions.remove (maxIndex);
            }
            else
            {
                pruned = false;
            }
        }
    }


    /**
     * Return postcondition is preconditions apply. Otherwise, return
     * null.
     */
    public Classification classify (Instance inst)
    {
        // If there are no preconditions, return null i.e. no classification
        if ( __preconditions.isEmpty() )
        {
            return null;
        }

        for (Attribute precond : __preconditions )
        {
            Attribute attr = inst.getAttribute (precond.getKey());
            if (! attr.equals (precond) ) return null;
        }

        log.finest (String.format("classify(): rule %s classifies instance %s as %s",
                                  toString(),
                                  inst.toString(),
                                  __postCondition.toString()));
        return __postCondition;
    }

    
    public String toString ()
    {
        StringBuffer sb = new StringBuffer ();
        sb.append ("[accuracy=");
        sb.append (__accuracy);
        sb.append ("][preconditions=[");
        for ( Attribute precond : __preconditions )
        {
            sb. append (precond.toString());
        }
        sb.append ("][postcondition=");
        sb.append (__postCondition.toString());
        sb.append ("]");
        return sb.toString ();
    }
}

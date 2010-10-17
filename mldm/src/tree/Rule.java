 /**
 * $Id: AttributeSet.java 15 2010-10-11 16:16:32Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-11 13:16:32 -0300 (Mon, 11 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package tree;

import java.util.ArrayList;
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


    public Rule (Classification postcond)
    {
        __preconditions = new ArrayList<Attribute>();
        __postCondition = postcond;
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
    

    public void addPrecondition (Attribute precond)
    {
        log.finest ("addPrecondition (" + precond + ")");
        __preconditions.add (0, precond);
    }
 
    
    public void prune (InstanceSet validationSet)
    {
    }


    /**
     * Return postcondition is preconditions apply. Otherwise, return
     * null.
     */
    public Classification classify (Instance inst)
    {
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
        sb.append ("[preconditions=[");
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

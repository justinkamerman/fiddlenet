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
import instance.Classification;
import instance.Classifier;
import instance.Instance;
import instance.InstanceSet;


public class RuleSet extends Classifier
{
    private static Logger log = Logger.getLogger (RuleSet.class.getName());
    private ArrayList<Rule> __rules;

    public RuleSet () 
    { 
        __rules = new ArrayList<Rule> ();
    }

    
    public void addRule (Rule rule)
    {
    }

    
    public void prune (InstanceSet validationSet)
    {
    }


    public Classification classify (Instance inst)
    {
        return null;
    }
}

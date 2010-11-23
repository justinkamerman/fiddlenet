 /**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;
import instance.Classification;
import instance.Classifier;
import instance.Instance;
import instance.InstanceSet;


public class RuleSet extends Classifier implements Comparator<Rule>
{
    private static Logger log = Logger.getLogger (RuleSet.class.getName());
    private ArrayList<Rule> __rules;
    private Classification __defaultClassification;

    public RuleSet () 
    { 
        __rules = new ArrayList<Rule> ();
    }

    
    public void setDefaultClassification (Classification classification)
    {
        __defaultClassification = classification;
    }

    
    public void addRule (Rule rule)
    {
        log.finest ("ruleSet(): adding rule " + rule.toString());
        __rules.add (rule);
    }

    
    public void prune (InstanceSet validationSet)
    {
        for (Rule rule : __rules )
        {
            rule.prune (validationSet);
        }

        // Sort rules by descending accuracy
        Collections.sort (__rules, this);
    }


    public Classification classify (Instance inst)
    {
        Classification clas = __defaultClassification;
        for (Rule rule : __rules )
        {
            Classification ruleClass = rule.classify (inst);
            if (ruleClass != null)
            {
                clas = ruleClass;
                break;
            }
        }
        return clas;
    }

    
    public int compare (Rule a, Rule b)
    {
        if (a.getAccuracy() < b.getAccuracy())
        {
            return 1;
        }
        else if (a.getAccuracy() > b.getAccuracy())
        {
            return -1;
        }
        else return 0;
    }

    
    public String toString ()
    {
        StringBuffer sb = new StringBuffer ();
        sb.append ("[defaultClassification=" + __defaultClassification.toString() + "][rules=[");
        for (Rule rule : __rules )
        {
            sb.append (rule.toString());
        }
        sb.append ("]]");
        return sb.toString();
    }
}
  
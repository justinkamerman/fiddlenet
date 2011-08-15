/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

import java.util.logging.Logger;


public abstract class Classifier
{
    private static Logger log = Logger.getLogger (Classifier.class.getName());

    /**
     * Classify a single instance. Classification of instance not
     * required.
     */
    public abstract Classification classify ( Instance inst );

    
    /**
     * Evaluate an instance set, returning predictive
     * accuracy. Classification of instances is required.
     */
    public double evaluate ( InstanceSet instSet )
    {
        if (instSet.size() == 0)
        {
            return 0;
        }
        else
        {
            double correct = 0;
            for ( Instance inst : instSet )
            {
                Classification classification = classify (inst);
                if ( classification != null && classification.equals (inst.getClassification()) )
                {
                    correct++;
                }
            }
            
            log.finest (String.format("evaluate(): correct = %f; instance set size = %d",
                                      correct, instSet.size()));
            return (correct / (double) instSet.size());
        }
    }
}
/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

public abstract class Classifier
{

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
        double correct = 0;
        for ( Instance inst : instSet )
        {
            Classification classification = classify (inst);
            if ( classification != null && classification.equals (inst.getClassification()) )
            {
                correct++;
            }
        }
        return (correct / (double) instSet.size());
    }
}
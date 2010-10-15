/**
 * $Id: DecisionTree.java 15 2010-10-11 16:16:32Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-11 13:16:32 -0300 (Mon, 11 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
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
            if ( classification.equals (inst.getClassification()) )
            {
                correct++;
            }
        }
        return (correct / (double) instSet.size());
    }
}
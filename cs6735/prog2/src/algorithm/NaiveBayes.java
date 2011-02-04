/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$ 
 */
package algorithm;

import java.util.HashMap;
import java.util.logging.Logger;
import bayes.NaiveBayesClassifier;
import instance.Attribute;
import instance.AttributeSet;
import instance.Classification;
import instance.ClassificationSet;
import instance.InstanceSet;


/**
 * Class NaiveBayes
 */
public class NaiveBayes 
{
    private static Logger log = Logger.getLogger (NaiveBayes.class.getName());


    public NaiveBayes () { };
  

    public NaiveBayesClassifier createClassifier (InstanceSet trainSet, int m)
    {
        log.fine ("creating classifier from training data: " + trainSet.toString());
        NaiveBayesClassifier classifier = new NaiveBayesClassifier ();
        ClassificationSet classSet = trainSet.getClassificationSet ();
        AttributeSet attrSet = trainSet.getAttributeSet ();
        HashMap<Classification, Double> classProbs = classifier.getClassProbs ();
        HashMap<Classification, HashMap<Object, HashMap<Object, Double>>> class2attr 
            = classifier.getAttrProbs ();

        log.fine ("createClassifier(): populating probability matrices.");
        for (Classification clas : classSet)
        {
            // Marginal probability
            double margP = (double) classSet.getOccurence(clas) / (double) trainSet.size();
            classProbs.put (clas, margP);
            log.fine (String.format ("createClassifier(): added probability P(C=%s) = %f", clas, margP));

            // Conditional probabilities
            HashMap<Object, HashMap<Object, Double>> key2val = new HashMap<Object, HashMap<Object, Double>> ();
            InstanceSet subsetClass = trainSet.subset (clas);

            // Use training set's attribute domain, not subset's. This
            // way we won't end up with a sparse probability
            // matrix. Use m-estimate technique to account for small
            // samples.
            for (Object key : trainSet.getAttributeSet().getKeys ())
            {
                double p = 1 / (double) trainSet.getAttributeSet().getValues(key).size();
                HashMap<Object, Double> val2prob = new  HashMap<Object, Double> ();
                for (Object val : attrSet.getValues (key))
                {
                    // Calculate P(A=a|C=c) = (nc + mp)/(n + m) where p = 1/k 
                    InstanceSet subsetAttr = subsetClass.subset (key, val);
                    double condP = (double) (subsetAttr.size() + (m * p)) / (double) (subsetClass.size() + m);
                    val2prob.put (val, condP);
                    log.fine (String.format ("createClassifier(); added probability P(%s=%s|C=%s) = %f",
                                             key.toString(), val.toString(), clas.toString(), condP));
                }

                // Add default probability for attributes that were not seen during training for this classification
                double pp = 1 / (double) (trainSet.getAttributeSet().getValues(key).size() + 1);
                double defaultP = (double) (m * pp) / (double) (subsetClass.size() + m);
                val2prob.put (NaiveBayesClassifier.__DEFAULT, defaultP);
                log.fine (String.format ("createClassifier(); added probability P(%s=DEFAULT|C=%s) = %f",
                                             key.toString(), clas.toString(), defaultP));

                key2val.put (key, val2prob);
            }

            class2attr.put (clas, key2val);
        }

        return classifier;
    }
}

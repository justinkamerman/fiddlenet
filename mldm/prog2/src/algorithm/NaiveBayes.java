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
  

    public NaiveBayesClassifier createClassifier (InstanceSet trainSet)
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
            double probC = (double) classSet.getOccurence(clas) / (double) trainSet.size();
            classProbs.put (clas, probC);
            log.fine (String.format ("createClassifier(): added probability P(C=%s) = %f", clas, probC));

            // Conditional probabilities
            HashMap<Object, HashMap<Object, Double>> key2val = new HashMap<Object, HashMap<Object, Double>> ();
            InstanceSet subsetClass = trainSet.subset (clas);

            // Use training set's attribute domain, not subset's. This
            // way we won't end up with a spare probability
            // matrix. Use m-estimate technique to account for small
            // samples.
            for (Object key : trainSet.getAttributeSet().getKeys ())
            {
                HashMap<Object, Double> val2prob = new  HashMap<Object, Double> ();
                for (Object val : attrSet.getValues (key))
                {
                    // Add probabilites
                    InstanceSet subsetAttr = subsetClass.subset (key, val);
                    double probA = (double) subsetAttr.size() / (double) subsetClass.size(); // use m-estimate here

                    log.fine (String.format ("createClassifier(); added probability P(%s=%s|C=%s) = %f",
                                             key.toString(), val.toString(), clas.toString(), probA));
                }
                
                key2val.put (key, val2prob);
            }

            class2attr.put (clas, key2val);
        }

        return classifier;
    }
}

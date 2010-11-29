/**
 * $Id: DecisionTree.java 29 2010-10-26 13:01:00Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-26 10:01:00 -0300 (Tue, 26 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package bayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import instance.Attribute;
import instance.Classification;
import instance.Classifier;
import instance.Instance;
import instance.InstanceSet;


public class NaiveBayesClassifier extends Classifier
{
    private static Logger log = Logger.getLogger (NaiveBayesClassifier.class.getName());
    public static final Object __DEFAULT = new String ("DEFAULT");

    // Classification => Marginal Probability
    private HashMap<Classification, Double> __classProbs = new HashMap<Classification, Double> ();
    
    // Classification => Attribute Key => Attribute Value => Conditional Probability
    private HashMap<Classification, HashMap<Object, HashMap<Object, Double>>> __attrProbs 
        = new  HashMap<Classification, HashMap<Object, HashMap<Object, Double>>> ();


    public NaiveBayesClassifier () {}


    public HashMap<Classification, Double> getClassProbs ()
    {
        return __classProbs;
    }


    public HashMap<Classification, HashMap<Object, HashMap<Object, Double>>> getAttrProbs ()
    {
        return __attrProbs;
    }


    /**
     * Classify a single instance. Classification of instance not
     * required.
     */
    public Classification classify ( Instance inst )
    {
        double argMax = 0;
        Classification argMaxC = null;
        
        /* for each classification, calculate Vmap candidate value; select classification with highest Vmap */
        for (Classification clas : __classProbs.keySet() )
        {
            double map = __classProbs.get (clas);
            

            // Loop through attributes
            for (Attribute attr : inst)
            {
                log.finest (String.format ("P(%s=%s|C=%s)",
                                           attr.getKey().toString(), 
                                           attr.getValue().toString(), 
                                           clas.toString()));
                
                double condP;
                HashMap<Object, Double> value2prob = __attrProbs.get(clas).get(attr.getKey());

                if (value2prob == null)
                {
                    log.info (">>> instance = " + inst.toString());
                    log.info (">>> class = " + clas.toString());
                    log.info (">>> attribute = " + attr.toString());
                }
                    
                
                if ( value2prob.containsKey (attr.getValue()) )
                {
                    condP = __attrProbs.get(clas).get(attr.getKey()).get(attr.getValue());
                }
                else
                {
                    condP = __attrProbs.get(clas).get(attr.getKey()).get(__DEFAULT);
                }

                map *= condP;
                log.finest (String.format ("P(%s=%s|C=%s) = %f",
                                           attr.getKey().toString(), 
                                           attr.getValue().toString(), 
                                           clas.toString(),
                                           condP));
                
            }


            log.finest (String.format ("classify(): calculated probability for C=%s: %f", clas, map)); 
            if (map > argMax)
            {
                argMax = map;
                argMaxC = clas;
            }
        }

        log.fine (String.format ("classify (%s): %s", inst, argMaxC));
        return argMaxC;
    }
}

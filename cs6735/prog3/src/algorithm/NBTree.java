/**
 * $Id: NBTree.java 62 2010-11-23 13:03:29Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-11-23 09:03:29 -0400 (Tue, 23 Nov 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $ import org.apache.commons.cli.BasicParser;
 */
package algorithm;

import java.util.Stack;
import java.util.logging.Logger;
import algorithm.NaiveBayes;
import bayes.NaiveBayesClassifier;
import instance.Fold;
import instance.Attribute;
import instance.InstanceSet;
import nbtree.NBTreeClassifier;
import nbtree.Edge;
import nbtree.Node;
import util.Evaluation;


/**
 * Class NBTree
 */
public class NBTree 
{
    private static Logger log = Logger.getLogger (NBTree.class.getName());
    private static int __M = 10;
    private static int __K = 5;

    public NBTree () { };
  

    public NBTreeClassifier createNBTree (InstanceSet trainingData)
    {
        log.fine ("createNBTree(): creating NB tree from training data: " + trainingData.toString());
        NBTreeClassifier tree = new NBTreeClassifier ();
        Stack<StackFrame> stack = new Stack<StackFrame> ();

        // Prime stack
        StackFrame rootFrame = new StackFrame (null,  
                                               null, 
                                               trainingData);
        stack.push (rootFrame);
        log.fine ("createNBTreeClassifier(): pushing root stack frame: " + rootFrame.toString());

        // Main loop
        while ( ! stack.empty () )
        {
            StackFrame frame = stack.pop ();
            log.fine ("\tcreateNBTreeClassifier(): popped stack frame: " + frame);
            Node P = frame.getParent();
            Object W = frame.getWeight();
            MaxSplitUtilityResult msur = new MaxSplitUtilityResult ();
            InstanceSet S = frame.getInstanceSet();
            Node A = new Node (NaiveBayes.createClassifier (S));

            // Will the new node be a leaf or a split ?
            //
            // 1. check size of training set to avoid splits of little value
            if ( S.size() < 30 )
            {
                log.fine ("\t\tcreateNBTreeClassifier(): training set has less than 30 instances. Adding leaf node");
            }
            else
            {
                double Un = utility (S);
                msur = maxSplitUtility (S);
                log.fine (String.format("\t\tcreateNBTreeClassifier(): Un = %f; Us = %s", Un, msur));
                
                // 2. check that relative reduction in error >5% to avoid split of little value
                if ( ((msur.getUtility() - Un) / Un) <= 0.01 )
                {
                    log.fine ("\t\tcreateNBTreeClassifier(): split utility gain <5%. Adding leaf node");
                }
                else // Do the split
                {
                    log.fine ("\tcreateNBTreeClassifier(): split utility gain >5%. Splitting on attribute " 
                                + msur.getAttributeKey().toString());
                    
                    A.setKey (msur.getAttributeKey());
                    for (Object val : S.getAttributeSet().getValues (msur.getAttributeKey()))
                    {
                        InstanceSet splitSet = S.subsetAndRemoveAttribute (msur.getAttributeKey(), val);
                        //InstanceSet splitSet = S.subset (msur.getAttributeKey(), val);
                        StackFrame sf = new StackFrame (A, val, splitSet);                                           
                        log.fine ("\t\t\tcreateNBTreeClassifier(): pushing stack frame: " + sf.toString());
                        stack.push (sf);
                    }
                }
            }

            // Add new node to tree
            if ( P != null )
            {
                log.fine (String.format("\tcreateNBTreeClassifier(): adding node %s, with weight %s",
                                          A.toString(),
                                          W.toString()));
                P.addChild (A, W);
            }
            else 
            {
                log.fine (String.format("\tcreateNBTreeClassifier(): adding root node %s",
                                          A.toString()));

                tree.setRoot (A);
            }
        }
            
        log.fine ("\tcreateNBTreeClassifier(): done");
        return tree;
    }
    

    /**
     * Node utility
     */
    private double utility (InstanceSet instSet)
    {
        Evaluation eval = new Evaluation ();
        for (Fold fold : instSet.fold (__K))
        {
            NaiveBayesClassifier classifier= (new NaiveBayes()).createClassifier (fold.getTrainingSet());
            double accuracy = classifier.evaluate (fold.getTestSet());
            eval.addAccuracy (accuracy);
        }
        
        double utility = eval.mean ();
        log.finest ("utility(): utility = " + utility);
        return eval.mean ();
    }


    /**
     * Split utility
     */
    private double splitUtility (InstanceSet instSet, Object attrKey)
    {
        log.finest (String.format("splitUtility (%s)", attrKey.toString()));
        double splitUtility = 0;
        for (Object val : instSet.getAttributeSet().getValues (attrKey))
        {
            InstanceSet splitSet = instSet.subsetAndRemoveAttribute (attrKey, val);
            //InstanceSet splitSet = instSet.subset (attrKey, val);
            double utility = utility (splitSet);
            log.finest (String.format("splitUtility (%s=%s): %f * (%d/%d = %f)", 
                                      attrKey.toString(),
                                      val.toString(), 
                                      utility, splitSet.size(), 
                                      instSet.size(), 
                                      (double) splitSet.size() / (double) instSet.size()));
            splitUtility += utility * (double) splitSet.size() / (double) instSet.size();
        }
        
        return splitUtility;
    }


    /**
     * Split utility
     */
    private MaxSplitUtilityResult maxSplitUtility (InstanceSet instSet)
    {
        log.finest ("maxSplitUtility ()");
        MaxSplitUtilityResult msur = new MaxSplitUtilityResult();
        for ( Object attrKey : instSet.getAttributeSet().getKeys () )
        {
            double splitUtility = splitUtility (instSet, attrKey);
            log.finest (String.format("maxSplitUtility(): Us(%s) = %f", attrKey, splitUtility));

            if  (splitUtility > msur.getUtility())
            {
                msur.setUtility (splitUtility);
                msur.setAttributeKey (attrKey);
            }
        }

        return msur;
    }


    /**
     * Encapsulates an InstanceSet and the Attribute of that set
     * with the the highest information gain.
     */
    private class StackFrame
    {
        public InstanceSet __instanceSet;
        public Node __parent;
        public Node __node;
        public Object __weight;


        public StackFrame (Node parent, Object weight, InstanceSet instSet)
        {
            __parent = parent;
            __weight = weight;
            __instanceSet = instSet;
        }

        public Node getParent () { return __parent; }
        public Object getWeight () { return __weight; }
        public InstanceSet getInstanceSet () { return __instanceSet; }

        public String toString ()
        {
            return String.format ("[parent=%s][weight=%s][instance_set=%s]",
                                  __parent == null ? "null" : __parent.toString(), 
                                  __weight == null ? "null" : __weight.toString(), 
                                  __instanceSet == null ? "null" : __instanceSet.toString());
        }
    }


    /**
     * Encapsulate a max split utility i.e. utility and split atribute
     */
    private class MaxSplitUtilityResult
    {
        private double __utility;
        private Object __attributeKey;

        
        public double getUtility () { return __utility; }
        public void setUtility (double utility) { __utility = utility; }
        public Object getAttributeKey () { return __attributeKey; }
        public void setAttributeKey (Object key) { __attributeKey = key; }
            
        public String toString ()
        {
            return String.format ("[utility=%f][attributeKey=%s]",
                                  __utility,
                                  __attributeKey.toString());
        }
    }
}

/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$ import org.apache.commons.cli.BasicParser;
 */
package algorithm;

import java.util.Stack;
import java.util.logging.Logger;
import tree.DecisionTree;
import tree.Edge;
import tree.Node;
import instance.Attribute;
import instance.InstanceSet;


/**
 * Class ID3
 */
public class ID3 
{
    private static Logger log = Logger.getLogger (ID3.class.getName());


    public ID3 () { };
  

    public DecisionTree createDecisionTree (InstanceSet trainingData)
    {
        log.fine ("creating decision tree from training data: " + trainingData.toString());
        DecisionTree tree = new DecisionTree ();
        Stack<StackFrame> stack = new Stack<StackFrame> ();

        // Prime stack
        StackFrame rootFrame = new StackFrame (null,  null, 
                                               new Node (trainingData.maxInformationGain(), trainingData.getDefaultClassification()),
                                               trainingData);
        stack.push (rootFrame);
        log.finest ("createDecisionTree(): pushing stack frame: " + rootFrame.toString());

        // Main loop
        while ( ! stack.empty () )
        {
            StackFrame frame = stack.pop ();
            log.finest ("createDecisionTree(): popped stack frame: " + frame);
            Node P = frame.getParent();
            Node A = frame.getChild();
            Object W = frame.getWeight();
            InstanceSet S = frame.getInstanceSet();
            log.finest ("createDecisionTree(): processing attribute " + A.toString());

            // Add node to represent attribute
            if ( P != null )
            {
                P.addChild (A, W);
            }
            else 
            {
                tree.setRoot (A);
            }

            for ( Object weight : S.getValues (A.getKey()) )
            {
                log.finest ("\tcreateDecisionTree(): processing value " + weight.toString());
                InstanceSet Sv = S.subset (A.getKey(), weight);
                log.finest (String.format ("\tcreateDecisionTree(): S(%s, %s): %s",
                                           A.toString(), weight.toString(), Sv.toString()));

                // Sv is empty: no training instance to guide our
                // decision, so settle on most probable classification.
                if ( Sv.size() == 0 )
                {
                    // add leaf node
                    log.finest ("\t\tcreateDecisionTree(): Sv is empty; adding leaf."); 
                    A.addChild (new Node(Sv.getDefaultClassification()), weight);
                }
                // Sv elements all have same classification
                else if ( Sv.getClassificationSetSize() == 1 )
                {
                    // add leaf node
                    log.finest ("\t\tcreateDecisionTree(): Sv only has one classification; adding leaf."); 
                    A.addChild (new Node(Sv.getDefaultClassification()), weight);
                }
                // No more attributes and previous cases don't apply:
                // we must have contradictory training instances, so
                // settle on most probable classification.
                else if ( Sv.getAttributeSetSize() == 0 ) 
                {
                    // Add leaf with most probably classification
                    log.finest ("\t\tcreateDecisionTree(): Sv has no more attributes and no obvious classification; adding leaf with most probable classification.");
                    A.addChild (new Node(Sv.getDefaultClassification()), weight);
                }
                else
                {
                    // Push a stack frame for the next attribute
                    StackFrame sf = new StackFrame (A,
                                                    weight,
                                                    new Node (Sv.maxInformationGain()),
                                                    Sv.removeAttribute (A.getKey()));
                    log.finest ("\t\tcreateDecisionTree(): pushing stack frame: " + sf.toString());
                    stack.push (sf);
                   
                }
            }

            
        }
        return tree;
    }
    
    
    /**
     * Encapsulates an InstanceSet and the Attribute of that set
     * with the the highest information gain.
     */
    private class StackFrame
    {
        public InstanceSet __instanceSet;
        public Node __parent;
        public Object __weight;
        public Node __child;


        public StackFrame (Node parent, Object weight, Node child, InstanceSet instSet)
        {
            __parent = parent;
            __weight = weight;
            __child = child;
            __instanceSet = instSet;
        }

        public Node getParent () { return __parent; }
        public Object getWeight () { return __weight; }
        public Node getChild () { return __child; }
        public InstanceSet getInstanceSet () { return __instanceSet; }

        public String toString ()
        {
            return String.format ("[parent=%s][weight=%s][child=%s][instance_set=%s",
                                  __parent == null ? "null" : __parent.toString(), 
                                  __weight == null ? "null" : __weight.toString(), 
                                  __child == null ? "null" : __child.toString(), 
                                  __instanceSet == null ? "null" : __instanceSet.toString());
        }
    }
}

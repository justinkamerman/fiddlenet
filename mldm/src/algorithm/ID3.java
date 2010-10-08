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
        log.fine ("creating decision tree from trainign data: " + trainingData.toString());
        DecisionTree tree = new DecisionTree ();
        Stack<StackFrame> stack = new Stack<StackFrame> ();

        // Prime stack
        stack.push (new StackFrame (null, null, new Node (trainingData.maxInformationGain()), trainingData));

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

            for ( Object v : S.getValues (A.getKey()) )
            {
                log.finest ("\tcreateDecisionTree(): processing value " + v.toString());
                InstanceSet Sv = S.subset (A.getKey(), v);
                log.finest (String.format ("\tcreateDecisionTree(): S(%s, %s): %s", A.toString(), v.toString(), Sv.toString()));

                // Sv is empty
                if ( Sv.size() == 0 )
                {
                    // add leaf node
                    log.finest ("\t\tcreateDecisionTree(): Sv is empty; adding leaf."); 
                    P.addChild (new Node(S.getDefaultClassification()), v);
                }
                // Sv elements all have same classification
                if ( Sv.getClassificationSetSize() == 1 )
                {
                    // add leaf node
                    log.finest ("\t\tcreateDecisionTree(): Sv only has one classification; adding leaf."); 
                    P.addChild (new Node(S.getDefaultClassification()), v);
                }
                else
                {
                    // Push a stack frame for the next attribute
                    StackFrame sf = new StackFrame (A, W, new Node(Sv.maxInformationGain()), Sv.removeAttribute(A.getKey()));
                    stack.push (sf);
                    log.finest ("\t\tcreateDecisionTree(): pushing stack frame: " + sf.toString());
                }
            }

            System.out.println (tree.dot());
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

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
        log.fine (trainingData.toString());
        DecisionTree tree = new DecisionTree ();
        Stack<StackFrame> stack = new Stack<StackFrame> ();
        Node root;

        // Prime stack
        stack.push (new StackFrame (trainingData.maxInformationGain(), trainingData));

        // Main loop
        while ( ! stack.empty () )
        {
            StackFrame frame = stack.pop ();
            Object A = frame.getAttributeKey();
            InstanceSet S = frame.getInstanceSet();
            log.finest ("createDecisionTree(): processing attribute " + A.toString());
            
            for ( Object v : S.getValues (A) )
            {
                log.finest ("createDecisionTree():\t processing value " + v.toString());
                InstanceSet Sv = S.subset (A, v);
                
                // Sv is empty
                if ( Sv.size() == 0 )
                {
                    // add leaf node
                    log.finest ("createDecisionTree():\t\t Sv is empty; adding leaf."); 
                    Node C = Node.createLeaf (S.getDefaultClassification());
                    tree.addEdge (A, C, v);
                }
                // Sv elements all have same classification
                if ( Sv.getClassificationSetSize() == 0 )
                {
                    // add leaf node
                    log.finest ("createDecisionTree():\t\t Sv only has one classification; adding leaf."); 
                    Node C = Node.createLeaf (S.getDefaultClassification());
                    tree.addEdge (A, C, v);
                }
                else
                {
                    // Remove A from Sv and push onto stack
                    stack.push (new StackFrame (Sv.maxInformationGain(), Sv));
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
        public Object __attributeKey;

        public StackFrame (Object attrKey, InstanceSet instSet)
        {
            __attributeKey = attrKey;
            __instanceSet = instSet;
        }

        public Object getAttributeKey () { return __attributeKey; }
        public InstanceSet getInstanceSet () { return __instanceSet; }
    }
}

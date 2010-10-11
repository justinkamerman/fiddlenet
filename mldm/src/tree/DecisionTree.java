/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import instance.Attribute;
import instance.Classification;
import instance.Instance;
import instance.InstanceSet;


public class DecisionTree
{
    private static Logger log = Logger.getLogger (DecisionTree.class.getName());
    private Node __root;

    public DecisionTree () {}

    
    public Node getRoot ()
    {
        return __root;
    }


    public void setRoot (Node root)
    {
        __root = root;
    }
    
    
    public String dot(  )
    {
        StringBuffer sb = new StringBuffer ();
        sb.append ("digraph G {\n");

        Iterator<Edge> iter = __root.iterator();
        while ( iter.hasNext() )
        {
            Edge edge = iter.next();
            log.finest ("dot(): processing edge " + edge.toString());

            // Add child node
            sb.append (String.format ("\t%s  [label=\"%s\", shape=%s];\n", 
                                      edge.getChild().hashCode(),
                                      edge.getChild().toString(),
                                      edge.getChild().isLeaf() ? "ellipse" : "box"));


            // Add edge to child if not root
            if ( edge.getParent() != null )
            {
                sb.append (String.format ("\t%s -> %s [label=\"%s\"];\n", 
                                          edge.getParent().hashCode(),
                                          edge.getChild().hashCode(),
                                          edge.getWeight().toString()));
            }
        }
        sb.append ("}");       
        return sb.toString();
    }


    /**
     * Classify a single instance. Classification of instance not
     * required.
     */
    public Classification classify ( Instance inst )
    {
        Node node = __root;
        log.fine ("classify(): classifying instance " + inst.toString());

        while (! node.isLeaf () )
        {            
            Attribute attr = inst.getAttribute (node.getKey());
            node = node.getChild (attr.getValue());
            log.fine ("classify(): examining attribute " + attr.toString());
        }
        
        Classification classification = new Classification (node.getKey());
        log.fine ("classify(): assigning classification " + classification.toString());
        return classification;
    }

    
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

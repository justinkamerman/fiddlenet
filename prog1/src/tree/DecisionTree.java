/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import instance.Attribute;
import instance.Classification;
import instance.Classifier;
import instance.Instance;
import instance.InstanceSet;


public class DecisionTree extends Classifier
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
        log.finest ("classify(): classifying instance " + inst.toString());

        while ( ! node.isLeaf () )
        {            
            Attribute attr = inst.getAttribute (node.getKey());            
            Node child = node.getChild (attr.getValue());
            log.finest ("classify(): examining attribute " + attr.toString());

            // No path defined for this instance, use default
            // classification for this subtree.
            if ( child == null )
            {
                break;
            }
            else
            {
                node = child;
            }
        }
        
        Classification classification;
        if (node.isLeaf())
        {
            classification = new Classification (node.getKey());
        }
        else
        {
            log.finest ("No path defined for this instance. Using default classification for this subtree.");
            classification = node.getDefaultClassification();
        }
        
        log.fine (String.format ("classify(): classified instance %s as %s", 
                                 inst.toString(), classification.toString()));
        return classification;
    }


    /**
     * Generate post-pruning rule set
     */
    public RuleSet ruleSet ()
    {
        RuleSet ruleSet = new RuleSet ();
        ArrayList<Node> leaves = new ArrayList<Node>();

        // Find all leaves
        Iterator<Edge> iter = __root.iterator();
        while ( iter.hasNext() )
        {
            Edge edge = iter.next();
            if (edge.getChild().isLeaf()) leaves.add (edge.getChild());
        }

        // Find path from each leaf to root
        for ( Node leaf : leaves )
        {
            Rule rule = new Rule (new Classification (leaf.getKey()));
            Node child = leaf;
            Node parent = leaf.getParent ();
            while ( parent != null )
            {
                rule.addPrecondition (new Attribute (parent.getKey(),
                                                     parent.getWeight(child)));
                child = parent;
                parent = parent.getParent ();
            }
            ruleSet.addRule (rule);
        }

        ruleSet.setDefaultClassification (__root.getDefaultClassification());
        return ruleSet;
    }
}

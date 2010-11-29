/**
 * $Id: NBTreeClassifier.java 62 2010-11-23 13:03:29Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-11-23 09:03:29 -0400 (Tue, 23 Nov 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package nbtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import instance.Attribute;
import instance.Classification;
import instance.Classifier;
import instance.Instance;
import instance.InstanceSet;


public class NBTreeClassifier extends Classifier
{
    private static Logger log = Logger.getLogger (NBTreeClassifier.class.getName());
    private Node __root;

    public NBTreeClassifier () {}

    
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
            log.finest ("classify(): visitng node " + node.toString());
            Attribute attr = inst.getAttribute (node.getKey());    
            log.finest ("classify(): examining attribute " + attr.toString());        
            Node child = node.getChild (attr.getValue());

            // No path defined for this instance, use default
            // classification for this subtree.
            if ( child == null )
            {
                log.finest ("No path defined for this instance. Using intermediate Naive Bayes classifier.");
                break;
            }
            else
            {
                // Remove the attribute from the instance as we advance down the tree.
                log.finest ("classify(): removing attribute from instance " + node.getKey());
                inst.removeAttribute (node.getKey());
                node = child;
            }
        }
        
        Classification classification = node.getNaiveBayesClassifier().classify (inst);
        log.fine (String.format ("classify(): classified instance %s as %s", 
                                 inst.toString(), classification.toString()));
        return classification;
    }
}

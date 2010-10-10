/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package tree;

import instance.Classification;
import instance.Instance;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;


public class DecisionTree
{
    private static Logger log = Logger.getLogger (DecisionTree.class.getName());
    private Node __root;

    public DecisionTree () {}
    

    public Classification classify ( Instance inst )
    {
        return null;
    }

    
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
}

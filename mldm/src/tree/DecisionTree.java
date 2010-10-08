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
            sb.append (String.format ("\t%s -> %s [label=\"%s\"];\n", 
                                      edge.getParent() == null ? "null" : edge.getParent().toString(), 
                                      edge.getChild() == null ? "null" : edge.getChild().toString(), 
                                      edge.getWeight() == null ? "null" : edge.getWeight().toString()));
        }
        sb.append ("}");       
        return sb.toString();
    }
}

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


public class DecisionTree
{
    private Node __root;
    private HashMap<Object, Node> __nodes;
    private HashMap<Object, Edge> __edges;

    public DecisionTree () {};
    

    public Classification classify( instance.Instance inst )
    {
        return null;
    }

    
    public void addEdge (Object fromKey, Object toKey, Object value)
    {

    }

    public String dot(  )
    {
        StringBuffer sb = new StringBuffer ();
        sb.append ("digraph G {");
        for ( Edge edge : __edges.values() )
        {
            sb.append (String.format ("\t%s -> %s [label=\"%s\"];", 
                                      edge.getFrom().toString(), 
                                      edge.getTo().toString(), 
                                      edge.getValue().toString()));
        }
        sb.append ("}");       
        return sb.toString();
    }
}

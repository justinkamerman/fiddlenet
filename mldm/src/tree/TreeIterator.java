/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package tree;

import java.util.Iterator;
import java.util.Stack;


public class TreeIterator implements Iterator<Edge>
{
    private Stack<Edge> __stack = new Stack<Edge>();

    private TreeIterator () {}
    public TreeIterator (Node root)
    {
        __stack.push ( new Edge (null, root, null) );
    }

    
    public boolean hasNext ()
    {
        return (__stack.size() != 0);
    }

    
    public Edge next ()
    {
        Edge edge = __stack.pop ();
        for ( Object weight : edge.getChild().getWeights() )
        {
            __stack.push ( new Edge (edge.getChild(), edge.getChild().getChild(weight), weight ));
        }
        return edge;
    }


    public void remove ()
    {
        throw new UnsupportedOperationException ();
    }
}
/**
 * $Id: Node.java 4 2010-09-27 20:14:47Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-09-27 17:14:47 -0300 (Mon, 27 Sep 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
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
        for ( Node child : edge.getChild().getChildren() )
        {
            __stack.push ( new Edge (edge.getChild(), child, edge.getWeight()) );
        }
        return edge;
    }


    public void remove ()
    {
        throw new UnsupportedOperationException ();
    }
}
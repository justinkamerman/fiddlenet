/**
 * $Id: TreeIterator.java 62 2010-11-23 13:03:29Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-11-23 09:03:29 -0400 (Tue, 23 Nov 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package nbtree;

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
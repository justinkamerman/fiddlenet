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
import java.util.Collection;
import instance.Attribute;
import instance.Classification;


public class Node implements Iterable<Edge>
{
    private Object __key;
    // value -> child map
    private HashMap<Object, Node> __children = new HashMap<Object, Node>();

    private Node () {}
    public Node (Object key)
    {
        __key = key;
    }

    
    public Object getKey ()
    {
        return __key;
    }


    public void addChild (Node child, Object value)
    {
        __children.put (value, child);
    }

    public Iterator<Edge> iterator ()
    {
        return new TreeIterator (this);
    }
    

    public Collection<Node> getChildren ()
    {
        return __children.values();
    }


    public String toString ()
    {
        return __key.toString();
    }
}

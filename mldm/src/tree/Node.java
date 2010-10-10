/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import instance.Attribute;
import instance.Classification;


public class Node implements Iterable<Edge>
{
    private static Logger log = Logger.getLogger (Node.class.getName());
    private Object __key;
    // weight -> child map
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


    public void addChild (Node child, Object weight)
    {
        log.finest ("adding child " + child.toString() + " with weight " + weight + " to node " + toString());
        __children.put (weight, child);
    }

    public Iterator<Edge> iterator ()
    {
        return new TreeIterator (this);
    }
   

    public Collection<Node> getChildren ()
    {
        return __children.values();
    }

    
    public Node getChild (Object weight)
    {
        return __children.get (weight);
    }

    
    public boolean isLeaf ()
    {
        return __children.isEmpty();
    }

    
    public Set<Object> getWeights ()
    {
        return __children.keySet();
    }


    public String toString ()
    {
        return __key.toString();
    }
}

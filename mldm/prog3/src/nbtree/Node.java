/**
 * $Id: Node.java 62 2010-11-23 13:03:29Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-11-23 09:03:29 -0400 (Tue, 23 Nov 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package nbtree;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import instance.Attribute;
import instance.Classification;
import nbtree.NaiveBayesClassifier;


public class Node implements Iterable<Edge>
{
    private static Logger log = Logger.getLogger (Node.class.getName());
    private Object __key;
    private Node __parent;
    private NaiveBayesClassifier __nbc;
    // weight -> child map
    private HashMap<Object, Node> __children = new HashMap<Object, Node>();

    private Node () {}
    public Node (Object key)
    {
        __key = key;
    }


    public Node (Object key, Object clas)
    {
        __key = key;
        __defaultClassification = new Classification (clas);
    }

    
    public Object getKey ()
    {
        return __key;
    }

    
    public Node getParent ()
    {
        return __parent;
    }

    
    public void setParent (Node parent)
    {
        __parent = parent;
    }

    
    public NaiveBayesClassifier getNaiveBayesClassifier ()
    {
        return __nbc;
    }


    public void setNaiveBayesClassifier (NaiveBayesClassifier classifier)
    {
        __nbc = classifier;
    }


    public void addChild (Node child, Object weight)
    {
        log.finest ("adding child " + child.toString() 
                    + " with weight " + weight 
                    + " to node " + toString());
        __children.put (weight, child);
        child.setParent (this);
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

    
    /** 
     * Return weight of edge to given child. Returns null if no such
     * child exisits.
     */
    public Object getWeight (Node child)
    {
        for (Object weight : __children.keySet ())
        {
            Node c = getChild(weight);
            if (child.equals (getChild(weight))) return weight;
        }
        return null;
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

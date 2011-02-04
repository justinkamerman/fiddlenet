/**
 * $Id: Edge.java 62 2010-11-23 13:03:29Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-11-23 09:03:29 -0400 (Tue, 23 Nov 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package nbtree;


public class Edge 
{
    Node __parent;
    Node __child;
    Object __weight;


    private Edge () { };
    public Edge (Node parent, Node child, Object weight)
    {
        __parent = parent;
        __child = child;
        __weight = weight;
    }


    public Node getParent ()
    {
        return __parent;
    }


    public Node getChild ()
    {
        return __child;
    }


    public Object getWeight ()
    {
        return __weight;
    }

    
    public String toString ()
    {
        return String.format ("[parent=%s][weight=%s][child=%s]", 
                              __parent == null ? "null" : __parent.toString(),
                              __weight == null ? "null" : __weight.toString(),
                              __child == null ? "null" : __child.toString());
    }
}

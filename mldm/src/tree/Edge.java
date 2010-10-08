/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package tree;


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
}

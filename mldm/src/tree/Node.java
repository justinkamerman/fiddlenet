/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package tree;

import java.util.*;
import instance.Attribute;
import instance.Classification;


public class Node 
{
    private Object __attributeKey;
    private Object __classificationValue;

    private Node () {}

    public static Node createLeaf (Classification clas)
    {
        Node node = new Node ();
        node.setClassification (clas);
        return node;
    }

    public static Node createNode (Attribute attr)
    {
        Node node = new Node ();
        node.setAttribute (attr);
        return node;
    }


    public void setClassification (Classification clas)
    {
        __attributeKey = null;
        __classificationValue = clas.getValue();
    }

    
    public void setAttribute (Attribute attr)
    {
        __classificationValue = null;
        __attributeKey = attr.getKey();
    }


    public String toDOT ()
    {
        if ( __classificationValue != null )
        {
            return __classificationValue.toString();
        }
        else if ( __attributeKey  != null )
        {
            return __attributeKey.toString();
        }
        else return "EMPTY";
    }


    public String toString ()
    {
        return String.format ("[class=%][attribute=%s]", __classificationValue.toString(), __attributeKey.toString());
    }
}

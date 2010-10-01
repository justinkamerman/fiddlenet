/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

import java.util.ArrayList;
import java.util.logging.Logger;


public class Instance 
{
    private static Logger log = Logger.getLogger (Instance.class.getName());
    Classification __classification;
    ArrayList<Attribute> __attrs;


    private Instance () {};
    public Instance (ArrayList attrs)
    {
        __attrs = attrs;
    }


    public Instance (ArrayList<Attribute> attrs, Classification c)
    {
        __attrs = attrs;
        __classification = c;
    }


    /**
     * @return       instance.Attribute
     * @param        index
     */
    public Attribute getAttribute ( int index )
    {
        return null;
    }


    /**
     * @param        index
     * @param        val
     */
    public void setAttribute ( int index, Object val )
    {
    }


    /**
     * @return       boolean
     * @param        obj
     */
    public boolean equals( Object obj )
    {
        return true;
    }

    
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        for ( Attribute attr : __attrs )
        {
            sb.append (String.format("[%s]", attr.toString()));
        }
        
        sb.append (String.format ("[class=%s]", __classification == null ? "" : __classification.toString()));
        return sb.toString();
    }
}

/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

import java.util.logging.Logger;


public class Instance 
{
    private static Logger log = Logger.getLogger (Instance.class.getName());
    Classification __classification;
    Attribute[] __attrs;


    private Instance () {};
    public Instance (Attribute[] attrs)
    {
        __attrs = attrs;
    }


    public Instance (Attribute[] attrs, Classification c)
    {
        __attrs = attrs;
        __classification = c;
    }


    /**
     * @return       instance.Attribute
     * @param        index
     */
    public Attribute getAttribute( int index )
    {
        return null;
    }


    /**
     * @param        index
     * @param        val
     */
    public void setAttribute( int index, instance.Value val )
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
        for (int i = 0; i < __attrs.length; i++)
        {
            sb.append (String.format("[%s]", __attrs[i].toString()));
        }
        
        sb.append (String.format ("[class=%s]", __classification == null ? "" : __classification.toString()));
        return sb.toString();
    }
}

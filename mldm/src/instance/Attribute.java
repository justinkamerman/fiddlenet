/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

import java.util.logging.Logger;


public class Attribute 
{
    private static Logger log = Logger.getLogger (Attribute.class.getName());
    Object __key;;
    Object __value;
  
    private Attribute () {};
    public Attribute (Object key, Object value)
    {
        __key = key;
        __value = value;
    }
  

    /**
     * @return       Object
     */
    public Object getKey ( )
    {
        return __key;
    }


    /**
     * @param        key
     */
    public void setKey( String name )
    {
        __key = name;
    }

    /**
     * @return       Object
     */
    public Object getValue( )
    {
        return __value;
    }


    /**
     * @param        value
     */
    public void setValue ( Object value )
    {
        __value = value;
    }


    /**
     * @return       boolean
     * @param        obj
     */
    public boolean equals (Object  obj )
    {
        if ( obj instanceof Attribute )
        {
            if ( __value.equals (((Attribute) obj).getValue()) && __key.equals (((Attribute) obj).getKey()))
            {
                return true;
            }
        }
        return false;
    }


    public String toString ()
    {
        return String.format ("%s=%s", __key.toString(), __value.toString());
    }
}

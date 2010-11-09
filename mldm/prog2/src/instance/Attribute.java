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
    Object __key;
    Object __value;
    int __refCount = 0;
  
    private Attribute () {};
    public Attribute (Object key, Object value)
    {
        __key = key;
        __value = value;
    }
  

    public Object getKey ()
    {
        return __key;
    }


    public void setKey( String name )
    {
        __key = name;
    }


    public Object getValue( )
    {
        return __value;
    }


    public void setValue ( Object value )
    {
        __value = value;
    }


    public void incrRef () 
    { 
        __refCount++; 
    }

    public void decrRef () { __refCount--; }
    public int getRef () { return __refCount; }
        

    public boolean equals (Object  obj )
    {
        if ( obj instanceof Attribute )
        {
            if ( __value.equals (((Attribute) obj).getValue()) 
                 && __key.equals (((Attribute) obj).getKey()))
            {
                return true;
            }
        }
        return false;
    }


    public String toString ()
    {
        return String.format ("[%s=%s][refCount=%d]", 
                              __key != null ? __key.toString() : "null", 
                              __value != null ? __value.toString() : "null",
                              __refCount);
    }
}

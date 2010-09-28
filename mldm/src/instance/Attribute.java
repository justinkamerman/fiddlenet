/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;


public class Attribute 
{
    String __name;
    Object __value;
  
    private Attribute () {};
    public Attribute (Object value)
    {
        __name = "";
        __value = value;
    }

    public Attribute (String name, Object value)
    {
        __name = name;
        __value = value;
    }
  

    /**
     * @return       String
     */
    public String getName(  )
    {
        return __name;
    }


    /**
     * @param        name
     */
    public void setName( String name )
    {
        __name = name;
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
            if ( __value.equals (((Attribute) obj).getValue()))
            {
                return true;
            }
        }
        
        return false;
    }


    public String toString ()
    {
        return String.format ("%s=%s", __name, __value.toString());
    }
}

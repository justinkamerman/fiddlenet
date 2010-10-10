/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;


public class Classification 
{
    private Object __value;


    private Classification () {};
    public Classification (Object value)
    {
        __value = value;
    }

    
    public Object getValue ()
    {
        return __value;
    }


    public String toString ()
    {
        return __value.toString();
    }
}

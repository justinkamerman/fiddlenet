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

    public boolean equals (Object obj)
    {
        if ( obj instanceof Classification )
        {
            Classification classification = (Classification) obj;
            if ( __value.equals (classification.getValue()) )
            {
                return true;
            }
        }
        return false;
    }
}

/**
 * $Id: Class.java 4 2010-09-27 20:14:47Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-09-27 17:14:47 -0300 (Mon, 27 Sep 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
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

/**
 * $Id: Yytoken.java 109 2011-03-15 02:33:57Z justinkamerman $ 
 *
 * $LastChangedDate: 2011-03-14 23:33:57 -0300 (Mon, 14 Mar 2011) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package inv;


public class Yytoken 
{
    private String __keyword;
    private int __position;

    
    public Yytoken (String keyword, int position) 
    {
        __keyword = keyword;
        __position = position;
    }


    public String getKeyword ()
    {
        return __keyword;
    }

    
    public int getPosition ()
    {
        return __position;
    }


    public String toString() 
    {
        return String.format ("[%s:%d]", __keyword, __position);
    }
}


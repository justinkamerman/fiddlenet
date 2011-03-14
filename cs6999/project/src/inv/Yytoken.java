/**
 * $Id: Main.java 97 2011-03-04 19:55:28Z justinkamerman $ 
 *
 * $LastChangedDate: 2011-03-04 15:55:28 -0400 (Fri, 04 Mar 2011) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package inv;


class Yytoken 
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


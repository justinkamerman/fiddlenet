/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
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


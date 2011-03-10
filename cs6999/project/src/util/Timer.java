/**
 * $Id: TreeIterator.java 14 2010-10-10 15:32:16Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-10 12:32:16 -0300 (Sun, 10 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package util;

public class Timer 
{
    private long start = 0;
    private long end = 0;
    
    
    public void start() 
    {
        start = System.currentTimeMillis();
    }
    
    public void stop() 
    {
        end = System.currentTimeMillis();
    }

    public void reset()
    {
        start = 0;
        end = 0;
    }

    public long duration()
    {
        return (end-start);
    }   
}


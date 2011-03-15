/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
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


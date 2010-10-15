/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */

package util;

import java.util.ArrayList;


public class Evaluation
{
    private ArrayList<Double> __values = new ArrayList<Double>();
    public Evaluation () {};
    
    public void addAccuracy (double accuracy)
    {
        __values.add (accuracy);
    }

    
    public double mean ()
    {
        double mean = 0;
        for (int i = 0; i < __values.size(); mean += __values.get (i++));
        return mean / (double) __values.size ();
    }


    public double standardDeviation ()
    {
        double u = mean ();
        double sqrsum = 0;
        for (int i = 0; i < __values.size(); i++)
        {
            sqrsum += Math.pow ((__values.get (i) - u), 2);
        }

        return Math.sqrt (sqrsum / (double) __values.size ());
    }

    
    public String toString ()
    {
        return String.format ("[tests=%d][mean accuracy=%f][standard deviation=%f]", 
                              __values.size(), mean(), standardDeviation());
    }
}
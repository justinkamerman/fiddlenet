/**
 * $Id: Main.java 101 2011-03-07 21:57:19Z justinkamerman $ 
 *
 * $LastChangedDate: 2011-03-07 16:57:19 -0500 (Mon, 07 Mar 2011) $ 
 * 
 * $LastChangedBy: justinkamerman $
 *
 * Thanks to Michael Gilleland for the Combination Generator:
 * http://www.merriampark.com/comb.htm
 */
package util;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;


public class CombinationGenerator 
{
    private String[] __keywords;
    private int[] __a;
    private int __n;
    private int __r;
    private BigInteger numLeft;
    private BigInteger total;
    

    /**
     * Iterate over all possible r-combinations of the set
     */
    public CombinationGenerator (String[] keywords, int r) 
    {
        int n = keywords.length;
        if (r > n) 
        {
            throw new IllegalArgumentException ();
        }
        if (n < 1) 
        {
            throw new IllegalArgumentException ();
        }
        __keywords = keywords;
        __n = n;
        __r = r;
        __a = new int[__r];
        BigInteger nFact = getFactorial (__n);
        BigInteger rFact = getFactorial (__r);
        BigInteger nminusrFact = getFactorial (__n - __r);
        total = nFact.divide (rFact.multiply (nminusrFact));
        reset ();
    }
    

    public void reset () 
    {
        for (int i = 0; i < __a.length; i++) 
        {
            __a[i] = i;
        }
        numLeft = new BigInteger (total.toString ());
    }


    public BigInteger getNumLeft () 
    {
        return numLeft;
    }


    public boolean hasMore () 
    {
        return numLeft.compareTo (BigInteger.ZERO) == 1;
    }


    private static BigInteger getFactorial (int n) 
    {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) 
        {
            fact = fact.multiply (new BigInteger (Integer.toString (i)));
        }
        return fact;
    }

    
    public Set<String> getNext () 
    {
        if (numLeft.equals (total)) 
        {
            numLeft = numLeft.subtract (BigInteger.ONE);
        }
        else
        {
            int i = __r - 1;
            while (__a[i] == __n - __r + i) 
            {
                i--;
            }
            __a[i] = __a[i] + 1;
            for (int j = i + 1; j < __r; j++) 
            {
                __a[j] = __a[i] + j - i;
            }
            numLeft = numLeft.subtract (BigInteger.ONE);
        }
        
        HashSet<String> combination = new HashSet<String>();
        for (int k = 0; k < __r; k++)
        {
            combination.add (__keywords[__a[k]]);
        }
        return combination;
    }
}

/**
 * $Id: TreeIterator.java 14 2010-10-10 15:32:16Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-10 12:32:16 -0300 (Sun, 10 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


/**
 * Encapsulates a keyword index for a particular document
 */
public class Match
{
    private static Logger log = Logger.getLogger (Match.class.getName());
    private Document __document;

    // Map keywords to a list of positions at which the
    // keywords appear in the document
    private HashMap<String, List<Integer>> __index = new HashMap<String, List<Integer>> (); 

    
    public Match (Document document)
    {
        __document = document;
    }


    public void addOccurrence (Set<String> keywords, int position)
    {
        for (String keyword : keywords)
        {
            List<Integer> positions = __index.get (keyword);
            if ( positions == null )
            {
            positions = new ArrayList<Integer> ();
            __index.put (keyword, positions);
            }
            positions.add (position - keyword.length());
        }
    }
}
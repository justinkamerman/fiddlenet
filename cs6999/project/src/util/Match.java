/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
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


    public int size ()
    {
        return __index.size();
    }


    public Document getDocument ()
    {
        return __document;
    }


    public void addOccurrence (Set<String> keywords, int position)
    {
        for (String keyword : keywords)
        {
            addOccurrence (keyword, position);
        }
    }


    public void addOccurrence (String keyword, int position)
    {
        List<Integer> positions = __index.get (keyword);
        if ( positions == null )
        {
            positions = new ArrayList<Integer> ();
            __index.put (keyword, positions);
        }

        int startPosition = position - keyword.length();
        log.finest ("Found keyword \"" + keyword + "\" at position " + startPosition);
        positions.add (startPosition);
    }


    /**
     * Return occurrences of all keywords matched in the document
     */
    public Set<String> getKeywords ()
    {
        return __index.keySet ();
    }


    /**
     * Return occurrences of given keyword matched in the document
     */
    public List<Integer> getOccurrences (String keyword)
    {
        return __index.get (keyword);
    }

    
    public String toString ()
    {
        StringBuffer sb = new StringBuffer ();
        sb.append ("[document=" + __document.getName() + "]");
        for (String keyword : __index.keySet())
        {
            sb.append ("[" + keyword);
            sb.append (__index.get(keyword).toString());
            sb.append ("]");
        }
        
        return sb.toString();
    }
}
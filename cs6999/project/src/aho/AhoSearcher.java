/**
 * $Id: Main.java 97 2011-03-04 19:55:28Z justinkamerman $ 
 *
 * $LastChangedDate: 2011-03-04 15:55:28 -0400 (Fri, 04 Mar 2011) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package aho;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import util.*;


public class AhoSearcher extends DocumentTask
{
    private static Logger log = Logger.getLogger (AhoSearcher.class.getName());
    private StateMachine __sm;
    private Set<String> __searchWords;


    public AhoSearcher (StateMachine sm, Document document, List<Match> matches, Set<String> searchWords)
    {
        super (document, matches);
        __sm = sm;
        __searchWords = searchWords;
    }

    
    /**
     * Returns true if the given document contains at least one
     * occurrence of each keyword.
     */
    public Match work (Document document)
    {
        log.finest ("Processing document " + document.getName());
        Match match = new Match (document);
        ExecutionContext ctx = new ExecutionContext (__sm);

        try
        {
            InputStreamReader isr = new InputStreamReader (new FileInputStream (document.getFile()), "US-ASCII");
            BufferedReader br = new BufferedReader (isr);
            int b;
            while ((b = br.read()) != -1)
            {
                char a = (char) Character.toLowerCase(b);
                Set<String> output = ctx.goTo (a);

                log.finest (String.format("--%c--> %d", a, ctx.getState().getId()));

                // If there is any output from the target state, check
                // it against the search word list
                if ( output != null )
                {
                    for (String matchword : output)
                    {
                        if (__searchWords.contains (matchword))
                        {
                            match.addOccurrence (matchword, ctx.getPosition());
                            if ( match.size() == __searchWords.size() ) return match;
                        }
                    }
                    
                    // Check if we've found each search word
                    if (__searchWords.size() == 0) return match; 
                }

                br.close ();
            }
        }
        catch (FileNotFoundException ex)
        {
            log.severe ("File not found: " + document.getName() + ": " + ex.toString());
        }
        catch (IOException ex)
        {
            log.severe ("IO Exception reading document: " + ex.toString());
        }

        return null;
    }
}



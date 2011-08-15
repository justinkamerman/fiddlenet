/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package inv;

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


public class InvIndexer extends DocumentTask
{
    private static Logger log = Logger.getLogger (InvIndexer.class.getName());


    public InvIndexer (Document document, List<Match> matches)
    {
        super (document, matches);
    }


    /**
     * Index a document and return match result
     */
    public Match work (Document document)
    {
        log.fine ("Processing document " + document.getName());
        Match match = new Match (document);

        try
        {
            InputStreamReader isr = new InputStreamReader (new FileInputStream (document.getFile()), "US-ASCII");
            BufferedReader br = new BufferedReader (isr);
        
            Scanner scanner = new Scanner (br);
            Yytoken token;
            while ((token = scanner.yylex()) != null)
            {
                match.addOccurrence (token.getKeyword(), token.getPosition());
            } 

            scanner.yyclose();
        }
        catch (FileNotFoundException ex)
        {
            log.severe ("File not found: " + document.getName() + ": " + ex.toString());
        }
        catch (IOException ex)
        {
            log.severe ("IO Exception reading document: " + ex.toString());
        }

        return match;
    }


    /**
     * Create an inverse index from all the document matches
     */
    static public InvIndex createInverseIndex (List<Match> matches)
    {
        InvIndex index = new InvIndex ();

        for (Match match : matches)
        {
            for (String keyword : match.getKeywords())
            {
                index.addDocument (keyword, match.getDocument());
            }            
        }

        return index;
    }
}



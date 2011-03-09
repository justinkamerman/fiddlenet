/**
 * $Id: TreeIterator.java 14 2010-10-10 15:32:16Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-10 12:32:16 -0300 (Sun, 10 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package util;

import java.util.List;
import java.util.logging.Logger;


/**
 * This class is an abstraction of a document indexing task
 */
public abstract class DocumentIndexer implements Runnable
{
    private static Logger log = Logger.getLogger (DocumentIndexer.class.getName()); 
    private Document __document;
    private List<Match> __matches;

    
    private DocumentIndexer() {}
    public DocumentIndexer (Document document, List<Match> matches)
    {
        __document = document;
        __matches = matches;
    }

    
    public Document getDocument ()
    {
        return __document;
    }


    public void run ()
    {
        Match match = index (__document);
        __matches.add (match);
    }

    
    public abstract Match index (Document document);
}
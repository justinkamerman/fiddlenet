/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package inv;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import util.*;


/**
 * Encapsulates mapping of keywords to set of documents in which the
 * keywords appear.
 */
public class InvIndex implements Iterable<String>
{
    private static Logger log = Logger.getLogger (InvIndexer.class.getName());
    private HashMap<String, HashSet<Document>> __termIndex = new HashMap<String, HashSet<Document>>();

    
    /**
     * Add a document to the term index and update document frequency
     */
    public void addDocument (String keyword, Document document)
    {
        HashSet<Document> documentSet = __termIndex.get (keyword);
        if (documentSet == null)
        {
            documentSet = new HashSet<Document>();
            __termIndex.put (keyword, documentSet);
        }
        documentSet.add (document);
    }


    public Iterator<String> iterator ()
    {
        return __termIndex.keySet().iterator();
    }


    public int getKeywordCount ()
    {
        return __termIndex.keySet().size();
    }

    
    public int getDocumentFrequency (String keyword)
    {
        HashSet documents = __termIndex.get (keyword);
        if (documents == null) 
            return 0;
        else
            return documents.size();
    }

    
    public Set<Document> search (Set<String> searchWords)
    {
        Set<Document> results = null;
        for (String searchWord : searchWords)
        {
            // If any of the terms does not exist in our index, we
            // can't get a conjunctive match so return empty set.
            HashSet<Document> docs = __termIndex.get (searchWord);
            if (docs == null) 
                return new HashSet<Document> ();
            else
            {
                if (results == null) // first keyword
                {
                    results = (HashSet<Document>) docs.clone ();
                }
                else // susequent keywords
                {
                    results.retainAll (docs);
                }
            }
        }
        return results;
    }
    

    public String toString ()
    {
        StringBuffer sb = new StringBuffer ();
        
        for (String keyword : __termIndex.keySet())
        {
            sb.append (String.format ("[%s[df=%d][", keyword, getDocumentFrequency(keyword)));
            for (Document document : __termIndex.get(keyword))
            {
                sb.append (document.getName() + ",");
            }
            sb.append ("]]\n");
        }
        return sb.toString ();
    }
}
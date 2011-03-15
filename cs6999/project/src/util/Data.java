/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;


/**
 * This class is an abstraction of user data
 */
public class Data
{
    private static Logger log = Logger.getLogger (Data.class.getName()); 
    private List<String> __keywords = new ArrayList<String>();
    protected List<Document> __documents = new ArrayList<Document>();

    
    private Data() {}
    public Data (String keywordsFile, String documentDir)
    {
        // Get documents
        File dir = new File (documentDir);
        FileFilter fileFilter = new FileFilter () 
        {
            public boolean accept (File file) 
            {
                return file.isFile ();
            }
        };

        File[] files = dir.listFiles (fileFilter);
        if ( files == null )
        {
            log.severe ("Document directory cannot be found: " + documentDir);
        }
        else
        {
            for (int i = 0; i < files.length; i++)
            {
                __documents.add (new Document (i, files[i]));
            }   
        }

        // Get keywords
        try
        {
            BufferedReader in = new BufferedReader (new FileReader(keywordsFile));
            String keyword;
            while ((keyword = in.readLine()) != null)
            {
                __keywords.add (keyword.trim());
            }
        }
        catch (IOException ex)
        {
            throw new NoSuchElementException (ex.getMessage());
        }
    }

    
    public List<String> getKeywords ()
    {
        return __keywords;
    }


    public List<Document> getDocuments ()
    {
        return __documents;
    }
}

    
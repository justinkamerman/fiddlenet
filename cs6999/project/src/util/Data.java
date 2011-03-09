/**
 * $Id: TreeIterator.java 14 2010-10-10 15:32:16Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-10 12:32:16 -0300 (Sun, 10 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;


/**
 * This class is an abstraction of user data
 */
public class Data implements Iterable<DataInputStream>
{
    private static Logger log = Logger.getLogger (Data.class.getName()); 
    private List<String> __keywords;
    protected File[] __documents;

    
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

        __documents = dir.listFiles (fileFilter);
        if (__documents == null)
        {
            log.severe ("Document directory cannot be found: " + documentDir);
        }

        // Get keywords
        
    }

    
    public List<String> getKeywords ()
    {
        return __keywords;
    }

    
    /**
     * Iterable
     */
    public Iterator<DataInputStream> iterator ()
    {
        return new DataIterator (this);
    }


    public class DataIterator implements Iterator<DataInputStream>
    {
        private Data __data;
        private int __index = 0;


        private DataIterator () {}
        public DataIterator (Data data)
        {
            __data = data;
        }

        
        public boolean hasNext ()
        {
            return __index < __data.__documents.length;
        }
        
        
        public DataInputStream next ()
        {
            if (hasNext())
            {
                log.finest ("index: " + __index);
                File document = __data.__documents[__index++];
                log.finest ("next document: " + document.getName());
                try
                {
                    return new DataInputStream (new FileInputStream(document));
                }
                catch (FileNotFoundException ex)
                {
                    throw new NoSuchElementException (ex.getMessage());
                }
            }
            else throw new NoSuchElementException();
        }

        
        public void remove () {}
    }
}

    
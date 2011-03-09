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
import java.util.logging.Logger;


/**
 * This class is an abstraction of user data
 */
public class Document
{
    private static Logger log = Logger.getLogger (Document.class.getName()); 
    private int __id;
    private File __file;

    
    private Document() {}
    public Document (int id, File file)
    {
        __id = id;
        __file = file;
    }

    
    public int getId ()
    {
        return __id;
    }

    
    public String getName () 
    {
        return __file.getName();
    }


    public File getFile ()
    {
        return __file;
    }
}
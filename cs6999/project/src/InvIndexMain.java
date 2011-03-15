/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */


import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


import inv.*;
import util.*;


public class InvIndexMain
{
    private static Logger log = Logger.getLogger (InvIndexMain.class.getName());
    private String __keywordsFile = "data/keywords";
    private String __documentDirectory = "data/documents";
    private int __poolSize = 10; 

    Options __opt;
    CommandLine __cl;


    private InvIndexMain ()
    {
        __opt = new Options(); 
        __opt.addOption("h", false, "Print help");
        __opt.addOption("k", true, "keywords file");
        __opt.addOption("d", true, "document directory");
        __opt.addOption("p", true, "Thread pool size");
    }

     
    public static void main (String[] args)
    {
        new InvIndexMain().run (args);
    }

    
    private void printUsage (String message, int rc)
    {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp (message, __opt);
        System.exit (rc);
    }

    
    private void run (String[] args)
    {
        try
        {        
            __cl = (new BasicParser()).parse (__opt, args); 
            if ( __cl.hasOption ('h') ) printUsage ("help", 0);
            if ( __cl.hasOption ('k') ) __keywordsFile = __cl.getOptionValue ('k');  
            if ( __cl.hasOption ('d') ) __documentDirectory = __cl.getOptionValue ('d');
            if ( __cl.hasOption ('p') ) __poolSize = Integer.parseInt(__cl.getOptionValue ('p'));
        }
        catch (ParseException ex)
        {
            printUsage (ex.getMessage(), 1);
            System.exit (1);
        }

        // Start execution timer
        Timer timer = new Timer ();
        timer.start();

        // Read data
        Data data = new Data (__keywordsFile, __documentDirectory);

        // Scan documents using thread pool
        log.info ("Creating thread pool with " + __poolSize + " threads");
        ExecutorService pool = Executors.newFixedThreadPool(__poolSize);
        List<Match> matches = Collections.synchronizedList (new ArrayList<Match>());
        
        for (Document document : data.getDocuments())
        {
            InvIndexer indexer = new InvIndexer (document, matches);
            pool.execute (indexer);            
        }

        try
        {
            pool.shutdown ();
            pool.awaitTermination (3600, TimeUnit.SECONDS);
        }
        catch (InterruptedException ex)
        {
            log.severe ("Thread pool shutdown interrupted: " + ex);
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        timer.stop ();
        log.fine (String.format("Scanned %d of %d documents in %d miliseconds.",
                                matches.size(),
                                data.getDocuments().size(),
                                timer.duration()));

        // Create inverted index
        timer.reset ();
        timer.start ();
        InvIndex index = InvIndexer.createInverseIndex (matches);
        //log.finest (index.toString());
        timer.stop ();
        log.fine (String.format("Generated inverted index of %d keywords in %d miliseconds.",
                                index.getKeywordCount(),
                                timer.duration()));
        
        // Search
        Set<String> searchwords = new HashSet<String>(Arrays.asList(new String[] { "cruise", "ships", "norwegian" }));
        Set<Document> results = index.search (searchwords);
        log.finest ("Search results:");
        for (Document document : results)
        {
            log.finest ("\t" + document.getName());
        }
        
    }
}





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
    private String __documentDirectory = "data/documents";
    private int __poolSize = 1; 

    Options __opt;
    CommandLine __cl;


    private InvIndexMain ()
    {
        __opt = new Options(); 
        __opt.addOption("h", false, "Print help");
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
        Data data = new Data (__documentDirectory);

        // Scan documents using thread pool
        log.info ("Creating thread pool with " + __poolSize + " threads");
        ExecutorService pool = Executors.newFixedThreadPool(__poolSize);
        List<Match> matches = Collections.synchronizedList (new ArrayList<Match>());
        
        log.info ("Creating inverted index");
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
        log.info (String.format("Scanned %d of %d documents in %d miliseconds.",
                                matches.size(),
                                data.getDocuments().size(),
                                timer.duration()));

        // Create inverted index
        InvIndex index = InvIndexer.createInverseIndex (matches);
        timer.stop ();
        log.info (String.format("Generated inverted index of %d keywords in %d miliseconds.",
                                index.getKeywords().size(),
                                timer.duration()));

        // Search
        String[] searchwords = new String[] { "police", 
                                           "international", 
                                           "record", 
                                           "talk", 
                                           "social", 
                                           "united", 
                                           "happy", 
                                           "press", 
                                           "hours", 
                                           "saturday" };

        for (int i = 1; i <= 10; i++)
        {
            Evaluation eval = new Evaluation ();
            CombinationGenerator cg = new CombinationGenerator (searchwords, i);
            while (cg.hasMore ()) 
            {
                Set<String> terms = cg.getNext();
                timer.reset ();
                timer.start ();
                Set<Document> results = index.search (terms);
                timer.stop ();
                eval.addMetric (timer.duration());
                log.finest (terms.toString() + ": " + results.size());
            }

            log.info (String.format("%d keyword search: %s", i, eval.toString()));
        }
    }
}





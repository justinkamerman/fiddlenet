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


import aho.*;
import util.*;


public class AhoSearchMain
{
    private static Logger log = Logger.getLogger (AhoSearchMain.class.getName());
    private String __keywordsFile = "data/keywords";
    private String __documentDirectory = "data/documents";
    private int __poolSize = 10; 

    Options __opt;
    CommandLine __cl;


    private AhoSearchMain ()
    {
        __opt = new Options(); 
        __opt.addOption("h", false, "Print help");
        __opt.addOption("k", true, "keywords file");
        __opt.addOption("d", true, "document directory");
        __opt.addOption("g", false, "Generate DOT visulaization of state machine");
        __opt.addOption("p", true, "Thread pool size");
    }

     
    public static void main (String[] args)
    {
        new AhoSearchMain().run (args);
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

        // Create state machine
        log.info ("Creating state machine...");
        StateMachine stateMachine = new StateMachine (data.getKeywords());

        // Output DOT
        if ( __cl.hasOption ('g') )
        {
            System.out.println (stateMachine.dot());
        }

        timer.stop ();
        log.info ("Created state machine for " + data.getKeywords().size() 
                  + " keywords in " + timer.duration() + " miliseconds.");
        
        timer.reset ();
        timer.start ();

        // Search
        log.info ("Creating thread pool with " + __poolSize + " threads");
        

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
                ExecutorService pool = Executors.newFixedThreadPool(__poolSize);
                Set<String> terms = cg.getNext();
                timer.reset ();
                timer.start ();

                List<Match> matches = Collections.synchronizedList (new ArrayList<Match>());
                for (Document document : data.getDocuments())
                {
                    AhoSearcher searcher = new AhoSearcher (stateMachine, document, matches, terms);
                    pool.execute (searcher);            
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
                eval.addMetric (timer.duration());
                log.finest (terms.toString() + ": " + timer.duration());
                
                // Debug
                for (Match match : matches)
                {
                    log.finest (terms.toString() + ": " + match.getDocument().getName());
                }
            }

            log.info (String.format("%d keyword search: %s", i, eval.toString()));
        }
    }
}





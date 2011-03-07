/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


import aho.*;


public class Main
{
    private static Logger log = Logger.getLogger (Main.class.getName());
    String __keywordsFile;
    String __documentDirectory;

    Options __opt;
    CommandLine __cl;


    private Main ()
    {
        __opt = new Options(); 
        __opt.addOption("h", false, "Print help");
        __opt.addOption("k", true, "keywords file");
        __opt.addOption("d", true, "document directory");
    }

     
    public static void main (String[] args)
    {
        new Main().run (args);
    }

    
    private void printUsage (String message, int rc)
    {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp (message, __opt);
        System.exit (rc);
    }

    
    private void run (String[] args)
    {
        // try
        // {        
        //     __cl = (new BasicParser()).parse (__opt, args); 
        //     if ( __cl.hasOption ('h') ) printUsage ("help", 0);
        //     if ( __cl.hasOption ('k') ) __keywordsFile = __cl.getOptionValue ('k');  
        //     else printUsage("option -k is required", 1);
        //     if ( __cl.hasOption ('d') ) __documentDirectory = __cl.getOptionValue ('d');
        //     else printUsage("option -d is required", 1);
        // }
        // catch (ParseException ex)
        // {
        //     printUsage (ex.getMessage(), 1);
        //     System.exit (1);
        // }

        log.info ("Creating state machine...");

        List<String> keywords = Arrays.asList("he", "she", "his", "hers");
        StateMachine stateMachine = new StateMachine (keywords);

        System.out.println (stateMachine.dot());
    }
}



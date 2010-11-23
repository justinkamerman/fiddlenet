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
import java.util.logging.Logger;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import algorithm.ID3;
import instance.Fold;
import instance.Instance;
import instance.InstanceSet;
import tree.DecisionTree;
import tree.RuleSet;
import util.Evaluation;


public class ID3Main
{
    private static Logger log = Logger.getLogger (ID3Main.class.getName());
    String __dataFile;
    Options __opt;
    CommandLine __cl;
    InstanceSet __instanceSet;
    String [] __attributeNames;
    int __iterations = 10;
    int __folds = 5;

    private ID3Main ()
    {
        __opt = new Options(); 
        __opt.addOption("h", false, "Print help");
        __opt.addOption("f", true, "Data file");
        __opt.addOption("n", true, "Attribute names (csv)");
        __opt.addOption("d", false, "Generate DOT output");
        __opt.addOption("i", true, "Number of iterations to perform. Default is 10");
        __opt.addOption("o", true, "Number of folds. Default is 5");
    }

     
    public static void main (String[] args)
    {
        new ID3Main().run (args);
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
            if ( __cl.hasOption ('f') ) __dataFile = __cl.getOptionValue ('f');  else printUsage("option -f is required", 1);
            if ( __cl.hasOption ('i') ) __iterations = Integer.parseInt (__cl.getOptionValue ('i'));
            if ( __cl.hasOption ('o') ) __folds = Integer.parseInt (__cl.getOptionValue ('o'));
            if ( __cl.hasOption ('n') ) 
            {
                __attributeNames = __cl.getOptionValue ('n').split (",");
            }
            else 
            {
                __attributeNames = new String[0];
            }
        }
        catch (NumberFormatException ex)
        {
            printUsage (ex.getMessage(), 1);
            System.exit (1);
        }
        catch (ParseException ex)
        {
            printUsage (ex.getMessage(), 1);
            System.exit (1);
        }

        // Create instance set
        try 
        {
            BufferedReader br = new BufferedReader (new FileReader (__dataFile));
            __instanceSet = new InstanceSet (br, __attributeNames);
        }
        catch (Exception ex)
        {
            log.severe ("Error loading data file " + __dataFile + ": " );
            ex.printStackTrace();
            System.exit (1);
        }
        log.info ("Loaded data from file " + __dataFile + ": " + __instanceSet.toString());

        // Fold data and evaluate
        Evaluation eval = new Evaluation ();
        Evaluation prunedEval = new Evaluation ();
        for (int i = 1; i <= __iterations; i++)
        {
            log.info ("Starting iteration " + i + " of 10");
            for (Fold fold :__instanceSet.fold (__folds))
            {
                DecisionTree decisionTree = (new ID3()).createDecisionTree (fold.getTrainingSet());
                double accuracy = decisionTree.evaluate (fold.getTestSet());
                eval.addAccuracy (accuracy);

                RuleSet ruleSet = decisionTree.ruleSet();
                ruleSet.prune (fold.getTrainingSet());
                double prunedAccuracy = ruleSet.evaluate (fold.getTestSet());
                prunedEval.addAccuracy (prunedAccuracy);
                
                log.info (String.format ("Processed fold %d: training set size = %d; test set size = %d; accuracy = %f; pruned_accuracy = %f",
                                         fold.getIndex(),
                                         fold.getTrainingSet().size(),
                                         fold.getTestSet().size(),
                                         accuracy,
                                         prunedAccuracy));

                // Dump first decision tree DOT to stdout
                if ( __cl.hasOption ('d') && i == 1 && fold.getIndex() == 0) System.out.println (decisionTree.dot());
            }
        }
        
        log.info ("\n\nTest Results\n\nID3:\t\t" + eval.toString() + "\nPost-pruned:\t" + prunedEval.toString());
    }
}



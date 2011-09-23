import java.io.IOException;
import java.io.StringWriter;
import java.util.Map.Entry;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import io.TermDocIdWritable;


public class Indexer extends Configured implements Tool
{
    Options __opt;
    CommandLine __cl;


    private Indexer ()
    {
        __opt = new Options(); 
        __opt.addOption("h", false, "Print help");
        __opt.addOption("i", true, "Input file");
        __opt.addOption("o", true, "Output directory");
        __opt.addOption("c", false, "Print configuration");
    }


    private void printUsage (String message, int rc)
    {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp (message, __opt);
        System.exit (rc);
    }

    
    private void printConfig () throws IOException
    {
        StringWriter out = new StringWriter();
        Configuration conf = getConf();
        for (Entry<String, String> entry: conf)
        {
            System.out.printf ("%s=%s\n", entry.getKey(), entry.getValue());
        }
        System.exit (0);
    }


    public int run (String[] args) throws Exception
    {
        String inputPath = "input";
        String outputPath = "output";

        try
        {        
            __cl = (new BasicParser()).parse (__opt, args); 
            if ( __cl.hasOption ('h') ) printUsage ("help", 0);
            if ( __cl.hasOption ('i') ) inputPath = __cl.getOptionValue ('i');  
            if ( __cl.hasOption ('o') ) outputPath = __cl.getOptionValue ('o');
            if ( __cl.hasOption ('c') ) printConfig ();
        }
        catch (ParseException ex)
        {
            printUsage (ex.getMessage(), 1);
            System.exit (1);
        }
        
        Job job = new Job ();
        job.setJarByClass (Indexer.class);
        
        FileInputFormat.addInputPath (job, new Path (inputPath));
        FileOutputFormat.setOutputPath (job, new Path (outputPath));
        
        job.setMapperClass (IndexerMapper.class);
        job.setReducerClass (IndexerReducer.class);
        
        job.setOutputKeyClass (TermDocIdWritable.class);
        job.setOutputValueClass (IntWritable.class);
        
        return (job.waitForCompletion (true) ? 0 : 1);
    }
    
    
    public static void main (String[] args) throws Exception
    {
        int exitCode = ToolRunner.run (new Indexer(), args);
        System.exit (exitCode);
    }
}
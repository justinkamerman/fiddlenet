import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import io.TermDocIdWritable;


public class Indexer
{
    public static void main (String[] args) throws Exception
    {
        if (args.length != 2)
        {
            System.err.println ("Usage: Indexer <input_path> <output_path>");
            System.exit (-1);
        }
        
        Job job = new Job ();
        job.setJarByClass (Indexer.class);
        
        FileInputFormat.addInputPath (job, new Path (args[0]));
        FileOutputFormat.setOutputPath (job, new Path (args[1]));
        
        job.setMapperClass (IndexerMapper.class);
        job.setReducerClass (IndexerReducer.class);
        
        job.setOutputKeyClass (TermDocIdWritable.class);
        job.setOutputValueClass (IntWritable.class);

        System.exit (job.waitForCompletion (true) ? 0 : 1);
    }
}
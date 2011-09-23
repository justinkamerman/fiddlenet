import java.io.IOException;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import io.PostingWritable;
import io.TermDocIdWritable;


public class IndexerReducer 
    extends Reducer<TermDocIdWritable, IntWritable, Text, Iterable<PostingWritable>>
{
    private static Log log = LogFactory.getLog (IndexerReducer.class);
    private Vector<PostingWritable> __postings;
    private Text __prevTerm;

    
    public void setup (Context context)
    {
        __prevTerm = new Text();
        __postings = new Vector<PostingWritable>();
    }


    public void reduce (TermDocIdWritable termDocId, Iterable<IntWritable> tfs, Context context)
        throws IOException, InterruptedException
    {
        Text term = termDocId.getTerm();
        LongWritable docId = termDocId.getDocumentId();
        IntWritable termCount = tfs.iterator().next();

        if ((! term.equals(__prevTerm)) && (__prevTerm != null))
        {
            log.debug ("emitting term " + __prevTerm);
            emitPostings (__prevTerm, context);
        }
        
        addPosting (docId, termCount);
        __prevTerm.set(term);
    }

    
    private void emitPostings (Text term, Context context) 
        throws IOException, InterruptedException
    {
        context.write(term, __postings);
        __postings.clear();
    }

    private void addPosting (LongWritable docId, IntWritable termCount)
    {
        __postings.add (new PostingWritable (new LongWritable(docId.get()), 
                                             new IntWritable(termCount.get())));
    }
}
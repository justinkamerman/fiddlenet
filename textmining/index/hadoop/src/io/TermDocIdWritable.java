package io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;


public class TermDocIdWritable implements WritableComparable<TermDocIdWritable>
{
    private Text __term;
    private LongWritable __documentId;
    
    
    public TermDocIdWritable ()
    {
        __term = new Text ("");
        __documentId = new LongWritable (0);
    }

    public TermDocIdWritable (String term, long documentId)
    {
        __term = new Text (term);
        __documentId = new LongWritable (documentId);
    }

    public TermDocIdWritable (Text term, LongWritable documentId)
    {
        __term = term;
        __documentId = documentId;
    }

    public Text getTerm ()
    {
        return __term;
    }

    public LongWritable getDocumentId ()
    {
        return __documentId;
    }

    @Override
    public void write (DataOutput out) throws IOException
    {
        __term.write (out);
        __documentId.write (out);
    }

    @Override
    public void readFields (DataInput in) throws IOException
    {
        __term.readFields (in);
        __documentId.readFields (in);
    }

    public static TermDocIdWritable read (DataInput in) throws IOException
    {
        TermDocIdWritable tuple = new TermDocIdWritable ();
        tuple.readFields (in);
        return tuple;
    }   

    @Override
    public int hashCode ()
    {
        return (__term.hashCode() ^ (__documentId.hashCode() * 163));
    }

    @Override
    public boolean equals (Object o)
    {
        if (o instanceof TermDocIdWritable)
        {
            TermDocIdWritable tuple = (TermDocIdWritable) o;
            return (__documentId.equals(tuple.getDocumentId())
                    && __term.equals(tuple.getTerm()));
        }
        
        return false;
    }

    @Override
    public String toString ()
    {
        return String.format ("%s, %d", __term, __documentId.get());
    }

    @Override
    public int compareTo (TermDocIdWritable tuple)
    {
        int cmp = __term.compareTo (tuple.getTerm());
        if (cmp != 0)
        {
            return cmp;
        }
        
        return __documentId.compareTo(tuple.getDocumentId());
    }
}

    
    
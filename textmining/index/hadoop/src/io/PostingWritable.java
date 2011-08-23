package io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.WritableComparable;


public class PostingWritable implements WritableComparable<PostingWritable>
{
    private LongWritable __documentId;
    private IntWritable __termCount;

    
    public PostingWritable ()
    {
        __documentId = null;
        __termCount = null;
    }

    public PostingWritable (LongWritable documentId, IntWritable termCount)
    {
        __documentId = documentId;
        __termCount = termCount;
    }

    public LongWritable getDocumentId ()
    {
        return __documentId;
    }


    public IntWritable getTermCount ()
    {
        return __termCount;
    }


    @Override
    public void write (DataOutput out) throws IOException
    {
        __documentId.write(out);
        __termCount.write(out);
    }

    @Override
    public void readFields (DataInput in) throws IOException
    {
        __documentId.readFields(in);
        __termCount.readFields(in);
    }
    

    public static PostingWritable read (DataInput in) throws IOException
    {
        PostingWritable posting = new PostingWritable ();
        posting.readFields (in);
        return posting;
    }   


    @Override
    public int hashCode ()
    {
        return ((__documentId.hashCode() * 163) ^ __termCount.hashCode());
    }

    @Override
    public boolean equals (Object o)
    {
        if (o instanceof PostingWritable)
        {
            PostingWritable tuple = (PostingWritable) o;
            return (__documentId.equals(tuple.getDocumentId())
                    && __termCount.equals(tuple.getTermCount()));
        }
        
        return false;
    }

    @Override
    public String toString ()
    {
        return String.format ("(%d, %d)", __documentId.get(), __termCount.get());
    }

    @Override
    public int compareTo (PostingWritable posting)
    {
        int cmp = __documentId.compareTo (posting.getDocumentId());
        if (cmp != 0)
        {
            return cmp;
        }
        
        return __termCount.compareTo (posting.getTermCount());
    }
}

    
    
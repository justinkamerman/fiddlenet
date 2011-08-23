import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import inv.Scanner;
import inv.Yytoken;
import io.TermDocIdWritable;
import org.tartarus.snowball.SnowballStemmer;


public class IndexerMapper 
    extends Mapper <LongWritable, Text, TermDocIdWritable, IntWritable>
{
    private static Log log = LogFactory.getLog (IndexerMapper.class);
    private static String __stemLanguage = "english";
    private SnowballStemmer __stemmer;

    
    protected void setup (Context context)
    {
        try 
        {
            Class stemClass = 
                Class.forName("org.tartarus.snowball.ext." + __stemLanguage + "Stemmer");
            __stemmer = (SnowballStemmer) stemClass.newInstance();
        }
        catch (Exception ex)
        {
            System.err.println ("Cannot load stemmer class: " + ex.getMessage());
            __stemmer = null;
        }
    }


    public void map (LongWritable offset,
                     Text text,
                     Context context) throws IOException, InterruptedException
    {
        HashMap<String, Integer> termCountMap = new HashMap<String, Integer> ();
        Scanner scanner = null;
        try
        {
            scanner = new Scanner (new StringReader (text.toString()));
            Yytoken token;
            while ((token = scanner.yylex()) != null)
            {
                String keyword = token.getKeyword();
                int position = token.getPosition();

                // Stem keyword
                if (__stemmer != null)
                {
                    __stemmer.setCurrent(keyword);
                    __stemmer.stem();
                    keyword = __stemmer.getCurrent();
                }
                
                Integer previousTermCount = termCountMap.get (keyword);
                termCountMap.put (keyword, previousTermCount == null ? 1 : ++previousTermCount);
            } 
        }
        catch (IOException ex)
        {
            System.err.println ("IOException scanning document: " + ex.getMessage());
        }
        finally
        {
            if (scanner != null) scanner.yyclose();

            // Emit key-value pair: ((term, documentId), termCount)
            Iterator<Map.Entry<String, Integer>> iterator = termCountMap.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry<String, Integer> pair = iterator.next();
                context.write (new TermDocIdWritable (pair.getKey(), offset.get()),
                               new IntWritable(pair.getValue().intValue()));
            }
        }
    }
}
/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class InstanceSet 
{
    private static Logger log = Logger.getLogger (InstanceSet.class.getName());
    private ArrayList __instances;
    private String[] __attributeNames;
    private ClassificationSet __classSet;

    private InstanceSet () {};
    public InstanceSet (BufferedReader br, String[] attributeNames) throws IOException
    {
        __instances = new ArrayList ();
        __classSet = new ClassificationSet ();
        __attributeNames = new String[0];

        if ( attributeNames != null ) __attributeNames = attributeNames;
       
        // Parse data
        String line;
        while (( line = br.readLine() ) != null )
        {
            StringTokenizer st = new StringTokenizer (line, ",");
            Attribute[] attrs = new Attribute [st.countTokens() - 1];
            for (int i = 0; i < attrs.length; i++)
            {
                // Create attribute, assigning attribute name if we have it
                attrs [i] = new Attribute ( i < __attributeNames.length ? __attributeNames[i] : "", st.nextToken());
            }
            Classification c = __classSet.getClassification (st.nextToken());
            Instance inst = new Instance (attrs, c);
            __instances.add (inst);
            log.finer ("Added instance: " + inst.toString());
        }
    }


    public int size ()
    {
        return __instances.size ();
    }


    /**
     * @return       long
     */
    public double entropy(  )
    {
        log.fine ("size => " + this.size());

        double entropy = 0;
        for ( Classification clas : __classSet )
        {
            log.fine ("occurence => " +__classSet.getOccurence (clas));
            double probability = (double) __classSet.getOccurence (clas) /  (double) this.size();
            
            double ei = (probability * Math.log (probability) / Math.log (2));
            entropy = entropy - ei;

            log.fine (probability + ", " + ei + ", " + entropy);
        }

        return entropy;
    }


    /**
     * @return       long
     * @param        attr
     */
    public long informationGain( instance.Attribute attr )
    {
        return 0;
    }


    /**
     * @return       instance.Attribute
     */
    public Attribute maxInformationGain(  )
    {
        return null;
    }


    /**
     * @return       InstanceSet
     * @param        attr
     * @param        val
     */
    public instance.InstanceSet subset( Attribute attr, Value val )
    {
        return new InstanceSet ();
    }


    /**
     * @return       InstanceSet
     */
    public InstanceSet fold(  )
    {
        return null;
    }


    /**
     * @param        inst
     */
    public void addInstance( Instance inst )
    {
    }


    /**
     * @param        inst
     */
    public void removeInstance( Instance inst )
    {
    }


    public String toString ()
    {
        StringBuffer sb = new StringBuffer ();
        sb.append ( String.format ("[size=%d][entropy=%f][classificationSet=[%s]][attributes=[", 
                                   size(), Double.valueOf (entropy()), __classSet) );
        for (int i = 0; i < __attributeNames.length; sb.append (String.format("[%s]", __attributeNames[i++])));
        sb.append ("]]");
        return sb.toString ();
    }
}

/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class InstanceSet implements Iterable<Instance>
{
    private static Logger log = Logger.getLogger (InstanceSet.class.getName());
    private ArrayList<Instance> __instances;
    private AttributeSet __attrSet;
    private ClassificationSet __classSet;

    private InstanceSet () {};
    public InstanceSet (BufferedReader br, String[] attributeNames) throws IOException
    {
        __instances = new ArrayList<Instance>();
        __classSet = new ClassificationSet ();
        __attrSet = new AttributeSet ();

        // Parse data
        String line;
        while (( line = br.readLine() ) != null )
        {
            StringTokenizer st = new StringTokenizer (line, ",");
            ArrayList<Attribute> attrs = new ArrayList<Attribute>();
            int attrCount = st.countTokens() - 1;
            for (int i = 0; i < attrCount; i++)
            {
                // Create attribute, assigning attribute name if we have it
                attrs.add (__attrSet.getAttribute ( i < attributeNames.length ? attributeNames[i] : "", st.nextToken()));
            }
            Classification c = __classSet.getClassification (st.nextToken());
            Instance inst = new Instance (attrs, c);
            __instances.add (inst);
            log.finest ("Added instance: " + inst.toString());
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
        double entropy = 0;
        for ( Classification clas : __classSet )
        {
            double probability = (double) __classSet.getOccurence (clas) /  (double) this.size();
            double ei = (probability * Math.log (probability) / Math.log (2));
            entropy = entropy - ei;
        }

        return entropy;
    }


    /**
     * @return       long
     * @param        attr
     */
    public long informationGain ( int attrIndex )
    {
        /*
          foreach value v of attribute[i]
                Siv = subset (i, v);
                return S.entropy * |Siv|/|size()| * Siv.entropy()
        */

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
     * Return subset of this instance set for which given attribute has given value.
     *
     * @return       InstanceSet
     * @param        attrIndex
     * @param        val
     */
    public InstanceSet subset ( int attrIndex, Object val )
    {
        InstanceSet subset = new InstanceSet ();
        for ( Instance inst : __instances )
        {
            // 
        }
        return subset;
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


    public Iterator<Instance> iterator ()
    {
        return __instances.iterator ();
    }


    public String toString ()
    {
        StringBuffer sb = new StringBuffer ();
        sb.append ( String.format ("[size=%d][entropy=%f][classificationSet=[%s]][attributes=[", 
                                   size(), Double.valueOf (entropy()), __classSet) );
        for ( Object key : __attrSet.getKeys() )
        {
            sb.append (String.format("[%s]", key.toString()));
        }
        sb.append ("]]");
        return sb.toString ();
    }
}

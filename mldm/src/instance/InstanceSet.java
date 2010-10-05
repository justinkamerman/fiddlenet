/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

import java.util.ArrayList;
import java.util.HashMap;
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


    private InstanceSet () 
    {
        __instances = new ArrayList<Instance>();
        __classSet = new ClassificationSet ();
        __attrSet = new AttributeSet ();
    }


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
     * @param       key  identifies attribute
     */
    public long informationGain ( Object key )
    {
        for ( Object value : __attrSet.getValues (key) )
        {
            log.fine ("\t" + __attrSet.getAttribute(key, value).toString());
            InstanceSet subset = subset (key, value);
            log.fine ("\t\t" + subset.toString());
        }
/*
                Siv = subset (i, v);
                return S.entropy * |Siv|/|size()| * Siv.entropy()
        */

        return 0;
    }


    /**
     * @return       instance.Attribute
     */
    public Attribute maxInformationGain ()
    {
        for ( Object key : __attrSet.getKeys () )
        {
            log.fine (String.format ("informationGain(%s) => %d", key.toString(), informationGain (key)));
        }

        return null;
    }


    public InstanceSet subset ( Object key, Object val )
    {
        InstanceSet subset = new InstanceSet ();
        for ( Instance inst : __instances )
        {
            if ( inst.getAttribute(key).getValue().equals(val) )
            {
                subset.addInstance (inst);
                log.finest (String.format ("(%s:%s) adding instance to subset %s", key, val, subset));
            }
        }
        return subset;
    }


    /**
     * Deep copy given instance and add to set.
     */
    public void addInstance (Instance inst)
    {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        Iterator<Attribute> iter = inst.getAttributeIterator ();
        while ( iter.hasNext() )
        {
            Attribute attr = iter.next ();
            attrs.add ( __attrSet.getAttribute ( attr.getKey(), attr.getValue() ) );
        }

        Classification c = __classSet.getClassification (inst.getClassification().getValue());
        __instances.add (new Instance (attrs, c));
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

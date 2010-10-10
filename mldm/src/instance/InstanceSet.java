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
import java.util.Set;
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
                //attrs.add (__attrSet.getAttribute ( i < attributeNames.length ? attributeNames[i] : "", st.nextToken()));
                attrs.add ( new Attribute ( i < attributeNames.length ? attributeNames[i] : "", st.nextToken() ) );
            }

            addInstance (new Instance (attrs, new Classification (st.nextToken())));
        }
    }


    public int size ()
    {
        return __instances.size ();
    }


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


    public double informationGain ( Object key )
    {
        log.finest ("Information gain for attribute " + key.toString());
        double infoGain = this.entropy ();
        for ( Object value : __attrSet.getValues (key) )
        {
            InstanceSet subset = subset (key, value);
            infoGain -= ( (double) subset.size() / (double) this.size() ) * subset.entropy ();
         }

        log.fine (String.format ("informationGain(%s)=%f", key.toString(), infoGain));
        return infoGain;
    }


    /**
     * Return key of attribute with highest information gain
     */
    public Object maxInformationGain ()
    {
        double maxGain = 0;
        Object maxGainAttrKey = null;
        for ( Object key : __attrSet.getKeys () )
        {
            double infoGain = informationGain (key);
            if ( (infoGain > maxGain) || maxGainAttrKey == null ) 
            {
                maxGain = infoGain;
                maxGainAttrKey = key;
            }
        }

        log.fine (String.format ("maxInformationGain()= (%s, %f)", maxGainAttrKey.toString(), maxGain)); 
        return maxGainAttrKey;
    }


    /**
     * Return the most probable classification in this instance set.
     */
    public Object getDefaultClassification ()
    {
        return __classSet.getMaxOccurence().getValue();
    }


    /**
     * Return the classification set size for this instance set.
     */
    public int getClassificationSetSize ()
    {
        return __classSet.size();
    }


    /**
     * Return the attribute set size for this instance set.
     */
    public int getAttributeSetSize ()
    {
        return __attrSet.size();
    }


    /**
     * Remove given attribute from instance set
     */
    public InstanceSet removeAttribute (Object key)
    {
        __attrSet.removeAttribute (key);
        return this;
    }


    /**
     * Create a subset of this instance set where all instances given value for given key.
     * The given attribute will be eliminated from all instances in the set.
     */
    public InstanceSet subset ( Object key, Object val )
    {
        InstanceSet subset = new InstanceSet ();
        for ( Instance inst : __instances )
        {
            if ( inst.getAttribute(key).getValue().equals(val) )
            {
                subset.addInstance (inst, key);
            }
        }

        return subset;
    }


    /**
     * Deep copy given instance and add to set.
     */
    public void addInstance (Instance inst)
    {
        addInstance (inst, null);
    }


    /**
     * Deep copy given instance and add to set, eliminating given attribute.
     */
    public void addInstance (Instance inst, Object key)
    {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        Iterator<Attribute> iter = inst.getAttributeIterator ();
        while ( iter.hasNext() )
        {
            Attribute attr = iter.next ();
            if ( key == null || ! attr.getKey().equals(key) )
            {
                attrs.add ( __attrSet.getAttribute ( attr.getKey(), attr.getValue() ) );
            }
        }

        Classification c = __classSet.getClassification (inst.getClassification().getValue());
        __instances.add (new Instance (attrs, c));
        log.finest ("Added instance: " + inst.toString());
    }


    /**
     * Return value domain for given attribute.
     */
    public Set<Object> getValues (Object key)
    {
        return __attrSet.getValues (key);
    }


    public InstanceSet fold(  )
    {
        return null;
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

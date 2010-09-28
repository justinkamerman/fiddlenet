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
    private static Logger log = Logger.getLogger (Instance.class.getName());
    private ArrayList __instances;
    private ClassificationSet __classSet;

    private InstanceSet () {};
    public InstanceSet (BufferedReader br) throws IOException
    {
        __instances = new ArrayList ();
        __classSet = new ClassificationSet ();
        
        // Read and parse data file
        String line;
        while (( line = br.readLine() ) != null )
        {
            StringTokenizer st = new StringTokenizer (line, ",");
            Attribute[] attrs = new Attribute [st.countTokens() - 1];
            for (int i = 0; i < attrs.length; attrs [i++] = new Attribute (st.nextToken()));
            Classification c = __classSet.getClassification (st.nextToken());
            Instance inst = new Instance (attrs, c);
            __instances.add (inst);
            log.fine ("Added instance: " + inst.toString());
        }
    }


    /**
     * @return       long
     */
    public long entropy(  )
    {
        return 0;
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


}

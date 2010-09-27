/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

import instance.InstanceSet;


/**
 * Class InstanceSet
 */
public class InstanceSet {

    //
    // Fields
    //

  
    //
    // Constructors
    //
    public InstanceSet () { };
  
    //
    // Methods
    //


    //
    // Accessor methods
    //

    //
    // Other methods
    //

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
     * @return       instance.InstanceSet
     * @param        attr
     * @param        val
     */
    public instance.InstanceSet subset( instance.Attribute attr, instance.Value val )
    {
        return new InstanceSet ();
    }


    /**
     * @return       instance.InstanceSet
     */
    public InstanceSet fold(  )
    {
        return null;
    }


    /**
     * @param        inst
     */
    public void addInstance( instance.Instance inst )
    {
    }


    /**
     * @param        inst
     */
    public void removeInstance( instance.Instance inst )
    {
    }


}

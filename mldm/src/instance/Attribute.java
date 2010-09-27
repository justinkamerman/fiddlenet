/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package instance;

/**
 * Class Attribute
 */
public class Attribute {

    //
    // Fields
    //
    String __name;
  
    //
    // Constructors
    //
    public Attribute () { };
  
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
     * @return       String
     */
    public String getName(  )
    {
        return __name;
    }


    /**
     * @param        name
     */
    public void setName( String name )
    {
        __name = name;
    }


    /**
     * @return       boolean
     * @param        obj
     */
    public boolean equals( instance.Attribute obj )
    {
        return true;
    }
}

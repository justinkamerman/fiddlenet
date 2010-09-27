/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$ import org.apache.commons.cli.BasicParser;
 */
package algorithm;

import tree.DecisionTree;
import instance.InstanceSet;


/**
 * Class ID3
 */
public class ID3 {

    //
    // Fields
    //

  
    //
    // Constructors
    //
    public ID3 () { };
  
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
     * @return       tree.DecisionTree
     * @param        trainingData
     */
    public tree.DecisionTree createDecisionTree( instance.InstanceSet trainingData )
    {
        return new tree.DecisionTree();
    }


}

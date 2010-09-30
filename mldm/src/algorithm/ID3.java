/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$ import org.apache.commons.cli.BasicParser;
 */
package algorithm;

import java.util.logging.Logger;
import tree.DecisionTree;
import instance.InstanceSet;


/**
 * Class ID3
 */
public class ID3 
{
    private static Logger log = Logger.getLogger (ID3.class.getName());


    public ID3 () { };
  

    /**
     * @return       tree.DecisionTree
     * @param        trainingData
     */
    public tree.DecisionTree createDecisionTree (InstanceSet trainingData)
    {
        log.fine (trainingData.toString());
        return new tree.DecisionTree();
    }
}

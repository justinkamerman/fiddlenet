/**
 * $Id: TreeIterator.java 14 2010-10-10 15:32:16Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-10 12:32:16 -0300 (Sun, 10 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package aho;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


/**
 * This class externalizes an execution path through an Aho state
 * machine so that a single machine can be used simultaneously by
 * multiple threads.
 */
public class ExecutionContext
{
    private static Logger log = Logger.getLogger (ExecutionContext.class.getName()); 
    private StateMachine __stateMachine;


    /**
     * Create execution context which can be used by a thread to
     * navigate the state machine without effecting other threads
     * doing the same.
     */
    private ExecutionContext () {};
    public ExecutionContext (StateMachine stateMachine)
    {
        __stateMachine = stateMachine;
    }

    
    /**
     * Index the data stream, returning a map of keywords to lists of
     * occurrences (positions) withing the stream of the associated
     * keyword.
     */
    public HashMap<String, List> index (DataInputStream in)
    {
        int position = 0; // document position
        State s = __stateMachine.getStartState ();
        HashMap<String, List> results = new HashMap<String, List> (); 
      
        try 
        {
            while (true)
            {
                Character a = in.readChar ();
                log.finest ("Processing character " + a);
                
            }
        }
        catch (IOException ex)
        {
            log.finest ("Reached end of document: " + ex.getMessage());
        }

        return results;
    }


    /**
     * Returns true if the input stream contains at least one
     * occurence of each given keyword.
     */
    public boolean search (DataInputStream in, Set keywords)
    {
        return false;
    }
}
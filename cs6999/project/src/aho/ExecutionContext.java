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
import util.*;

/**
 * This class externalizes an execution path through an Aho state
 * machine so that a single machine can be used simultaneously by
 * multiple threads.
 */
public class ExecutionContext
{
    private static Logger log = Logger.getLogger (ExecutionContext.class.getName()); 
    private StateMachine __stateMachine;
    private State __state;
    private int __position;


    /**
     * Create execution context which can be used by a thread to
     * navigate the state machine without effecting other threads
     * doing the same.
     */
    private ExecutionContext () {};
    public ExecutionContext (StateMachine stateMachine)
    {
        __stateMachine = stateMachine;
        __state = __stateMachine.getStartState();
        __position = 0;
    }

    
    /**
     * Return current state
     */
    public State getState ()
    {
        return __state;
    }


    /**
     * Get current document position
     */
    public int getPosition ()
    {
        return __position;
    }

    
    /**
     * Advance to the next state. Return the output of the destination
     * state, if any, else null
     */
    public Set<String> goTo (Character a)
    {
        // Aho75 Algorithm 1
        __position++;

        return null;
        
    }
}
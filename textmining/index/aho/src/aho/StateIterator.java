/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
package aho;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.logging.Logger;

public class StateIterator implements Iterator<State>
{
    private static Logger log = Logger.getLogger (StateIterator.class.getName());
    private Stack<State> __stack = new Stack<State>();
    private ArrayList<State> __visited = new ArrayList<State>();


    private StateIterator () {}
    public StateIterator (State state)
    {
        __stack.push (state);
    }

    
    public boolean hasNext ()
    {
        return (__stack.size() != 0);
    }

    
    public State next ()
    {
        State current = __stack.pop ();
        for ( State child : current.getTransitions().values() )
        {
            // Put state on the stack if we haven't visited already
            if ( ! __visited.contains (child) )
            {
                __stack.push (child);
            }
        }
        return current;
    }


    public void remove ()
    {
        throw new UnsupportedOperationException ();
    }
}
/**
 * $Id: TreeIterator.java 14 2010-10-10 15:32:16Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-10 12:32:16 -0300 (Sun, 10 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package aho;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;


public class StateMachine
{
    private static Logger log = Logger.getLogger (StateMachine.class.getName());
    private static int __stateIdSequence = 0;
    private State __startState;


    private StateMachine () {}
    public StateMachine (Collection<String> keywords)
    {
        __startState = new State (nextStateId());

        for (String keyword : keywords )
        {
            enter (keyword);
        }

        // Add loop edge to start state

        // Construct failure function

        // Eliminate failure transitions
    }

            
    private static int nextStateId ()
    {
        return __stateIdSequence++;
    }

    
    /**
     * Add pathways for a new keyword
     */
    private void enter (String keyword)
    {
        State s = __startState;
        int j = 0;

        // Follow existing path as far as possible
        while (s.goTo (keyword.charAt(j)) != null)
        {
            s = s.goTo (keyword.charAt(j++));
        }

        // Extend path
        for (int p = j; p < keyword.length(); p++)
        {
            State newState = new State (nextStateId());
            s = s.addTransition (keyword.charAt(j), newState);
        }

        s.setOutput (keyword);
    }

    
    public String dot ()
    {
        StringBuffer sb = new StringBuffer ();
        State s;
        sb.append ("digraph G {\n");

        Iterator<State> iter = __startState.iterator ();
        while (iter.hasNext())
        {
            s = iter.next ();
            sb.append (String.format ("\t%s  [label=\"%s\", shape=circle];\n", 
                                      s.getId(),
                                      s.getId()));
            for (Character a : s.getTransitions().keySet())
            {
                 sb.append (String.format ("\t%s -> %s [label=\"%s\"];\n", 
                                           s.getId(),
                                           s.getTransitions().get(a).getId(),
                                           a));
            }
        }

        return sb.toString ();
    }
}
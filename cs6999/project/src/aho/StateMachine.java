/**
 * $Id: TreeIterator.java 14 2010-10-10 15:32:16Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-10-10 12:32:16 -0300 (Sun, 10 Oct 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package aho;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;


public class StateMachine
{
    private static Logger log = Logger.getLogger (StateMachine.class.getName());
    private static int __stateIdSequence = 0;
    private State __startState;


    private StateMachine () {}
    public StateMachine (List<String> keywords)
    {
        __startState = new State (nextStateId());

        // Construct goto function (Aho75 alogorithm 2)
        for (String keyword : keywords )
        {
            log.finest ("Adding keyword: " + keyword);
            enter (keyword);
        }
        log.info ("Added " + keywords.size() + " keywords");

        // Construct failure function (Aho75 Algorithm 3)
        constructFailureFunction ();

        // Eliminate failure transitions - Aho75 algorithm 4
    }


    public State getStartState ()
    {
        return __startState;
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

        // Follow existing path as far as possible, don't use goTo()
        // because it has pseudo loops on start state
        while (s.getTransitions().get(keyword.charAt(j)) != null)
        {
            s = s.getTransitions().get(keyword.charAt(j++));
        }

        // Extend path
        for (int p = j; p < keyword.length(); p++)
        {
            State newState = new State (nextStateId());
            s = s.addTransition (keyword.charAt(j++), newState);
        }

        s.addOutput (keyword);
    }

    
    /**
     * Construct failure function
     */
    private void constructFailureFunction ()
    {
        Queue<State> queue = new LinkedList<State>();
        for (Character a : __startState.getTransitions().keySet())
        {
            State s = __startState.goTo(a);
            log.finest ("Constructing failure function for level one state " + s.getId());

            if ( s.getId() != 0 )
            {
                s.setFailure (__startState);
                queue.add (s);
            }
        }

        while ( queue.size() != 0 )
        {
            State r = queue.remove ();
            for (Character a : r.getTransitions().keySet())
            {
                State s = r.getTransitions().get(a);
                queue.add (s);
                State state = r.getFailure ();

                // Special condition for start state because we didn't
                // add failure edges for this state
                while (state.goTo(a) == null && ! state.isStart()) 
                {
                    state = state.getFailure();
                }
                
                s.setFailure (state.goTo(a));
                s.mergeOutput (s.getFailure().getOutput());
            }
        }
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
                                      s.getId() + " " + s.getOutput()));

            // Goto edges
            for (Character a : s.getTransitions().keySet())
            {
                 sb.append (String.format ("\t%s -> %s [label=\"%s\"];\n", 
                                           s.getId(),
                                           s.getTransitions().get(a).getId(),
                                           a));
            }

            // Failure function
            if ( s.getFailure() != null)
            {
                sb.append (String.format ("\t%s -> %s [color=\"red\"];\n", 
                                          s.getId(),
                                          s.getFailure().getId()));
            }

        }

        sb.append ("}");
        return sb.toString ();
    }
}
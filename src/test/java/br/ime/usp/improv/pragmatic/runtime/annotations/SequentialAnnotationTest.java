package br.ime.usp.improv.pragmatic.runtime.annotations;

import org.junit.Test;

import br.ime.usp.improv.pragmatic.Goal;
import br.ime.usp.improv.pragmatic.Refinement;
import br.ime.usp.improv.pragmatic.Task;
import br.ime.usp.improv.pragmatic.WorkflowPlan;
import br.ime.usp.improv.pragmatic.runtime.annotations.SequentialAnnotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Felipe on 12/07/2016.
 */
public class SequentialAnnotationTest {

    @Test
    public void shouldReturnSinglePlanForAndDecompositions() {
        Goal root = new Goal(Goal.AND);
        SequentialAnnotation seq = new SequentialAnnotation();
        root.setRuntimeAnnotation(seq);

        Task t1 = new Task("T1");
        Task t2 = new Task("T2");

        root.addDependency(t1);
        root.addDependency(t2);

        WorkflowPlan plan1 = new WorkflowPlan(t1);
        WorkflowPlan plan2 = new WorkflowPlan(t2);

        Map<Refinement, WorkflowPlan> plans;
        plans = new HashMap<Refinement, WorkflowPlan>();
        plans.put(t1, plan1);
        plans.put(t2, plan2);

        List<WorkflowPlan> possible = seq.getPossiblePlans(plans);

        assertEquals(1, possible.size());
        assertTrue(possible.get(0).getTasks().contains(t1.getWorkflowTask()));
        assertTrue(possible.get(0).getTasks().contains(t2.getWorkflowTask()));
    }
}

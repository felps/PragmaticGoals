package br.ime.usp.improv.pragmatic.runtime.annotations;

import org.junit.Test;

import br.ime.usp.improv.pragmatic.Refinement;
import br.ime.usp.improv.pragmatic.Task;
import br.ime.usp.improv.pragmatic.WorkflowPlan;
import br.ime.usp.improv.pragmatic.runtime.annotations.InterleavedAnnotation;
import br.ime.usp.improv.pragmatic.workflow.WorkflowTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Felipe on 12/07/2016.
 */
public class InterleavedAnnotationTest {


    @Test
    public void shouldReturnSinglePlanForAndDecompositions() {
        Task t1 = new Task("T1");
        Task t2 = new Task("T2");

        InterleavedAnnotation seq = new InterleavedAnnotation();
        seq.includeRefinement(t1, 0);
        seq.includeRefinement(t2, 1);

        WorkflowPlan plan1 = new WorkflowPlan(t1);
        WorkflowPlan plan2 = new WorkflowPlan(t2);

        Map<Refinement, WorkflowPlan> plans;
        plans = new HashMap<Refinement, WorkflowPlan>();
        plans.put(t1, plan1);
        plans.put(t2, plan2);

        List<WorkflowPlan> possible = seq.getPossiblePlans(plans);

        assertEquals(1, possible.size());
        assertTrue(possible.get(0).getTasks().contains(new WorkflowTask(t1)));
        assertTrue(possible.get(0).getTasks().contains(new WorkflowTask(t2)));
    }
}
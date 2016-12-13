package br.ime.usp.improv.pragmatic.runtime.annotations;

import org.junit.Test;

import br.ime.usp.improv.pragmatic.Refinement;
import br.ime.usp.improv.pragmatic.Task;
import br.ime.usp.improv.pragmatic.WorkflowPlan;
import br.ime.usp.improv.pragmatic.runtime.annotations.AlternativeAnnotation;
import br.ime.usp.improv.pragmatic.workflow.WorkflowTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Felipe on 12/07/2016.
 * <p>
 * Alternative is an annotation which allows any one refinement to be chosen.
 * Never more, never less
 */
public class AlternativeAnnotationTest {
    @Test
    public void shouldReturnNPlansForAnOrDecompositionsTest() throws Exception {
        Task t1 = new Task("T1");
        Task t2 = new Task("T2");

        AlternativeAnnotation seq = new AlternativeAnnotation();
        seq.includeRefinement(t1, 0);
        seq.includeRefinement(t2, 1);

        WorkflowPlan plan1 = new WorkflowPlan(t1);
        WorkflowPlan plan2 = new WorkflowPlan(t2);

        Map<Refinement, WorkflowPlan> plans;
        plans = new HashMap<>();
        plans.put(t1, plan1);
        plans.put(t2, plan2);

        List<WorkflowPlan> possible = seq.getPossiblePlans(plans);

        assertEquals(2, possible.size());
        WorkflowTask wt1 = t1.getWorkflowTask();
        WorkflowTask wt2 = t2.getWorkflowTask();
        for (Plan p : possible) {
            boolean hasT1 = p.getTasks().contains(wt1);
            boolean hasT2 = p.getTasks().contains(wt2);

            assertTrue(hasT1 || hasT2);
            assertEquals(1, p.getTasks().size());
        }
    }
}

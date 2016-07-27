package pragmatic.runtime.annotations;

import pragmatic.Refinement;
import pragmatic.Task;
import pragmatic.workflow.Plan;
import pragmatic.workflow.WorkflowTask;
import org.junit.Test;

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

        Plan plan1 = new Plan(t1);
        Plan plan2 = new Plan(t2);

        Map<Refinement, Plan> plans;
        plans = new HashMap<>();
        plans.put(t1, plan1);
        plans.put(t2, plan2);

        List<Plan> possible = seq.getPossiblePlans(plans);

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

package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.Task;
import cgm.workflow.Plan;
import org.junit.Test;

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

        Plan plan1 = new Plan(t1);
        Plan plan2 = new Plan(t2);

        Map<Refinement, Plan> plans;
        plans = new HashMap<Refinement, Plan>();
        plans.put(t1, plan1);
        plans.put(t2, plan2);

        List<Plan> possible = seq.getPossiblePlans(plans);

        assertEquals(1, possible.size());
        assertTrue(possible.get(0).getTasks().contains(t1.getWorkflowTask()));
        assertTrue(possible.get(0).getTasks().contains(t2.getWorkflowTask()));
    }
}
package pragmatic.runtime.annotations;

import pragmatic.Goal;
import pragmatic.Refinement;
import pragmatic.Task;
import pragmatic.workflow.Plan;
import org.junit.Test;

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

package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.Task;
import cgm.workflow.Plan;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Felipe on 12/07/2016.
 */
public class CardinalityAnnotationTest {


    @Test
    public void shouldReturnNPlansForAnOrDecompositionTest() throws Exception {
        Task t1 = new Task("iteracao");
        t1.setTimeConsumed(15);

        CardinalityAnnotation seq = new CardinalityAnnotation();
        seq.includeRefinement(t1, 0);
        seq.setIterations(20);

        Plan plan1 = new Plan(t1);

        Map<Refinement, Plan> plans;
        plans = new HashMap<Refinement, Plan>();
        plans.put(t1, plan1);

        List<Plan> possible = seq.getPossiblePlans(plans);

        assertEquals(1, possible.size());
        assertEquals(20, possible.get(0).getTasks().size());
        //TODO resolver...
        //assertEquals(15*20, possible.get(0).getTimeConsumed(), 0.1);

    }

    @Test
    public void shouldReturn1PlansForAnAndDecompositionTest() throws Exception {
        Task t1 = new Task("iteracao");
        t1.setTimeConsumed(15);

        CardinalityAnnotation seq = new CardinalityAnnotation();
        seq.includeRefinement(t1, 0);
        seq.setIterations(20);

        Plan plan1 = new Plan(t1);

        Map<Refinement, Plan> plans;
        plans = new HashMap<Refinement, Plan>();
        plans.put(t1, plan1);

        List<Plan> possible = seq.getPossiblePlans(plans);

        assertEquals(1, possible.size());
        assertEquals(20, possible.get(0).getTasks().size());

    }


}

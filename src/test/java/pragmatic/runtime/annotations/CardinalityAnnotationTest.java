package pragmatic.runtime.annotations;

import pragmatic.Refinement;
import pragmatic.Task;
import pragmatic.workflow.Plan;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.pow;
import static org.junit.Assert.assertEquals;

/**
 * Created by Felipe on 12/07/2016.
 */
public class CardinalityAnnotationTest {

    private Task t1;
    private Map<Refinement, Plan> planMap;
    private Plan plan1;
    private CardinalityAnnotation seq;
    private CardinalityAnnotation interleavedCardinalAnnotation;

    @Before
    public void setUp() throws Exception {
        t1 = new Task("iteracao");
        t1.setTimeConsumed(15);

        planMap = new HashMap<Refinement, Plan>();

        plan1 = new Plan(t1);
        planMap.put(t1, plan1);

        seq = new SequentialCardinalAnnotation();
        seq.includeRefinement(t1, 0);

        interleavedCardinalAnnotation = new InterleavedCardinalAnnotation();
        interleavedCardinalAnnotation.includeRefinement(t1, 0);
    }

    @Test
    public void shouldCloneAPlan() throws Exception {
        Plan p = new Plan(t1);
        Plan clone = seq.clonePlan(p, 1);

        assertEquals(p.getReliability(), clone.getReliability(), 0.01);
        assertEquals(p.getTimeConsumed(), clone.getTimeConsumed(), 0.01);
        assertEquals(p.getTasks().size(), clone.getTasks().size());
    }

    @Test
    public void shouldReturnNSequentialIterationsTest() throws Exception {

        int iterations = 50;
        for (iterations = 1; iterations < 100; iterations++) {
            seq.setIterations(iterations);

            List<Plan> possible = seq.getPossiblePlans(planMap);

            assertEquals(1, possible.size());
            assertEquals(iterations, possible.get(0).getTasks().size());

            assertEquals(15 * iterations, possible.get(0).getTimeConsumed(), 0.1);
            assertEquals(plan1.getReliability(), pow(possible.get(0).getReliability(), iterations), 0.1);
        }
    }

    @Test
    public void shouldReturNParallelPlansTest() throws Exception {
        int replicas = 1;

        for (replicas = 1; replicas < 100; replicas++) {
            interleavedCardinalAnnotation.setIterations(replicas);

            List<Plan> possible = interleavedCardinalAnnotation.getPossiblePlans(planMap);

            assertEquals(1, possible.size());
            assertEquals(replicas, possible.get(0).getTasks().size());
            assertEquals(plan1.getReliability(), pow(possible.get(0).getReliability(), replicas), 0.1);
            assertEquals(plan1.getTimeConsumed(), possible.get(0).getTimeConsumed(), 0.01);
        }
    }


}
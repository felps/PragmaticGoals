package experiment.runtime;

import cgm.Comparison;
import cgm.Goal;
import cgm.Interpretation;
import cgm.Task;
import cgm.metrics.Metric;
import cgm.quality.CompositeQualityConstraint;
import cgm.runtime.annotations.SequentialAnnotation;
import cgm.workflow.Plan;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Felipe on 07/07/2016.
 */
public class PragmaticRuntimeCgmTest {

    Task t1, t2;
    Goal rootAnd, rootOr;
    SequentialAnnotation sequentialRuntimeAnnotation;

    @Before
    public void setUp() throws Exception {
        t1 = new Task();
        t1.setIdentifier("T1");
        t1.setTimeConsumed(120);
        t1.setReliability(0.99);

        t2 = new Task();
        t2.setIdentifier("T2");
        t2.setTimeConsumed(120);
        t2.setReliability(0.99);

        sequentialRuntimeAnnotation = new SequentialAnnotation();
        sequentialRuntimeAnnotation.includeRefinement(t1, 0);
        sequentialRuntimeAnnotation.includeRefinement(t2, 1);

        rootAnd = new Goal(Goal.AND);
        rootAnd.setIdentifier("rootAnd");
        rootAnd.addDependency(t1);
        rootAnd.addDependency(t2);
    }

    @Test
    public void achievableSequentialAND() throws Exception {
        CompositeQualityConstraint compQC;
        Interpretation interp = new Interpretation();

        rootAnd.setRuntimeAnnotation(sequentialRuntimeAnnotation);

        compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 241);
        interp.addCompositeQualityConstraint(compQC);

        Plan plan = rootAnd.isAchievable(null, interp);

        assertTrue(plan.isAchievable());
        assertEquals(2, plan.getTasks().size());
    }

    @Test
    public void unachievableSequentialAND() throws Exception {
        CompositeQualityConstraint compQC;
        Interpretation interp = new Interpretation();

        rootAnd.setRuntimeAnnotation(sequentialRuntimeAnnotation);

        compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 120);
        interp.addCompositeQualityConstraint(compQC);

        Plan plan = rootAnd.isAchievable(null, interp);

        assertFalse(plan.isAchievable());
    }

    @Test
    public void unachievableParallelAND() {

    }

    @Test
    public void achievableParallelOR() {

    }

    @Test
    public void unachievableParallelOR() {

    }

    @Test
    public void achievableAlternativeOR() {

    }

    @Test
    public void unachievableAlternativeOR() {

    }


}

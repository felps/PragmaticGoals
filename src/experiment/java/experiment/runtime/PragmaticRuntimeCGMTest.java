package experiment.runtime;

import cgm.Comparison;
import cgm.Goal;
import cgm.Interpretation;
import cgm.Task;
import cgm.metrics.Metric;
import cgm.quality.CompositeQualityConstraint;
import cgm.runtime.annotations.AlternativeAnnotation;
import cgm.runtime.annotations.InterleavedAnnotation;
import cgm.runtime.annotations.SequentialAnnotation;
import cgm.workflow.Plan;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Felipe on 07/07/2016.
 *
 * pragmatic Runtime to evaluate composite metrics evaluation in small models (3 nodes)
 */
public class PragmaticRuntimeCgmTest {

    private Task t1, t2;
    private Goal rootAnd, rootOr;
    private SequentialAnnotation sequentialRuntimeAnnotation;
    private InterleavedAnnotation interleavedAnnotation;
    private AlternativeAnnotation alternativeAnnotation;

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

        interleavedAnnotation = new InterleavedAnnotation();
        interleavedAnnotation.includeRefinement(t1, 0);
        interleavedAnnotation.includeRefinement(t2, 1);

        alternativeAnnotation = new AlternativeAnnotation();
        alternativeAnnotation.includeRefinement(t1, 0);
        alternativeAnnotation.includeRefinement(t2, 1);

        rootAnd = new Goal(Goal.AND);
        rootAnd.setIdentifier("rootAnd");
        rootAnd.addDependency(t1);
        rootAnd.addDependency(t2);

        rootOr = new Goal(Goal.OR);
        rootOr.setIdentifier("rootOr");
        rootOr.addDependency(t1);
        rootOr.addDependency(t2);
    }

    @Test
    public void achievableSequentialAND() throws Exception {
        Goal root = this.rootAnd;
        root.setRuntimeAnnotation(sequentialRuntimeAnnotation);

        CompositeQualityConstraint compQC;
        Interpretation interp = new Interpretation();


        compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 241);
        interp.addCompositeQualityConstraint(compQC);

        Plan plan = root.isAchievable(null, interp);

        assertTrue(plan.isAchievable());
        assertEquals(2, plan.getTasks().size());
    }

    @Test
    public void unachievableSequentialAND() throws Exception {
        Goal root = this.rootAnd;
        root.setRuntimeAnnotation(sequentialRuntimeAnnotation);

        CompositeQualityConstraint compQC;
        Interpretation interp = new Interpretation();


        compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 120);
        interp.addCompositeQualityConstraint(compQC);

        Plan plan = root.isAchievable(null, interp);

        assertFalse(plan.isAchievable());
    }

    @Test
    public void unachievableParallelAND() throws Exception {
        Goal root = this.rootAnd;
        root.setRuntimeAnnotation(interleavedAnnotation);

        CompositeQualityConstraint compQC;
        Interpretation interp = new Interpretation();


        t1.setTimeConsumed(150);
        compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 120);
        interp.addCompositeQualityConstraint(compQC);

        Plan plan = root.isAchievable(null, interp);

        assertFalse(plan.isAchievable());

    }

    @Test
    public void achievableParallelAND() throws Exception {
        Goal root = this.rootAnd;
        root.setRuntimeAnnotation(interleavedAnnotation);

        CompositeQualityConstraint compQC;
        Interpretation interp = new Interpretation();

        t1.setTimeConsumed(150);
        t1.setTimeConsumed(120);

        compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 120);
        interp.addCompositeQualityConstraint(compQC);

        Plan plan = root.isAchievable(null, interp);

        assertTrue(plan.isAchievable());

    }

    @Test
    public void achievableParallelOR() throws Exception {
        Goal goal = this.rootOr;
        goal.setRuntimeAnnotation(interleavedAnnotation);

        CompositeQualityConstraint compQC;
        Interpretation interp = new Interpretation();


        t1.setTimeConsumed(150);
        t2.setTimeConsumed(100);

        compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 120);
        interp.addCompositeQualityConstraint(compQC);

        Plan plan = goal.isAchievable(null, interp);

        assertTrue(plan.isAchievable());
    }

    @Test
    public void unachievableParallelOR() throws Exception {
        Goal root = this.rootOr;
        root.setRuntimeAnnotation(interleavedAnnotation);

        CompositeQualityConstraint compQC;
        Interpretation interp = new Interpretation();


        t1.setTimeConsumed(150);
        t2.setTimeConsumed(150);

        compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 120);
        interp.addCompositeQualityConstraint(compQC);

        Plan plan = root.isAchievable(null, interp);

        assertFalse(plan.isAchievable());

    }

    @Test
    public void achievableAlternativeOR() throws Exception {
        Goal goal = this.rootOr;
        goal.setRuntimeAnnotation(alternativeAnnotation);

        CompositeQualityConstraint compQC;
        Interpretation interp = new Interpretation();


        t1.setTimeConsumed(150);
        t2.setTimeConsumed(100);

        compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 120);
        interp.addCompositeQualityConstraint(compQC);

        Plan plan = goal.isAchievable(null, interp);

        assertTrue(plan.isAchievable());


    }

    @Test
    public void unachievableAlternativeOR() throws Exception {
        Goal goal = this.rootOr;
        goal.setRuntimeAnnotation(interleavedAnnotation);

        CompositeQualityConstraint compQC;
        Interpretation interp = new Interpretation();

        t1.setTimeConsumed(150);
        t2.setTimeConsumed(130);

        compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 120);
        interp.addCompositeQualityConstraint(compQC);

        Plan plan = goal.isAchievable(null, interp);

        assertFalse(plan.isAchievable());

    }


}

package pragmatic.runtime.annotations;

import org.junit.Before;
import org.junit.Test;
import pragmatic.Comparison;
import pragmatic.Goal;
import pragmatic.Pragmatic;
import pragmatic.Task;
import pragmatic.metrics.Metric;
import pragmatic.quality.FilterQualityConstraint;
import pragmatic.workflow.Plan;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Felipe on 25/07/2016.
 */
public class TryAnnotationTest {
    private Pragmatic tryGoal;
    private Task tryTask;
    private Task thenTask;
    private Task elseTask;

    @Before
    public void setUp() throws Exception {
        tryTask = new Task("Try");
        thenTask = new Task("Then");
        elseTask = new Task("Else");

        tryGoal = new Pragmatic(Goal.OR);
        TryAnnotation seq = new TryAnnotation();
        tryGoal.setRuntimeAnnotation(seq);

        tryGoal.addDependency(tryTask);
        tryGoal.addDependency(thenTask);
        tryGoal.addDependency(elseTask);
    }

    @Test
    public void TryAchievableThenAchievableElseAchievable() throws Exception {
        tryTask.setProvidedQuality(null, Metric.NOISE, 100);
        FilterQualityConstraint qc = new FilterQualityConstraint(null, Metric.NOISE, 110, Comparison.LESS_OR_EQUAL_TO);
        tryGoal.getInterpretation().addFilterQualityConstraint(qc);

        Plan plan = tryGoal.isAchievable(null, null);

        assertTrue(plan.isAchievable());
        assertTrue(plan.getTasks().contains(tryTask.getWorkflowTask()));
        assertTrue(plan.getTasks().contains(thenTask.getWorkflowTask()));
    }

    @Test
    public void TryUnAchievableThenAchievableElseAchievable() throws Exception {
        tryTask.setProvidedQuality(null, Metric.NOISE, 120);

        FilterQualityConstraint qc = new FilterQualityConstraint(null, Metric.NOISE, 110, Comparison.LESS_OR_EQUAL_TO);
        tryGoal.getInterpretation().addFilterQualityConstraint(qc);

        Plan plan = tryGoal.isAchievable(null, null);

        assertTrue(plan.isAchievable());
        assertTrue(plan.getTasks().contains(tryTask.getWorkflowTask()));
        assertTrue(plan.getTasks().contains(elseTask.getWorkflowTask()));
    }

    @Test
    public void TryAchievableThenUnAchievableElseAchievable() throws Exception {
        tryTask.setProvidedQuality(null, Metric.NOISE, 100);
        thenTask.setProvidedQuality(null, Metric.NOISE, 120);

        FilterQualityConstraint qc = new FilterQualityConstraint(null, Metric.NOISE, 110, Comparison.LESS_OR_EQUAL_TO);
        tryGoal.getInterpretation().addFilterQualityConstraint(qc);

        Plan plan = tryGoal.isAchievable(null, null);

        assertFalse(plan.isAchievable());
        assertTrue(plan.getTasks().contains(tryTask.getWorkflowTask()));
        assertTrue(plan.getTasks().contains(thenTask.getWorkflowTask()));
    }

    @Test
    public void TryAchievableThenAchievableElseUnAchievable() throws Exception {
        tryTask.setProvidedQuality(null, Metric.NOISE, 100);

        FilterQualityConstraint qc = new FilterQualityConstraint(null, Metric.NOISE, 110, Comparison.LESS_OR_EQUAL_TO);
        tryGoal.getInterpretation().addFilterQualityConstraint(qc);

        Plan plan = tryGoal.isAchievable(null, null);

        assertTrue(plan.isAchievable());
        assertTrue(plan.getTasks().contains(tryTask.getWorkflowTask()));
        assertTrue(plan.getTasks().contains(thenTask.getWorkflowTask()));
    }

    @Test
    public void TryUnachievableThenUnAchievableElseAchievable() throws Exception {
        tryTask.setProvidedQuality(null, Metric.NOISE, 120);
        thenTask.setProvidedQuality(null, Metric.NOISE, 120);

        FilterQualityConstraint qc = new FilterQualityConstraint(null, Metric.NOISE, 110, Comparison.LESS_OR_EQUAL_TO);
        tryGoal.getInterpretation().addFilterQualityConstraint(qc);

        Plan plan = tryGoal.isAchievable(null, null);

        assertTrue(plan.isAchievable());
        assertTrue(plan.getTasks().contains(tryTask.getWorkflowTask()));
        assertTrue(plan.getTasks().contains(elseTask.getWorkflowTask()));
    }


    @Test
    public void TryUnachievableThenAchievableElseUnAchievable() throws Exception {
        tryTask.setProvidedQuality(null, Metric.NOISE, 120);
        elseTask.setProvidedQuality(null, Metric.NOISE, 120);

        FilterQualityConstraint qc = new FilterQualityConstraint(null, Metric.NOISE, 110, Comparison.LESS_OR_EQUAL_TO);
        tryGoal.getInterpretation().addFilterQualityConstraint(qc);

        Plan plan = tryGoal.isAchievable(null, null);

        assertFalse(plan.isAchievable());
        assertTrue(plan.getTasks().contains(tryTask.getWorkflowTask()));
        assertTrue(plan.getTasks().contains(elseTask.getWorkflowTask()));
    }

    @Test
    public void TryAchievableThenUnAchievableElseUnAchievable() throws Exception {
        tryTask.setProvidedQuality(null, Metric.NOISE, 100);
        thenTask.setProvidedQuality(null, Metric.NOISE, 120);
        elseTask.setProvidedQuality(null, Metric.NOISE, 120);

        FilterQualityConstraint qc = new FilterQualityConstraint(null, Metric.NOISE, 110, Comparison.LESS_OR_EQUAL_TO);
        tryGoal.getInterpretation().addFilterQualityConstraint(qc);

        Plan plan = tryGoal.isAchievable(null, null);

        assertFalse(plan.isAchievable());
        assertTrue(plan.getTasks().contains(tryTask.getWorkflowTask()));
        assertTrue(plan.getTasks().contains(thenTask.getWorkflowTask()));
    }

}

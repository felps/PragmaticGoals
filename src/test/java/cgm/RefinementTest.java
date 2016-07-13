package cgm;

import cgm.metrics.Metric;
import cgm.metrics.exceptions.DifferentMetricsException;
import cgm.quality.FilterQualityConstraint;
import cgm.runtime.annotations.InterleavedAnnotation;
import cgm.workflow.Plan;
import cgm.workflow.WorkflowTask;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RefinementTest {

	@Test
	public void shouldBeApplicable() throws Exception {
		Refinement goal = new Goal(false);
		Task task = new Task();
		Delegation delegation = new Delegation();

		Context current = new Context("c1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		task.addApplicableContext(current);
		goal.addApplicableContext(current);
		delegation.addApplicableContext(current);

		assertTrue(goal.isApplicable(fullContext));
		assertTrue(task.isApplicable(fullContext));
		assertTrue(delegation.isApplicable(fullContext));
	}

	@Test
	public void shouldBeNotApplicable() throws Exception {
		Refinement goal = new Goal(false);
		Task task = new Task();
		Delegation delegation = new Delegation();

		Context context = new Context("C1");

		task.addApplicableContext(context);
		goal.addApplicableContext(context);
		delegation.addApplicableContext(context);

		Context wrongContext = new Context("C2");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(wrongContext);

		assertFalse(goal.isApplicable(fullContext));
		assertFalse(task.isApplicable(fullContext));
		assertFalse(delegation.isApplicable(fullContext));
	}

	@Test
	public void aTaskShouldBeAchievable() throws Exception {
		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

        FilterQualityConstraint qc = new FilterQualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);

		task.addApplicableContext(current);
        task.setProvidedQuality(current, Metric.SECONDS, 12);

		Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

        assertTrue(task.isAchievable(fullContext, interp).getTasks().contains(task.getWorkflowTask()));
    }

	@Test
	public void aTaskMayNotBeAchievable() throws Exception {
		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

        FilterQualityConstraint qc = new FilterQualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);

		task.addApplicableContext(current);
        task.setProvidedQuality(current, Metric.SECONDS, 16);

		Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

        Plan plan = task.isAchievable(fullContext, interp);

        assertFalse(plan.isAchievable());
    }

	@Test
	public void aNonApplicableRootGoalIsNotAchievable() throws Exception {
		Goal goal = new Goal(false);
		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();

        FilterQualityConstraint qc = new FilterQualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);
        goal.addApplicableContext((new Context("C2")));

		Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

		assertEquals(null, goal.isAchievable(fullContext, interp));
	}

	@Test
	public void aGoalWithATaskMayBeAchievable() throws Exception {
		Goal goal = new Goal(false);

		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

        FilterQualityConstraint qc = new FilterQualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);
        Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

		task.addApplicableContext(current);
        task.setProvidedQuality(current, Metric.SECONDS, 13);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Plan plan = goal.isAchievable(fullContext, interp);
		assertEquals(1, plan.getTasks().size());
	}

	@Test
	public void aGoalAndDecomposedWithTwoTasksMayBeAchievable() throws Exception {
        Goal goal = new Goal(Goal.AND);
        goal.setRuntimeAnnotation(new InterleavedAnnotation());

        Task task1 = new Task("t1");
        Task task2 = new Task("t2");

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

        FilterQualityConstraint qc = new FilterQualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);
        Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

		task1.addApplicableContext(current);
        task1.setProvidedQuality(current, Metric.SECONDS, 13);

		task2.addApplicableContext(current);
        task2.setProvidedQuality(current, Metric.SECONDS, 11);

        goal.setIdentifier("Root");
        goal.addApplicableContext(current);

		goal.addDependency(task1);
		goal.addDependency(task2);


		Plan plan = goal.isAchievable(fullContext, interp);

        assertNotNull(plan);

        assertTrue(plan.isAchievable());

        Collection<WorkflowTask> tasks = plan.getTasks();
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(task1.getWorkflowTask()));
        assertTrue(tasks.contains(task2.getWorkflowTask()));


        Set<WorkflowTask> initialTasks = plan.getInitialTasks();
        assertTrue(initialTasks.contains(task1.getWorkflowTask()));
        assertTrue(initialTasks.contains(task2.getWorkflowTask()));
        assertEquals(2, initialTasks.size());

        Set<WorkflowTask> finalTasks = plan.getFinalTasks();
        assertTrue(finalTasks.contains(task1.getWorkflowTask()));
        assertTrue(finalTasks.contains(task2.getWorkflowTask()));
        assertEquals(2, finalTasks.size());
    }

	@Test
	public void aGoalAndDecomposedWithTwoTasksMayNotBeAchievable() throws Exception {
		Goal goal = new Goal(Goal.AND);

        Task task1 = new Task("T1");
        Task task2 = new Task("T2");

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

        FilterQualityConstraint qc = new FilterQualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
        task1.setProvidedQuality(current, Metric.SECONDS, 16);

		task2.addApplicableContext(current);
        task2.setProvidedQuality(current, Metric.SECONDS, 11);

		goal.addDependency(task1);
		goal.addDependency(task2);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

		Plan plan = goal.isAchievable(fullContext, interp);
        assertFalse(plan.isAchievable());
    }

	@Test
	public void aGoalOrDecomposedWithTwoTasksMayBeAchievable() throws Exception {
        Goal goal = new Goal(Goal.OR);
        assertTrue(goal.isOrDecomposition());

        Task task1 = new Task("T1");
        Task task2 = new Task("T2");

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

        FilterQualityConstraint qc = new FilterQualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);
        Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

		task1.addApplicableContext(current);
        task1.setProvidedQuality(current, Metric.SECONDS, 13);

		task2.addApplicableContext(current);
        task2.setProvidedQuality(current, Metric.SECONDS, 11);

		goal.addDependency(task1);
		goal.addDependency(task2);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Plan plan = goal.isAchievable(fullContext, interp);
        assertTrue(plan.isAchievable());
        assertEquals(1, plan.getTasks().size());
    }

	@Test
	public void aGoalOrDecomposedWithTwoTasksMayBeAchievableAtOnlyOneBranch() throws Exception {
        Goal goal = new Goal(Goal.OR);
        assertTrue(goal.isOrDecomposition());

        Task task1 = new Task("t1");
        Task task2 = new Task("t2");

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

        FilterQualityConstraint qc = new FilterQualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
        task1.setProvidedQuality(current, Metric.SECONDS, 16);

		task2.addApplicableContext(current);
        task2.setProvidedQuality(current, Metric.SECONDS, 11);

		goal.addDependency(task1);
		goal.addDependency(task2);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

		Plan plan = goal.isAchievable(fullContext, interp);
        assertTrue(plan.isAchievable());
        assertTrue(plan.getTasks().contains(task2.getWorkflowTask()));
        assertTrue(!plan.getTasks().contains(task1.getWorkflowTask()));
    }

	@Test
	public void aGoalOrDecomposedWithTwoTasksMayNotBeAchievable() throws Exception {
        Goal goal = new Goal(Goal.OR);

        Task task1 = new Task("t1");
        Task task2 = new Task("t2");
        Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

        FilterQualityConstraint qc = new FilterQualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
        task1.setProvidedQuality(current, Metric.SECONDS, 16);

		task2.addApplicableContext(current);
        task2.setProvidedQuality(current, Metric.SECONDS, 17);

		goal.addDependency(task1);
		goal.addDependency(task2);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

		Plan plan = goal.isAchievable(fullContext, interp);
		assertTrue(goal.isOrDecomposition());
        assertFalse(plan.isAchievable());
    }

	@Test
	public void testApplicableDeps() {
		Pragmatic goal = new Pragmatic(false);

        Task task = new Task("t1");
        Context context = new Context("C1");
		Context wrongContext = new Context("C2");
		HashSet<Context> current = new HashSet<Context>();

        FilterQualityConstraint qc = new FilterQualityConstraint(context, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);

		task.addApplicableContext(context);
        task.setProvidedQuality(context, Metric.SECONDS, 13);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addApplicableContext(context);
        goal.getInterpretation().addFilterQualityConstraint(qc);

		Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

		current.add(wrongContext);
		assertEquals(null, goal.isAchievable(current, interp));

		current.add(context);
		assertEquals(1, goal.isAchievable(current, interp).getTasks().size());

	}

	@Test
	public void testGetApplicableQC() {
		Pragmatic goal = new Pragmatic(false);

        Task task = new Task("t1");
        Context context = new Context("C1");
		Context anotherContext = new Context("C2");

		HashSet<Context> fullContext = new HashSet<Context>();

        FilterQualityConstraint qc = new FilterQualityConstraint(context, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);
        FilterQualityConstraint stricter = new FilterQualityConstraint(anotherContext, Metric.SECONDS, 10,
                Comparison.LESS_OR_EQUAL_TO);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addApplicableContext(context);
        goal.getInterpretation().addFilterQualityConstraint(qc);
        goal.getInterpretation().addFilterQualityConstraint(stricter);

		Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

		fullContext.add(context);
		// assertEquals(null, goal.isAchievable(fullContext, interp));

		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(qc));

		fullContext.add(anotherContext);
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(qc));
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(stricter));

		fullContext.remove(context);
		assertTrue(!goal.getInterpretation().getQualityConstraints(fullContext).contains(qc));
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(stricter));

	}

	@Test
	public void shouldThereBeMoreThanOneApplicableQCreturnTheStricterOne() throws DifferentMetricsException {
		Pragmatic goal = new Pragmatic(false);

        Task task = new Task("t1");
        Context context = new Context("C1");
		Context anotherContext = new Context("C2");

		HashSet<Context> fullContext = new HashSet<Context>();

        FilterQualityConstraint qc = new FilterQualityConstraint(context, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);
        FilterQualityConstraint stricter = new FilterQualityConstraint(anotherContext, Metric.SECONDS, 10,
                Comparison.LESS_OR_EQUAL_TO);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addApplicableContext(context);
        goal.getInterpretation().addFilterQualityConstraint(qc);
        goal.getInterpretation().addFilterQualityConstraint(stricter);

		assertEquals(stricter, qc.stricterQC(stricter));

		fullContext.add(context);
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(qc));

		fullContext.add(anotherContext);
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(stricter));

	}

	@Test
	public void shouldIncludeNonApplicableContexts() {
		Pragmatic goal = new Pragmatic(false);

        Task task = new Task("t1");
        Context context = new Context("C1");
		Context wrongContext = new Context("C2");
		HashSet<Context> current = new HashSet<Context>();

        FilterQualityConstraint qc = new FilterQualityConstraint(context, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);

		task.addApplicableContext(context);
        task.setProvidedQuality(context, Metric.SECONDS, 13);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addNonApplicableContext(wrongContext);
        goal.getInterpretation().addFilterQualityConstraint(qc);

		Interpretation interp = new Interpretation();
        interp.addFilterQualityConstraint(qc);

		current.add(wrongContext);
		assertEquals(null, goal.isAchievable(current, interp));

		current.add(context);
		assertEquals(null, goal.isAchievable(current, interp));

		current.remove(wrongContext);
		assertTrue(goal.isAchievable(current, interp) != null);
		assertTrue(goal.isAchievable(current, interp).getTasks() != null);
		assertEquals(1, goal.isAchievable(current, interp).getTasks().size());

	}

	@Test
	public void shouldAddSeveralContextsAtOnce(){
		Context context1 = new Context("C1");
		Context context2 = new Context("C2");

        Task task = new Task("t1");
        int originalSize = task.getApplicableContexts().size();
        HashSet<Context> set = new HashSet<Context>();
		
		set.add(context1);
		set.add(context2);
		
		task.addApplicableContext(set);
		// null is always an applicable context
        assertEquals(2, task.getApplicableContexts().size() - originalSize);
    }
}

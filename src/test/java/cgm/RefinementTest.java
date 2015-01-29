package cgm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import metrics.ExecutionTimeSec;

import org.junit.Test;

import cgm.Comparison;
import cgm.Context;
import cgm.Delegation;
import cgm.DifferentMetricsException;
import cgm.Goal;
import cgm.Interpretation;
import cgm.Plan;
import cgm.Pragmatic;
import cgm.QualityConstraint;
import cgm.Refinement;
import cgm.Task;

public class RefinementTest {

	@Test
	public void shouldReturnMyType() {
		Refinement goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Task task = new Task();
		Delegation delegation = new Delegation();

		assertEquals(Refinement.GOAL, goal.myType());

		assertEquals(Refinement.TASK, task.myType());

		assertEquals(Refinement.DELEGATION, delegation.myType());
	}

	@Test
	public void shouldBeApplicable() throws Exception {
		Refinement goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
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
		Refinement goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
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

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task.addApplicableContext(current);
		task.setProvidedQuality(current, (new ExecutionTimeSec()), 12);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		assertTrue(task.isAchievable(fullContext, interp).getTasks().contains(task));
	}

	@Test
	public void aTaskShouldBeAchievableWithoutRequirements() throws Exception {
		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		task.addApplicableContext(current);
		task.setProvidedQuality(current, (new ExecutionTimeSec()), 12);

		Interpretation interp = null;

		assertTrue(task.isAchievable(fullContext, interp).getTasks().contains(task));
	}

	@Test
	public void aTaskMayNotBeAchievable() throws Exception {
		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task.addApplicableContext(current);
		task.setProvidedQuality(current, (new ExecutionTimeSec()), 16);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		assertEquals(null, task.isAchievable(fullContext, interp));
	}

	@Test
	public void aNonApplicableRootGoalIsNotAchievable() throws Exception {
		Goal goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);
		goal.addApplicableContext((new Context("C2")));

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		assertEquals(null, goal.isAchievable(fullContext, interp));
	}

	@Test
	public void aGoalWithATaskMayBeAchievable() throws Exception {
		Goal goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);

		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);
		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		task.addApplicableContext(current);
		task.setProvidedQuality(current, (new ExecutionTimeSec()), 13);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Plan plan = goal.isAchievable(fullContext, interp);
		assertEquals(1, plan.getTasks().size());
	}

	@Test
	public void aGoalAndDecomposedWithTwoTasksMayBeAchievable() throws Exception {
		Goal goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		assertTrue(goal.isAndDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, (new ExecutionTimeSec()), 13);

		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, (new ExecutionTimeSec()), 11);

		goal.addDependency(task1);
		goal.addDependency(task2);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		Plan plan = goal.isAchievable(fullContext, interp);
		assertEquals(2, plan.getTasks().size());
	}

	@Test
	public void anotherTaskMayNotBeAchievable() throws Exception {
		Goal goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		assertTrue(goal.isAndDecomposition());

		Task task1 = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, (new ExecutionTimeSec()), 16);

		goal.addDependency(task1);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		Plan plan = goal.isAchievable(fullContext, interp);
		assertEquals(null, plan);
	}

	@Test
	public void anotherTasksMayBeAchievable() throws Exception {
		Goal goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		assertTrue(goal.isAndDecomposition());

		Task task2 = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, (new ExecutionTimeSec()), 11);

		goal.addDependency(task2);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		Plan plan = goal.isAchievable(fullContext, interp);
		assertTrue(plan != null);
	}

	@Test
	public void aGoalAndDecomposedWithTwoTasksMayNotBeAchievable() throws Exception {
		Goal goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		assertTrue(goal.isAndDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, (new ExecutionTimeSec()), 16);

		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, (new ExecutionTimeSec()), 11);

		goal.addDependency(task1);
		goal.addDependency(task2);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		Plan plan = goal.isAchievable(fullContext, interp);
		assertEquals(null, plan);
	}

	@Test
	public void aGoalOrDecomposedWithTwoTasksMayBeAchievable() throws Exception {
		Goal goal = new Goal(Goal.OR_DECOMPOSITION);
		assertTrue(goal.isOrDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, (new ExecutionTimeSec()), 13);

		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, (new ExecutionTimeSec()), 11);

		goal.addDependency(task1);
		goal.addDependency(task2);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		Plan plan = goal.isAchievable(fullContext, interp);
		assertEquals(1, plan.getTasks().size());
	}

	@Test
	public void aGoalOrDecomposedWithTwoTasksMayBeAchievableAtOnlyOneBranch() throws Exception {
		Goal goal = new Goal(Goal.OR_DECOMPOSITION);
		assertTrue(goal.isOrDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, (new ExecutionTimeSec()), 16);

		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, (new ExecutionTimeSec()), 11);

		goal.addDependency(task1);
		goal.addDependency(task2);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		Plan plan = goal.isAchievable(fullContext, interp);
		assertTrue(plan.getTasks().contains(task2));
		assertTrue(!plan.getTasks().contains(task1));
	}

	@Test
	public void aGoalOrDecomposedWithTwoTasksMayNotBeAchievable() throws Exception {
		Goal goal = new Goal(Goal.OR_DECOMPOSITION);

		Task task1 = new Task();
		Task task2 = new Task();
		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		QualityConstraint qc = new QualityConstraint(current, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, (new ExecutionTimeSec()), 16);

		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, (new ExecutionTimeSec()), 17);

		goal.addDependency(task1);
		goal.addDependency(task2);

		goal.setIdentifier("Root");
		goal.addApplicableContext(current);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		Plan plan = goal.isAchievable(fullContext, interp);
		assertTrue(goal.isOrDecomposition());
		assertEquals(null, plan);
	}

	@Test
	public void testApplicableDeps() {
		Pragmatic goal = new Pragmatic(Goal.PARALLEL_AND_DECOMPOSITION);

		Task task = new Task();
		Context context = new Context("C1");
		Context wrongContext = new Context("C2");
		HashSet<Context> current = new HashSet<Context>();

		QualityConstraint qc = new QualityConstraint(context, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task.addApplicableContext(context);
		task.setProvidedQuality(context, (new ExecutionTimeSec()), 13);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addApplicableContext(context);
		goal.getInterpretation().addQualityConstraint(qc);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		current.add(wrongContext);
		assertEquals(null, goal.isAchievable(current, interp));

		current.add(context);
		assertEquals(1, goal.isAchievable(current, interp).getTasks().size());

	}

	@Test
	public void testGetApplicableQC() {
		Pragmatic goal = new Pragmatic(Goal.PARALLEL_AND_DECOMPOSITION);

		Task task = new Task();
		Context context = new Context("C1");
		Context anotherContext = new Context("C2");

		HashSet<Context> fullContext = new HashSet<Context>();

		QualityConstraint qc = new QualityConstraint(context, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);
		QualityConstraint stricter = new QualityConstraint(anotherContext, (new ExecutionTimeSec()), 10,
				Comparison.LESS_OR_EQUAL_TO);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addApplicableContext(context);
		goal.getInterpretation().addQualityConstraint(qc);
		goal.getInterpretation().addQualityConstraint(stricter);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

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
		Pragmatic goal = new Pragmatic(Goal.PARALLEL_AND_DECOMPOSITION);

		Task task = new Task();
		Context context = new Context("C1");
		Context anotherContext = new Context("C2");

		HashSet<Context> fullContext = new HashSet<Context>();

		QualityConstraint qc = new QualityConstraint(context, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);
		QualityConstraint stricter = new QualityConstraint(anotherContext, (new ExecutionTimeSec()), 10,
				Comparison.LESS_OR_EQUAL_TO);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addApplicableContext(context);
		goal.getInterpretation().addQualityConstraint(qc);
		goal.getInterpretation().addQualityConstraint(stricter);

		assertEquals(stricter, qc.stricterQC(stricter));

		fullContext.add(context);
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(qc));

		fullContext.add(anotherContext);
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(stricter));

	}

	@Test
	public void shouldIncludeNonApplicableContexts() {
		Pragmatic goal = new Pragmatic(Goal.PARALLEL_AND_DECOMPOSITION);

		Task task = new Task();
		Context context = new Context("C1");
		Context wrongContext = new Context("C2");
		HashSet<Context> current = new HashSet<Context>();

		QualityConstraint qc = new QualityConstraint(context, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		task.addApplicableContext(context);
		task.setProvidedQuality(context, (new ExecutionTimeSec()), 13);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addNonApplicableContext(wrongContext);
		goal.getInterpretation().addQualityConstraint(qc);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

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
	public void shouldAddSeveralContextsAtOnce() {
		Context context1 = new Context("C1");
		Context context2 = new Context("C2");

		Task task = new Task();
		int originalSize = task.getApplicableContext().size();
		HashSet<Context> set = new HashSet<Context>();

		set.add(context1);
		set.add(context2);

		task.addApplicableContext(set);
		// null is always an applicable context
		assertEquals(2, task.getApplicableContext().size() - originalSize);
	}
}

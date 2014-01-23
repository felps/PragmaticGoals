package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class RefinementTest {

	@Test
	public void shouldReturnMyType() {
		Refinement goal = new Goal(false);
		Task task = new Task();
		Delegation delegation = new Delegation();

		assertEquals(Refinement.GOAL, goal.myType());
		
		assertEquals(Refinement.TASK, task.myType());
		
		assertEquals(Refinement.DELEGATION, delegation.myType());
	}
	
	@Test
	public void shouldBeApplicable() throws Exception {
		Refinement goal = new Goal(false);
		Task task = new Task();
		Delegation delegation = new Delegation();
		
		Context context = new Context();
		
		task.setApplicableContext(context);
		goal.setApplicableContext(context);
		delegation.setApplicableContext(context);
		
		assertTrue(goal.isApplicable(context));
		assertTrue(task.isApplicable(context));
		assertTrue(delegation.isApplicable(context));
	}

	@Test
	public void shouldBeNotApplicable() throws Exception {
		Refinement goal = new Goal(false);
		Task task = new Task();
		Delegation delegation = new Delegation();
		
		Context context = new Context();
		
		task.setApplicableContext(context);
		goal.setApplicableContext(context);
		delegation.setApplicableContext(context);
		
		Context wrongContext = new Context();
		
		assertFalse(goal.isApplicable(wrongContext));
		assertFalse(task.isApplicable(wrongContext));
		assertFalse(delegation.isApplicable(wrongContext));
	}

	@Test
	public void aTaskShouldBeAchievable() throws Exception {
		Task task = new Task();
		Context current = new Context();
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		
		task.setApplicableContext(current);
		task.setProvidedQuality(current, Metric.SECONDS, 12);
		
		assertEquals(task, task.isAchievable(current, qc));
	}
	
	@Test
	public void aTaskMayNotBeAchievable() throws Exception {
		Task task = new Task();
		Context current = new Context();
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		
		task.setApplicableContext(current);
		task.setProvidedQuality(current, Metric.SECONDS, 16);
		
		assertEquals(null, task.isAchievable(current, qc));
	}
	
	@Test
	public void aNonApplicableRootGoalIsNotAchievable() throws Exception {
		Goal goal = new Goal(false);
		Context current = new Context();
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);
		
		assertEquals(null, goal.isAchievable((new Context()), qc));
	}
	
	@Test
	public void aGoalWithATaskMayBeAchievable() throws Exception {
		Goal goal = new Goal(false);

		Task task = new Task();
		Context current = new Context();
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task.setApplicableContext(current);
		task.setProvidedQuality(current, Metric.SECONDS, 13);
		
		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Refinement plan = goal.isAchievable(current, qc);
		System.out.println(plan.getIdentifier());
		assertTrue("Root".contentEquals(plan.getIdentifier()));
	}

	@Test
	public void aGoalAndDecomposedWithTwoTasksMayBeAchievable() throws Exception {
		Goal goal = new Goal(false);
		assertTrue(goal.isAndDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();
		Context current = new Context();
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.setApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 13);
		
		task2.setApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
		goal.addDependency(task1);
		goal.addDependency(task2);
		
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Refinement plan = goal.isAchievable(current, qc);
		System.out.println(plan.getIdentifier());
		assertTrue("Root".contentEquals(plan.getIdentifier()));
	}

	@Test
	public void aGoalAndDecomposedWithTwoTasksMayNotBeAchievable() throws Exception {
		Goal goal = new Goal(false);
		assertTrue(goal.isAndDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();
		Context current = new Context();
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.setApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 16);
		
		task2.setApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
		goal.addDependency(task1);
		goal.addDependency(task2);
		
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Refinement plan = goal.isAchievable(current, qc);
		assertEquals(null, plan);
	}
	
	@Test
	public void aGoalOrDecomposedWithTwoTasksMayBeAchievable() throws Exception {
		Goal goal = new Goal(true);
		assertTrue(goal.isOrDecomposition());
		
		Task task1 = new Task();
		Task task2 = new Task();
		Context current = new Context();
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.setApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 13);
		
		task2.setApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
		goal.addDependency(task1);
		goal.addDependency(task2);
		
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Refinement plan = goal.isAchievable(current, qc);
		System.out.println(plan.getIdentifier());
		assertTrue("Root".contentEquals(plan.getIdentifier()));
	}

	@Test
	public void aGoalOrDecomposedWithTwoTasksMayBeAchievableAtOnlyOneBranch() throws Exception {
		Goal goal = new Goal(true);
		assertTrue(goal.isOrDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();
		Context current = new Context();
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.setApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 16);
		
		task2.setApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
		goal.addDependency(task1);
		goal.addDependency(task2);
		
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Refinement plan = goal.isAchievable(current, qc);
		System.out.println(plan.getIdentifier());
		assertTrue("Root".contentEquals(plan.getIdentifier()));
	}

	@Test
	public void aGoalOrDecomposedWithTwoTasksMayNotBeAchievable() throws Exception {
		Goal goal = new Goal(true);

		Task task1 = new Task();
		Task task2 = new Task();
		Context current = new Context();
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.setApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 16);
		
		task2.setApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 17);
		
		goal.addDependency(task1);
		goal.addDependency(task2);
		
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Refinement plan = goal.isAchievable(current, qc);
		assertTrue(goal.isOrDecomposition());
		assertEquals(null, plan);
	}

	@Test
	public void shouldCloneAGoal() throws Exception {
		Goal goal = new Goal(false);

		Task task = new Task();
		Context current = new Context();
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task.setApplicableContext(current);
		task.setProvidedQuality(current, Metric.SECONDS, 13);
		
		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		goal.setQualityConstraint(qc);
		
		Refinement clonedGoal = goal.cloneWithoutDependencies();
		 
		assertEquals(qc, clonedGoal.getQualityConstraint());
		assertEquals(current, clonedGoal.getApplicableContext());
		assertTrue("Root".contentEquals(clonedGoal.getIdentifier()));
	}
	
}

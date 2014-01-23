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
	
	
}

package cgm;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

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
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		task.setApplicableContext(current);
		goal.setApplicableContext(current);
		delegation.setApplicableContext(current);
		
		assertTrue(goal.isApplicable(fullContext));
		assertTrue(task.isApplicable(fullContext));
		assertTrue(delegation.isApplicable(fullContext));
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
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(wrongContext);
		
		
		assertFalse(goal.isApplicable(fullContext));
		assertFalse(task.isApplicable(fullContext));
		assertFalse(delegation.isApplicable(fullContext));
	}

	@Test
	public void aTaskShouldBeAchievable() throws Exception {
		Task task = new Task();
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		
		task.setApplicableContext(current);
		task.setProvidedQuality(current, Metric.SECONDS, 12);
		
		assertTrue(task.isAchievable(fullContext, qc).getTasks().contains(task));
	}
	
	@Test
	public void aTaskMayNotBeAchievable() throws Exception {
		Task task = new Task();
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		
		task.setApplicableContext(current);
		task.setProvidedQuality(current, Metric.SECONDS, 16);
		
		assertEquals(null, task.isAchievable(fullContext, qc));
	}
	
	@Test
	public void aNonApplicableRootGoalIsNotAchievable() throws Exception {
		Goal goal = new Goal(false);
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);
		
		assertEquals(null, goal.isAchievable(fullContext, qc));
	}
	
	@Test
	public void aGoalWithATaskMayBeAchievable() throws Exception {
		Goal goal = new Goal(false);

		Task task = new Task();
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task.setApplicableContext(current);
		task.setProvidedQuality(current, Metric.SECONDS, 13);
		
		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Plan plan = goal.isAchievable(fullContext, qc);
		assertEquals(1, plan.getTasks().size());
	}

	@Test
	public void aGoalAndDecomposedWithTwoTasksMayBeAchievable() throws Exception {
		Goal goal = new Goal(false);
		assertTrue(goal.isAndDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.setApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 13);
		
		task2.setApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
		goal.addDependency(task1);
		goal.addDependency(task2);
		
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Plan plan = goal.isAchievable(fullContext, qc);
		assertEquals(2, plan.getTasks().size());
	}

	@Test
	public void aGoalAndDecomposedWithTwoTasksMayNotBeAchievable() throws Exception {
		Goal goal = new Goal(false);
		assertTrue(goal.isAndDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.setApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 16);
		
		task2.setApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
		goal.addDependency(task1);
		goal.addDependency(task2);
		
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Plan plan = goal.isAchievable(fullContext, qc);
		assertEquals(null, plan);
	}
	
	@Test
	public void aGoalOrDecomposedWithTwoTasksMayBeAchievable() throws Exception {
		Goal goal = new Goal(true);
		assertTrue(goal.isOrDecomposition());
		
		Task task1 = new Task();
		Task task2 = new Task();
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.setApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 13);
		
		task2.setApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
		goal.addDependency(task1);
		goal.addDependency(task2);
		
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Plan plan = goal.isAchievable(fullContext, qc);
		assertEquals(1, plan.getTasks().size());
	}

	@Test
	public void aGoalOrDecomposedWithTwoTasksMayBeAchievableAtOnlyOneBranch() throws Exception {
		Goal goal = new Goal(true);
		assertTrue(goal.isOrDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.setApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 16);
		
		task2.setApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
		goal.addDependency(task1);
		goal.addDependency(task2);
		
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Plan plan = goal.isAchievable(fullContext, qc);
		//assertTrue("Root".contentEquals(plan.getIdentifier()));
	}

	@Test
	public void aGoalOrDecomposedWithTwoTasksMayNotBeAchievable() throws Exception {
		Goal goal = new Goal(true);

		Task task1 = new Task();
		Task task2 = new Task();
		Context current = new Context();
		HashSet<Context> fullContext= new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.setApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 16);
		
		task2.setApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 17);
		
		goal.addDependency(task1);
		goal.addDependency(task2);
		
		goal.setIdentifier("Root");
		goal.setApplicableContext(current);
		
		Plan plan = goal.isAchievable(fullContext, qc);
		assertTrue(goal.isOrDecomposition());
		assertEquals(null, plan);
	}

	@Test
	public void testApplicableDeps(){
		Goal goal = new Goal(false);

		Task task = new Task();
		Context context = new Context();
		Context wrongContext = new Context();
		HashSet<Context> current = new HashSet<Context>();
		current.add(wrongContext);
		
		QualityConstraint qc = new QualityConstraint(context, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task.setApplicableContext(context);
		task.setProvidedQuality(context, Metric.SECONDS, 13);
		
		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.setApplicableContext(context);
		goal.addQualityConstraint(qc);
		
		assertEquals(null, goal.isAchievable(current, qc));
		
		current.add(context);
		assertEquals(1, goal.isAchievable(current, qc).getTasks().size());
		

	}
	
	@Test
	public void testGetApplicableQC(){
		Goal goal = new Goal(false);

		Task task = new Task();
		Context context = new Context();
		Context anotherContext = new Context();
		
		HashSet<Context> fullContext = new HashSet<Context>();
		
		QualityConstraint qc = new QualityConstraint(context, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		QualityConstraint stricter = new QualityConstraint(anotherContext, Metric.SECONDS,10,Comparison.LESS_OR_EQUAL_TO);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.setApplicableContext(context);
		goal.addQualityConstraint(qc);
		goal.addQualityConstraint(stricter);
		
		assertEquals(null, goal.isAchievable(fullContext, qc));
		
		fullContext.add(context);
		assertEquals(qc, goal.getQualityConstraint(fullContext));
		
		fullContext.add(anotherContext);

	}
	
	@Test
	public void shouldThereBeMoreThanOneApplicableQCreturnTheStricterOne(){
		Goal goal = new Goal(false);

		Task task = new Task();
		Context context = new Context();
		Context anotherContext = new Context();
		
		HashSet<Context> fullContext = new HashSet<Context>();
		
		QualityConstraint qc = new QualityConstraint(context, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		QualityConstraint stricter = new QualityConstraint(anotherContext, Metric.SECONDS,10,Comparison.LESS_OR_EQUAL_TO);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.setApplicableContext(context);
		goal.addQualityConstraint(qc);
		goal.addQualityConstraint(stricter);
		
		assertEquals(stricter, qc.stricterQC(stricter));
		
		
		fullContext.add(context);
		assertEquals(qc, goal.getQualityConstraint(fullContext));
		
		fullContext.add(anotherContext);
		assertEquals(stricter, goal.getQualityConstraint(fullContext));

	}
/*	@Test
	public void shouldCloneAGoal() throws Exception {
		Goal goal = new Goal(false);

		Task task = new Task();
		Context context = new Context();
		HashSet<Context> current = new HashSet<Context>();
		current.add(context);
		
		QualityConstraint qc = new QualityConstraint(context, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task.setApplicableContext(context);
		task.setProvidedQuality(context, Metric.SECONDS, 13);
		
		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.setApplicableContext(context);
		goal.setQualityConstraint(qc);
		
		Refinement clonedGoal = goal.cloneWithoutDependencies();
		 
		assertEquals(qc, clonedGoal.getQualityConstraint(current));
		assertEquals(current, clonedGoal.getApplicableContext());
		assertTrue("Root".contentEquals(clonedGoal.getIdentifier()));
	}
	*/
}

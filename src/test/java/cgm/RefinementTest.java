package cgm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

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
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		
		task.addApplicableContext(current);
		task.setProvidedQuality(current, Metric.SECONDS, 12);
			
		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);
		
		assertTrue(task.isAchievable(fullContext, interp).getTasks().contains(task));
	}
	
	@Test
	public void aTaskMayNotBeAchievable() throws Exception {
		Task task = new Task();
		
		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		
		task.addApplicableContext(current);
		task.setProvidedQuality(current, Metric.SECONDS, 16);

		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		assertEquals(null, task.isAchievable(fullContext, interp));
	}
	
	@Test
	public void aNonApplicableRootGoalIsNotAchievable() throws Exception {
		Goal goal = new Goal(false);
		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);
		goal.addApplicableContext((new Context("C2")));
		
		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

		assertEquals(null, goal.isAchievable(fullContext, interp));
	}
	
	@Test
	public void aGoalWithATaskMayBeAchievable() throws Exception {
		Goal goal = new Goal(false);

		Task task = new Task();
		
		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);

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
		Goal goal = new Goal(false);
		assertTrue(goal.isAndDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();
		
		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 13);
		
		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
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
	public void aGoalAndDecomposedWithTwoTasksMayNotBeAchievable() throws Exception {
		Goal goal = new Goal(false);
		assertTrue(goal.isAndDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();
		
		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 16);
		
		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
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
		Goal goal = new Goal(true);
		assertTrue(goal.isOrDecomposition());
		
		Task task1 = new Task();
		Task task2 = new Task();
		
		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 13);
		
		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
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
		Goal goal = new Goal(true);
		assertTrue(goal.isOrDecomposition());

		Task task1 = new Task();
		Task task2 = new Task();
		
		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 16);
		
		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 11);
		
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
		Goal goal = new Goal(true);

		Task task1 = new Task();
		Task task2 = new Task();
		Context current = new Context("C1");
		HashSet<Context> fullContext= new HashSet<Context>();
		fullContext.add(current);
		
		QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task1.addApplicableContext(current);
		task1.setProvidedQuality(current, Metric.SECONDS, 16);
		
		task2.addApplicableContext(current);
		task2.setProvidedQuality(current, Metric.SECONDS, 17);
		
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
	public void testApplicableDeps(){
		Pragmatic goal = new Pragmatic(false);

		Task task = new Task();
		Context context = new Context("C1");
		Context wrongContext = new Context("C2");
		HashSet<Context> current = new HashSet<Context>();
		
		QualityConstraint qc = new QualityConstraint(context, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);

		task.addApplicableContext(context);
		task.setProvidedQuality(context, Metric.SECONDS, 13);
		
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
	public void testGetApplicableQC(){
		Pragmatic goal = new Pragmatic(false);

		Task task = new Task();
		Context context = new Context("C1");
		Context anotherContext = new Context("C2");
		
		HashSet<Context> fullContext = new HashSet<Context>();
		
		QualityConstraint qc = new QualityConstraint(context, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		QualityConstraint stricter = new QualityConstraint(anotherContext, Metric.SECONDS,10,Comparison.LESS_OR_EQUAL_TO);

		goal.addDependency(task);
		goal.setIdentifier("Root");
		goal.addApplicableContext(context);
		goal.getInterpretation().addQualityConstraint(qc);
		goal.getInterpretation().addQualityConstraint(stricter);
		
		Interpretation interp = new Interpretation();
		interp.addQualityConstraint(qc);
		
		fullContext.add(context);
		//assertEquals(null, goal.isAchievable(fullContext, interp));
		
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(qc));
		
		fullContext.add(anotherContext);
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(qc));
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(stricter));
		
		fullContext.remove(context);
		assertTrue(!goal.getInterpretation().getQualityConstraints(fullContext).contains(qc));
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(stricter));
		
	}
	
	@Test
	public void shouldThereBeMoreThanOneApplicableQCreturnTheStricterOne() throws DifferentMetricsException{
		Pragmatic goal = new Pragmatic(false);

		Task task = new Task();
		Context context = new Context("C1");
		Context anotherContext = new Context("C2");
		
		HashSet<Context> fullContext = new HashSet<Context>();
		
		QualityConstraint qc = new QualityConstraint(context, Metric.SECONDS,15,Comparison.LESS_OR_EQUAL_TO);
		QualityConstraint stricter = new QualityConstraint(anotherContext, Metric.SECONDS,10,Comparison.LESS_OR_EQUAL_TO);

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

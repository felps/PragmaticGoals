package gm.cgm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import metrics.ExecutionTimeSec;

import org.junit.Test;

import workflow.datatypes.Workflow;
import workflow.datatypes.WorkflowNode;

public class PlanTest {

	@Test
	public void shouldAddOneTaskAsDependencyOnConstructor() {
		Task task = new Task();
		task.setIdentifier("Task 1");
		Plan plan = new Plan(task);

		assertTrue(plan.getTasks().contains(task));
	}

	@Test
	public void shouldConcatTwoPlans() {
		Task task1 = new Task();
		task1.setIdentifier("Task 1");
		Plan plan1 = new Plan(task1);

		Task task2 = new Task();
		task2.setIdentifier("Task 2");
		Plan plan2 = new Plan(task2);

		plan1.add(plan2);
		assertTrue(plan1.getTasks().contains(task1));
		assertTrue(plan1.getTasks().contains(task2));
		assertEquals(2, plan1.getTasks().size());
	}

	@Test
	public void shouldConvertASingleTaskToSimpleWorkflow() throws EmptyWorkflow {

		Task task = new Task();
		task.setIdentifier("Task 1");

		Plan plan = new Plan(task);

		Workflow wf = plan.convertToWorkflow();

		assertEquals(1, wf.getStart().getEdges().size());
		assertEquals(1, wf.getLastNodes().size());
		assertEquals(1, wf.getNodes().size());
		assertEquals("Task 1", wf.getNodes().get(0).getName());
	}

	@Test
	public void shouldConvertSerialAndDecompositionIntoASingleNodeWorkflow() throws EmptyWorkflow {

		Task task1 = new Task();
		task1.setIdentifier("Task 1");

		Goal goal = new Goal(Goal.SERIAL_AND_DECOMPOSITION);
		goal.addDependency(task1);

		Plan plan = new Plan();
		plan.add(goal);

		Workflow wf = plan.convertToWorkflow();

		assertEquals(1, wf.getStart().getEdges().size());
		assertEquals(1, wf.getLastNodes().size());
		assertEquals(1, wf.getNodes().size());
		assertEquals("Task 1", wf.getNodes().get(0).getName());
	}

	@Test
	public void shouldCreateALinearAWorkflow() throws EmptyWorkflow {
		Task task1 = new Task();
		task1.setIdentifier("Task 1");

		Task task2 = new Task();
		task2.setIdentifier("Task 2");

		Goal goal = new Goal(Goal.SERIAL_AND_DECOMPOSITION);
		goal.addDependency(task1);
		goal.addDependency(task2);

		Plan plan = new Plan();
		plan.add(goal);

		Workflow wf = plan.convertToWorkflow();

		wf.printWorkflow();
		assertEquals(1, wf.getStart().getEdges().size());
		assertEquals(2, wf.getNodes().size());
		assertEquals(1, wf.getLastNodes().size());
		assertEquals("Task 1", ((WorkflowNode) wf.getStart().getEdges().toArray()[0]).getName());
		assertEquals("Task 2", ((WorkflowNode) wf.getLastNodes().toArray()[0]).getName());
		assertEquals(1, wf.getNodes().get(0).getEdges().size());
		assertEquals(0, wf.getNodes().get(1).getEdges().size());

	}

	@Test
	public void shouldCreateAParallelWorkflow() throws EmptyWorkflow {
		Plan plan = new Plan();

		Task task1 = new Task();
		task1.setIdentifier("Task 1");

		Task task2 = new Task();
		task2.setIdentifier("Task 2");

		Goal goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		goal.addDependency(task1);
		goal.addDependency(task2);

		plan.add(goal);

		Workflow wf = plan.convertToWorkflow();

		wf.printWorkflow();
		assertEquals(2, wf.getStart().getEdges().size());
		assertEquals(2, wf.getNodes().size());
		assertEquals(2, wf.getLastNodes().size());
		assertEquals(0, wf.getNodes().get(0).getEdges().size());
		assertEquals(0, wf.getNodes().get(1).getEdges().size());

	}

	@Test
	public void shouldCreateALinearFollowedByAParallelWorkflow() throws EmptyWorkflow {
		Plan plan = new Plan();
		Goal goal1 = new Goal(Goal.SERIAL_AND_DECOMPOSITION);
		Goal goal2 = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Task task1 = new Task();
		Task task2 = new Task();
		Task task3 = new Task();
		Task task4 = new Task();

		task1.setIdentifier("Task 1");
		task2.setIdentifier("Task 2");
		task3.setIdentifier("Task 3");
		task4.setIdentifier("Task 4");

		goal1.addDependency(task1);
		goal1.addDependency(task2);
		goal1.addDependency(goal2);

		goal2.addDependency(task3);
		goal2.addDependency(task4);

		plan.add(goal1);

		Workflow wf = plan.convertToWorkflow();

		wf.printWorkflow();
		assertEquals(1, wf.getStart().getEdges().size());
		assertEquals(4, wf.getNodes().size());
		assertEquals(2, wf.getLastNodes().size());

		assertEquals("Task 1", wf.getNodes().get(0).getName());
		assertEquals(1, wf.getNodes().get(0).getEdges().size());

		assertEquals("Task 2", wf.getNodes().get(1).getName());
		assertEquals(2, wf.getNodes().get(1).getEdges().size());

		assertEquals("Task 3", wf.getNodes().get(2).getName());
		assertEquals(0, wf.getNodes().get(2).getEdges().size());

		assertEquals("Task 4", wf.getNodes().get(3).getName());
		assertEquals(0, wf.getNodes().get(3).getEdges().size());

	}

	@Test
	public void aSingleTaskShouldReturnQoS() {
		Task t1 = new Task();

		t1.setIdentifier("T1");
		t1.setProvidedQuality(null, new ExecutionTimeSec(), 15);

		Plan plan = new Plan(t1);

		assertEquals(15.0, plan.getQoS().get(new ExecutionTimeSec()).floatValue(), 0.01);
	}

	@Test
	public void aParallelGoalShouldReturnTheParallelCompositeQos() {

		Task t1 = new Task();
		t1.setIdentifier("T1");
		t1.setProvidedQuality(null, new ExecutionTimeSec(), 15);

		Task t2 = new Task();
		t2.setIdentifier("T2");
		t2.setProvidedQuality(null, new ExecutionTimeSec(), 13);

		Goal root = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		root.addDependency(t1);
		root.addDependency(t2);

		Plan plan = new Plan();
		plan.add(root);

		assertEquals(15.0, plan.getQoS().get(new ExecutionTimeSec()).floatValue(), 0.01);
	}

	@Test
	public void aSerialGoalShouldReturnTheParallelCompositeQos() {

		Task t1 = new Task();
		t1.setIdentifier("T1");
		t1.setProvidedQuality(null, new ExecutionTimeSec(), 15);

		Task t2 = new Task();
		t2.setIdentifier("T2");
		t2.setProvidedQuality(null, new ExecutionTimeSec(), 13);

		Goal root = new Goal(Goal.SERIAL_AND_DECOMPOSITION);
		root.addDependency(t1);
		root.addDependency(t2);

		Plan plan = new Plan();
		plan.add(root);

		assertEquals(28.0, plan.getQoS().get(new ExecutionTimeSec()).floatValue(), 0.01);
	}

	@Test
	public void theOrderShouldntMatterToTheQoS() {

		Task t1 = new Task();
		t1.setIdentifier("T1");
		t1.setProvidedQuality(null, new ExecutionTimeSec(), 15);

		Task t2 = new Task();
		t2.setIdentifier("T2");
		t2.setProvidedQuality(null, new ExecutionTimeSec(), 13);

		Plan plan = new Plan();
		plan.add(t1);
		plan.add(t2);

		assertEquals(15.0, plan.getQoS().get(new ExecutionTimeSec()).floatValue(), 0.01);

		plan = new Plan();
		plan.add(t2);
		plan.add(t1);

		assertEquals(15.0, plan.getQoS().get(new ExecutionTimeSec()).floatValue(), 0.01);
	}

	@Test
	public void refinementsAddedToPlanShouldBeAddedAsAParallelDecompAndThusReturnTheParallelCompositeQos() {

		Task t1 = new Task();
		t1.setIdentifier("T1");
		t1.setProvidedQuality(null, new ExecutionTimeSec(), 15);

		Task t2 = new Task();
		t2.setIdentifier("T2");
		t2.setProvidedQuality(null, new ExecutionTimeSec(), 13);

		Plan plan = new Plan();
		plan.add(t1);
		plan.add(t2);

		assertEquals(15.0, plan.getQoS().get(new ExecutionTimeSec()).floatValue(), 0.01);
	}
}

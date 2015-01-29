package cgm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import workflow.datatypes.Workflow;
import workflow.datatypes.WorkflowNode;

public class CGMTest {

	@Test
	public void shouldReturnRootNode() {
		CGM cgm = new CGM();
		Refinement root = new Goal(Goal.OR_DECOMPOSITION);
		cgm.setRoot(root);
		assertEquals(cgm.getRoot(), root);
	}

	@Test
	public void shouldConvertASingleTaskToSimpleWorkflow() throws EmptyWorkflow {
		CGM cgm = new CGM();
		Task task = new Task();
		task.setIdentifier("Task 1");

		cgm.setRoot(task);

		Workflow wf = cgm.convertToWorkflow(null);

		assertEquals(1, wf.getStart().getEdges().size());
		assertEquals(1, wf.getLastNodes().size());
		assertEquals(1, wf.getNodes().size());
		assertEquals("Task 1", wf.getNodes().get(0).getName());
	}

	@Test
	public void shouldCreateALinearAWorkflow() throws EmptyWorkflow {
		CGM cgm = new CGM();
		Task task1 = new Task();
		task1.setIdentifier("Task 1");

		Task task2 = new Task();
		task2.setIdentifier("Task 2");

		Goal goal = new Goal(Goal.SERIAL_AND_DECOMPOSITION);
		goal.addDependency(task1);
		goal.addDependency(task2);

		cgm.setRoot(goal);

		assertTrue(cgm.isAchievable(null, null) != null);
		Workflow wf = cgm.convertToWorkflow(null);

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
		CGM cgm = new CGM();
		Task task1 = new Task();
		task1.setIdentifier("Task 1");

		Task task2 = new Task();
		task2.setIdentifier("Task 2");

		Goal goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		goal.addDependency(task1);
		goal.addDependency(task2);

		cgm.setRoot(goal);

		assertTrue(cgm.isAchievable(null, null) != null);
		Workflow wf = cgm.convertToWorkflow(null);

		wf.printWorkflow();
		assertEquals(2, wf.getStart().getEdges().size());
		assertEquals(2, wf.getNodes().size());
		assertEquals(2, wf.getLastNodes().size());
		assertEquals(0, wf.getNodes().get(0).getEdges().size());
		assertEquals(0, wf.getNodes().get(1).getEdges().size());

	}

	@Test
	public void shouldCreateALinearFollowedByAParallelWorkflow() throws EmptyWorkflow {
		CGM cgm = new CGM();
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

		cgm.setRoot(goal1);
		assertTrue(cgm.isAchievable(null, null) != null);

		Workflow wf = cgm.convertToWorkflow(null);

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
}

package workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import workflow.datatype.Workflow;
import workflow.datatype.WorkflowNode;

public class WorkflowGeneration {

	@Test
	public void shouldCreateAWorkflow() {
		WorkflowFactory wfFactory = new WorkflowFactory();
		int nodes = 1, averageEdgesPerNode = 1;
		Workflow wf = null;

		for (nodes = 1; nodes <= 100; nodes++) {

			wf = wfFactory.createRandomWorkflow(nodes, averageEdgesPerNode);
			assertTrue(wf != null);
			assertEquals(nodes, wf.getNodes().size());

			// TODO Verify average edge density
		}
	}

	@Test
	public void shouldCreateAWorkflowWithMoreThanOneEdge() {
		WorkflowFactory wfFactory = new WorkflowFactory();
		int nodes = 1, averageEdgesPerNode = 3;
		Workflow wf = null;

		for (nodes = 1; nodes <= 100; nodes++) {

			wf = wfFactory.createRandomWorkflow(nodes, averageEdgesPerNode);
			assertTrue(wf != null);
			assertEquals(nodes, wf.getNodes().size());

			// TODO Verify average edge density
		}
	}

	@Test
	public void workflowShouldBeConnected() {
		WorkflowFactory wfFactory = new WorkflowFactory();
		int nodes = 100, averageEdgesPerNode = 1;
		Workflow wf = null;

		wf = wfFactory.createRandomWorkflow(nodes, averageEdgesPerNode);
		int unconnectedNodes = 0; // only the root may not have any edge
									// pointing towards it

		for (WorkflowNode node : wf.getNodes()) {
			int found = 0;
			for (WorkflowNode origin : wf.getNodes()) {
				if (origin.getEdges().contains(node))
					found = 1;
			}
			if (found == 0)
				unconnectedNodes++;
		}
		assertEquals(1, unconnectedNodes); // Only the root node may not have
											// any incoming edges
	}

}

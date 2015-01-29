package workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import workflow.WorkflowFactory;
import workflow.datatypes.Workflow;
import workflow.datatypes.WorkflowNode;

public class WorkflowGenerationTest {

	@Test
	public void shouldCreateAWorkflow() {
		WorkflowFactory wfFactory = new WorkflowFactory();
		int nodes = 1, averageEdgesPerNode = 1;
		Workflow wf = null;
		int edges = 0;
		for (nodes = 1; nodes <= 10; nodes++) {

			System.out.println("===============");
			wf = wfFactory.createRandomWorkflow(nodes, averageEdgesPerNode);
			assertTrue(wf != null);
			assertEquals(nodes, wf.getNodes().size());

			for (WorkflowNode node : wf.getNodes()) {
				edges += node.getEdges().size();
				System.out.println("Edges from node " + node.getName() + ": " + node.getEdges().size());
			}
			System.out.println("Total edges for " + nodes + " nodes: " + edges);
			// assertEquals(nodes - 1, edges);
			edges = 0;
		}
	}

	@Test
	public void shouldCreateAWorkflowWithMoreThanOneEdge() {
		WorkflowFactory wfFactory = new WorkflowFactory();
		int nodes = 1, averageEdgesPerNode = 3, edges = 0;

		Workflow wf = null;

		for (nodes = averageEdgesPerNode + 1; nodes <= 10; nodes++) {

			System.out.println("Test 1");
			wf = wfFactory.createRandomWorkflow(nodes, averageEdgesPerNode);
			assertTrue(wf != null);
			assertEquals(nodes, wf.getNodes().size());

			for (WorkflowNode node : wf.getNodes()) {
				edges += node.getEdges().size();
			}
			assertEquals("For " + nodes + " nodes, there should be " + (nodes - averageEdgesPerNode + 1)
					* averageEdgesPerNode + " edges.", (nodes - averageEdgesPerNode + 1) * averageEdgesPerNode, edges);
			edges = 0;
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

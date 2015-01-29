package workflow.datatypes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class WorkflowTest {

	@Test
	public void newNodesMustBeCreatedConnectedToStart() {
		WorkflowNode node1 = new WorkflowNode("Node 1");

		Workflow wf = new Workflow(node1);

		for (int i = 0; i < 100; i++) {
			WorkflowNode node2 = new WorkflowNode("Node 2");
			wf.addNode(node2);

			assertTrue(wf.getStart().getEdges().contains(node2));
		}
	}

	@Test
	public void newNodesMustBeCreatedConnectedToEnd() {
		WorkflowNode node1 = new WorkflowNode("Node 1");

		Workflow wf = new Workflow(node1);

		for (int i = 0; i < 100; i++) {
			WorkflowNode node2 = new WorkflowNode("Node 2");
			wf.addNode(node2);

			assertTrue(wf.getLastNodes().contains(node2));
		}
	}

	@Test
	public void nodesWithOutgoingEdgesMustNotBeConnectedToEnd() {
		WorkflowNode node1 = new WorkflowNode("Node 1");

		Workflow wf = new Workflow(node1);
		WorkflowNode node2 = new WorkflowNode("Node 2");
		WorkflowNode node3 = new WorkflowNode("Node 3");

		node1.addEdge(node2);
		node2.addEdge(node3);

		assertFalse(wf.getLastNodes().contains(node1));
		assertFalse(wf.getLastNodes().contains(node2));
	}

	@Test
	public void nodesWithIncomingEdgesMustNotBeConnectedToStart() {

		WorkflowNode node1 = new WorkflowNode("Node 1");
		WorkflowNode node2 = new WorkflowNode("Node 2");
		WorkflowNode node3 = new WorkflowNode("Node 3");

		Workflow wf = new Workflow(node1);

		node1.addEdge(node2);
		node2.addEdge(node3);

		assertFalse(wf.getStart().getEdges().contains(node2));
		assertFalse(wf.getStart().getEdges().contains(node3));
	}

	@Test
	public void everyNodeShouldBeConnectedToStartUnlessOtherwiseStated() {
		ArrayList<WorkflowNode> nodes = new ArrayList<WorkflowNode>();
		for (int i = 0; i < 10; i++) {
			nodes.add(new WorkflowNode("t" + i));
		}
		Workflow wf = new Workflow(nodes.get(0));
		for (int i = 1; i < nodes.size(); i++)
			wf.addNode(nodes.get(i));

		assertTrue(wf.getStart().getEdges().containsAll(nodes));

	}
}

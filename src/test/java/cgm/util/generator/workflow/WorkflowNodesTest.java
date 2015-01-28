package cgm.util.generator.workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cgm.util.generator.workflow.datatypes.WorkflowNode;

public class WorkflowNodesTest {

	@Test
	public void nodeMustHaveAName() {
		WorkflowNode node = new WorkflowNode("Node 1");
		assertEquals("Node 1", node.getName());
	}

	@Test
	public void nodeMustBeginWithoutEdges() {
		WorkflowNode node1 = new WorkflowNode("Node 1");

		assertEquals(0, node1.getEdges().size());
	}

	@Test
	public void nodeMustStoreEdges() {
		WorkflowNode node1 = new WorkflowNode("Node 1");
		WorkflowNode node2 = new WorkflowNode("Node 2");

		node1.addEdge(node2);

		assertEquals(1, node1.getEdges().size());
		assertTrue(node1.getEdges().contains(node2));
	}

}

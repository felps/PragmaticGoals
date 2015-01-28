package cgm.util.generator.workflow;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cgm.util.generator.workflow.datatypes.Workflow;
import cgm.util.generator.workflow.datatypes.WorkflowNode;

public class WorkflowTest {

	@Test
	public void newNodesMustBeCreatedConnected() {
		WorkflowNode node1 = new WorkflowNode("Node 1");

		Workflow wf = new Workflow(node1);

		for (int i = 0; i < 100; i++) {
			int found = 0;
			WorkflowNode node2 = new WorkflowNode("Node 2");
			wf.addNode(node2);

			for (WorkflowNode node : wf.getNodes()) {
				if (node.getEdges().contains(node2)) {
					found = 1;
				}
			}
			assertEquals(1, found);
		}
	}
}

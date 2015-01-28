package cgm.util.generator.workflow.datatypes;

import java.util.ArrayList;
import java.util.List;

public class Workflow {

	private ArrayList<WorkflowNode> nodes;

	public Workflow(WorkflowNode node1) {
		nodes = new ArrayList<WorkflowNode>();
		nodes.add(node1);
	}

	public List<WorkflowNode> getNodes() {
		return nodes;
	}

	public void addNode(WorkflowNode node2) {
		int randomNodeIndex = (int) (Math.random() * nodes.size());
		nodes.get(randomNodeIndex).addEdge(node2);
		nodes.add(node2);

	}
}

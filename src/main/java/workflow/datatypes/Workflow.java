package workflow.datatypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Workflow {

	private WorkflowNode startNode;
	private WorkflowNode endNode;

	private ArrayList<WorkflowNode> nodes;

	public Workflow(WorkflowNode node1) {
		startNode = new WorkflowNode("start");
		endNode = new WorkflowNode("end");
		nodes = new ArrayList<WorkflowNode>();
		nodes.add(node1);
	}

	public List<WorkflowNode> getNodes() {
		return nodes;
	}

	public void addNode(WorkflowNode node2) {
		nodes.add(node2);
		setStartEdges();
		setEndEdges();

	}

	private void setEndEdges() {
		HashSet<WorkflowNode> lastNodes = new HashSet<WorkflowNode>();

		for (WorkflowNode node : getNodes()) {
			if (node.getEdges().size() == 0)
				lastNodes.add(node);
		}

	}

	public WorkflowNode getStart() {
		return startNode;

	}

	public WorkflowNode getEnd() {
		return endNode;
	}

	private void setStartEdges() {
		startNode.getEdges().clear();

		HashSet<WorkflowNode> unconnected = new HashSet<WorkflowNode>();
		unconnected.addAll(getNodes());

		for (WorkflowNode node : getNodes()) {
			unconnected.removeAll(node.getEdges());
		}

		for (WorkflowNode unconnectedNode : unconnected) {
			startNode.addEdge(unconnectedNode);
		}
	}

}

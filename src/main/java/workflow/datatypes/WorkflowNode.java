package workflow.datatypes;

import java.util.HashSet;
import java.util.Set;

public class WorkflowNode {
	private String name;
	private HashSet<WorkflowNode> edges;

	public WorkflowNode(String name) {
		this.name = name;
		edges = new HashSet<WorkflowNode>();
	}

	public String getName() {
		return name;
	}

	public void addEdge(WorkflowNode node) {
		edges.add(node);
	}

	public void addEdges(Set<WorkflowNode> newEdges) {
		edges.addAll(newEdges);
	}

	public Set<WorkflowNode> getEdges() {
		return edges;
	}
}
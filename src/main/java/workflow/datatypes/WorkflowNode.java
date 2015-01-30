package workflow.datatypes;

import gm.cgm.Goal;
import gm.cgm.Refinement;
import gm.cgm.Task;

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

	public Set<WorkflowNode> getNodes() {

		HashSet<WorkflowNode> nodes = new HashSet<WorkflowNode>();

		for (WorkflowNode edge : edges) {
			nodes.addAll(edge.getNodes());
		}

		return nodes;
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

	public Refinement convertToGM() {
		Task task = new Task();
		task.setIdentifier(name);

		if (getEdges().isEmpty()) {

			return task;

		} else {

			Goal rootSerial = new Goal(Goal.SERIAL_AND_DECOMPOSITION);
			rootSerial.setIdentifier("Root Serial for Task " + name);
			Goal parallel = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
			parallel.setIdentifier("Parallel for Task " + name);

			rootSerial.addDependency(task);
			rootSerial.addDependency(parallel);

			for (WorkflowNode workflowNode : edges) {
				Refinement depRoot = workflowNode.convertToGM();
				parallel.addDependency(depRoot);
			}

			return rootSerial;
		}
	}

}

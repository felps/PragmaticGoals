package workflow.datatypes;

import gm.cgm.Goal;
import gm.cgm.Refinement;
import gm.cgm.Task;

import java.util.HashSet;
import java.util.Set;

public class WorkflowNode {
	private String name;
	private HashSet<WorkflowNode> outgoingEdges;
	public HashSet<WorkflowNode> getOutgoingEdges() {
		return outgoingEdges;
	}

	private HashSet<WorkflowNode> incomingEdges;

	public HashSet<WorkflowNode> getIncomingEdges() {
		return incomingEdges;
	}

	public WorkflowNode(String name) {
		this.name = name;
		outgoingEdges = new HashSet<WorkflowNode>();
		incomingEdges = new HashSet<WorkflowNode>();
	}

	public String getName() {
		return name;
	}

	public Set<WorkflowNode> getNodes() {

		HashSet<WorkflowNode> nodes = new HashSet<WorkflowNode>();
		nodes.add(this);
		for (WorkflowNode edge : outgoingEdges) {
			nodes.addAll(edge.getNodes());
		}

		return nodes;
	}

	public void addEdge(WorkflowNode node) {
		outgoingEdges.add(node);
		node.addIncomingEdge(this);
	}

	private void addIncomingEdge(WorkflowNode node) {
		incomingEdges.add(node);
	}

	public void addEdges(Set<WorkflowNode> newEdges) {
		outgoingEdges.addAll(newEdges);
	}

	public Set<WorkflowNode> getEdges() {
		return outgoingEdges;
	}

	public Refinement convertToGM() {
		Task task = new Task();
		task.setIdentifier(name);
		HashSet<WorkflowNode> completedNodes = new HashSet<WorkflowNode>();

		if (getEdges().isEmpty()) {
			return task;
		} else {

			Goal rootSerial = new Goal(Goal.SERIAL_AND_DECOMPOSITION);
			rootSerial.setIdentifier("Root Serial for Task " + name);
			Goal parallel = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
			parallel.setIdentifier("Parallel for Task " + name);

			// T1
			rootSerial.addDependency(task);
			completedNodes.add(this);

			// T2, T3
			rootSerial.addDependency(parallel);
			for (WorkflowNode edge : outgoingEdges) {
				if (getNodes().containsAll(edge.getIncomingEdges())) {
					Refinement depRoot = edge.convertToGM();
					parallel.addDependency(depRoot);
					// Add the task nodes from the workflow to the
					// completedNodes
					for (Task ble : depRoot.getTasks()) {
						completedNodes.add(new WorkflowNode(ble.getIdentifier()));
					}
				}
			}

			// T4
			// Find pending tasks
			HashSet<WorkflowNode> pendingNodes = new HashSet<WorkflowNode>();
			pendingNodes.addAll(getNodes());
			pendingNodes.removeAll(completedNodes);

			// Any node not already included in the CGM is pending. Out of
			// these, those that have had their dependencies satisfied by the
			// completed tasks are able to continue
			for (WorkflowNode pending : pendingNodes) {
				if (completedNodes.containsAll(pending.getIncomingEdges())) {
					System.out.println("Pending: " + pending.getName());
					Refinement depRoot = pending.convertToGM();
					rootSerial.addDependency(depRoot);
				}
			}

			return rootSerial;
		}
	}
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == this.getClass()) {
			if (((WorkflowNode) obj).getName().equals(this.getName()))
				return true;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}
}

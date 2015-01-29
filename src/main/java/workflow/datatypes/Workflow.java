package workflow.datatypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Workflow {

	private WorkflowNode startNode;
	private HashSet<WorkflowNode> lastNodes;

	private ArrayList<WorkflowNode> nodes;

	public Workflow() {
		setUp();

	}

	public Workflow(WorkflowNode node1) {
		setUp();
		addNode(node1);
	}

	private void setUp() {
		startNode = new WorkflowNode("start");
		lastNodes = new HashSet<WorkflowNode>();
		nodes = new ArrayList<WorkflowNode>();
	}

	public List<WorkflowNode> getNodes() {
		return nodes;
	}

	public void addNode(WorkflowNode node2) {
		nodes.add(node2);
		setStartEdges();
		setEndEdges();

	}

	public void addNodes(Collection<WorkflowNode> newNodes) {
		nodes.addAll(newNodes);
		setStartEdges();
		setEndEdges();

	}

	public void addEdge(WorkflowNode node1, WorkflowNode node2) throws WorkflowNodeNotFound {
		if (nodes.contains(node1) && nodes.contains(node2)) {
			node1.addEdge(node2);
			setStartEdges();
			setEndEdges();
		} else
			throw (new WorkflowNodeNotFound());
	}

	private void addEdges(WorkflowNode originNode, Set<WorkflowNode> edges) throws WorkflowNodeNotFound {
		for (WorkflowNode destinationNode : edges) {
			addEdge(originNode, destinationNode);
		}
	}

	private void setEndEdges() {
		lastNodes.clear();
		for (WorkflowNode node : getNodes()) {
			if (node.getEdges().size() == 0)
				lastNodes.add(node);
		}
	}

	public HashSet<WorkflowNode> getLastNodes() {
		return lastNodes;
	}

	public WorkflowNode getStart() {
		return startNode;

	}

	private void setStartEdges() {
		startNode.getEdges().clear();

		HashSet<WorkflowNode> unconnected = new HashSet<WorkflowNode>();
		unconnected.addAll(getNodes());

		for (WorkflowNode node : getNodes()) {
			unconnected.removeAll(node.getEdges());
		}

		startNode.addEdges(unconnected);
	}

	public Workflow concat(Workflow wf2) {
		Workflow concat = new Workflow();

		concat.addNodes(getNodes());
		concat.addNodes(wf2.getNodes());

		for (WorkflowNode workflowNode : getLastNodes()) {
			try {
				concat.addEdges(workflowNode, wf2.getStart().getEdges());
			} catch (WorkflowNodeNotFound e) {
				System.err.println("Error while including nodes - workflow.Workflow.java - concat method");
				e.printStackTrace();
			}
		}

		return concat;
	}

	public Workflow parallel(Workflow wf2) {
		Workflow parallel = new Workflow();

		parallel.addNodes(getNodes());
		parallel.addNodes(wf2.getNodes());

		return parallel;
	}

	public void printWorkflow() {
		System.out.println("==== Workflow ====\n\n");
		System.out.println("Starting nodes (" + startNode.getEdges().size() + ") ==>");
		for (WorkflowNode workflowNode : startNode.getEdges()) {
			System.out.println(workflowNode.getName());
		}
		System.out.println("<===\nNodes ==>");
		for (WorkflowNode workflowNode : nodes) {
			System.out.println(workflowNode.getName());
			for (WorkflowNode edge : workflowNode.getEdges()) {
				System.out.println(" |==> " + edge.getName());
			}
		}
	}
}

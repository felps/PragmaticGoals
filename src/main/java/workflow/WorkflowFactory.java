package workflow;

import workflow.datatypes.Workflow;
import workflow.datatypes.WorkflowNode;

public class WorkflowFactory {

	public Workflow createRandomWorkflow(int nodes, int edgesPerNode) {
		if (nodes == 0)
			return null;

		System.out.println("Creating node 0");

		Workflow wf = new Workflow(new WorkflowNode("Node0"));
		for (int i = 1; i < nodes; i++) {
			System.out.println("Creating node " + i);
			WorkflowNode newNode = new WorkflowNode("Node" + i);
			wf.addNode(newNode);

			for (int j = 0; j < (edgesPerNode) && j < (wf.getNodes().size() - 1); j++) {
				// System.out.println("Adding edge #" + j + 1);
				int newEdgeIndex = (int) (Math.random() * (wf.getNodes().size()));
				WorkflowNode originalNode = wf.getNodes().get(newEdgeIndex);
				if (originalNode.getEdges().contains(newNode)) {
					j--; // if the picked node already contains the newNode
							// as
							// an edge, pick again
				} else {
					originalNode.addEdge(newNode);
				}
			}
		}

		return wf;
	}

}
package workflow.datatypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gm.GM;
import gm.cgm.Goal;
import gm.cgm.Refinement;
import gm.cgm.Task;

import java.util.ArrayList;
import java.util.HashSet;

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
	public void nodesWithOutgoingEdgesMustNotBeConnectedToEnd() throws WorkflowNodeNotFound {
		WorkflowNode node1 = new WorkflowNode("Node 1");

		Workflow wf = new Workflow();
		WorkflowNode node2 = new WorkflowNode("Node 2");
		WorkflowNode node3 = new WorkflowNode("Node 3");

		wf.addNode(node1);
		wf.addNode(node2);
		wf.addNode(node3);

		wf.addEdge(node1, node2);
		wf.addEdge(node2, node3);

		assertFalse(wf.getLastNodes().contains(node1));
		assertFalse(wf.getLastNodes().contains(node2));
	}

	@Test
	public void shouldAddSeveralEdgesAtOnce() {
		WorkflowNode node1 = new WorkflowNode("Node 1");

		Workflow wf = new Workflow(node1);
		WorkflowNode node2 = new WorkflowNode("Node 2");
		WorkflowNode node3 = new WorkflowNode("Node 3");

		wf.addNode(node2);
		wf.addNode(node3);
		HashSet<WorkflowNode> edges = new HashSet<>();
		edges.add(node2);
		edges.add(node3);

		node1.addEdges(edges);

		assertEquals(edges.size(), node1.getEdges().size());

	}

	@Test
	public void shouldAddSeveralNodesAtOnce() {

		Workflow wf = new Workflow();
		WorkflowNode node1 = new WorkflowNode("Node 1");
		WorkflowNode node2 = new WorkflowNode("Node 2");
		WorkflowNode node3 = new WorkflowNode("Node 3");

		HashSet<WorkflowNode> nodes = new HashSet<>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);

		wf.addNodes(nodes);

		assertEquals(nodes.size(), wf.getNodes().size());
		assertEquals(nodes.size(), wf.getStart().getEdges().size());
		assertEquals(nodes.size(), wf.getLastNodes().size());

	}

	@Test
	public void nodesWithIncomingEdgesMustNotBeConnectedToStart() throws WorkflowNodeNotFound {

		WorkflowNode node1 = new WorkflowNode("Node 1");
		WorkflowNode node2 = new WorkflowNode("Node 2");
		WorkflowNode node3 = new WorkflowNode("Node 3");

		Workflow wf = new Workflow(node1);

		wf.addNode(node2);
		wf.addNode(node3);

		wf.addEdge(node1, node2);
		wf.addEdge(node2, node3);

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

	@Test
	public void shouldConcatenateSimpleWorkflows() {
		WorkflowNode node1 = new WorkflowNode("Node 1");
		WorkflowNode node2 = new WorkflowNode("Node 2");

		Workflow wf1 = new Workflow(node1);
		Workflow wf2 = new Workflow(node2);

		Workflow concat = wf1.concat(wf2);

		assertEquals(1, wf1.getStart().getEdges().size());
		assertTrue(concat.getStart().getEdges().contains(node1));

		assertTrue(node1.getEdges().contains(node2));

		assertEquals(1, concat.getLastNodes().size());
		assertTrue(concat.getLastNodes().contains(node2));

	}

	@Test
	public void shouldConcatenateLinearWorkflows() throws WorkflowNodeNotFound {
		WorkflowNode node1 = new WorkflowNode("Node 1");
		WorkflowNode node2 = new WorkflowNode("Node 2");
		WorkflowNode node3 = new WorkflowNode("Node 3");
		WorkflowNode node4 = new WorkflowNode("Node 4");

		Workflow wf1 = new Workflow();
		wf1.addNode(node1);
		wf1.addNode(node2);

		wf1.addEdge(node1, node2);

		Workflow wf2 = new Workflow();
		wf2.addNode(node3);
		wf2.addNode(node4);

		wf2.addEdge(node3, node4);

		Workflow concat = wf1.concat(wf2);

		assertTrue(concat.getStart().getEdges().containsAll(wf1.getStart().getEdges()));
		assertEquals(1, concat.getStart().getEdges().size());

		assertTrue(node1.getEdges().contains(node2));
		assertTrue(node2.getEdges().contains(node3));
		assertTrue(node3.getEdges().contains(node4));
		assertEquals(0, node4.getEdges().size());

		assertTrue(concat.getLastNodes().containsAll(wf2.getLastNodes()));
		assertEquals(1, concat.getLastNodes().size());

	}

	@Test
	public void shouldConcatenateNonLinearWorkflows() throws WorkflowNodeNotFound {
		WorkflowNode node1 = new WorkflowNode("Node 1");
		WorkflowNode node2 = new WorkflowNode("Node 2");
		WorkflowNode node3 = new WorkflowNode("Node 3");
		WorkflowNode node4 = new WorkflowNode("Node 4");

		Workflow wf1 = new Workflow();
		wf1.addNode(node1);
		wf1.addNode(node2);
		wf1.addNode(node3);

		wf1.addEdge(node1, node2);
		wf1.addEdge(node1, node3);

		Workflow wf2 = new Workflow();
		wf2.addNode(node4);

		Workflow concat = wf1.concat(wf2);

		assertTrue(concat.getStart().getEdges().containsAll(wf1.getStart().getEdges()));
		assertEquals(1, concat.getStart().getEdges().size());

		assertTrue(node1.getEdges().contains(node2));
		assertTrue(node1.getEdges().contains(node3));
		assertTrue(node2.getEdges().contains(node4));
		assertTrue(node3.getEdges().contains(node4));
		assertEquals(0, node4.getEdges().size());

		assertTrue(concat.getLastNodes().containsAll(wf2.getLastNodes()));
		assertEquals(1, concat.getLastNodes().size());

	}

	@Test
	public void shouldConvertToGMAndKeepAllTasks() throws WorkflowNodeNotFound {
		Workflow wf = new Workflow();

		WorkflowNode node1 = new WorkflowNode("T1");
		WorkflowNode node2 = new WorkflowNode("T2");
		WorkflowNode node3 = new WorkflowNode("T3");
		WorkflowNode node4 = new WorkflowNode("T4");
		WorkflowNode node5 = new WorkflowNode("T5");

		wf.addNode(node1);
		wf.addNode(node2);
		wf.addNode(node3);
		wf.addNode(node4);
		wf.addNode(node5);

		wf.addEdge(node1, node2);
		wf.addEdge(node2, node3);
		wf.addEdge(node2, node4);
		wf.addEdge(node3, node5);
		wf.addEdge(node4, node5);

		GM gm = wf.convertToGM();
		gm.getRoot().printCGM();

		for (Task task : gm.getRoot().getTasks()) {
			System.out.println(task.getIdentifier());
			if (!("T1".equals(task.getIdentifier()) || "T2".equals(task.getIdentifier())
					|| "T3".equals(task.getIdentifier()) || "T4".equals(task.getIdentifier()) || "T5".equals(task
					.getIdentifier()))) {
				fail("Unforeseen task: " + task.getIdentifier());
			}
		}

		assertEquals(5, gm.getRoot().getTasks().size());

	}

	@Test
	public void shouldConvertTwoLinearTasksToGM() throws WorkflowNodeNotFound {
		Workflow wf = new Workflow();

		WorkflowNode node1 = new WorkflowNode("T1");
		WorkflowNode node2 = new WorkflowNode("T2");

		wf.addNode(node1);
		wf.addNode(node2);
		wf.addEdge(node1, node2);

		GM gm = wf.convertToGM();
		wf.printWorkflow();
		assertEquals(2, gm.getRoot().getTasks().size());

		assertEquals(Refinement.GOAL, gm.getRoot().myType());

		Goal root = (Goal) gm.getRoot();
		assertTrue(root.isParallelAndDecomposition());

		assertEquals(1, root.getDependencies().size());

		Goal rootDep = null;

		for (Refinement dep : root.getDependencies()) {
			assertEquals(Refinement.GOAL, dep.myType());
			rootDep = (Goal) dep;
		}

		assertEquals(2, rootDep.getDependencies().size());

		for (Task task : gm.getRoot().getTasks()) {
			if (!("T1".equals(task.getIdentifier()) || "T2".equals(task.getIdentifier()))) {
				fail("Unforeseen task");
			}
		}
	}
}

package br.ime.usp.improv.pragmatic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

import br.ime.usp.improv.pragmatic.workflow.WorkflowTask;

public class GoalTest {

	@Test
	public void shouldGetDependencies() {
		Goal root = new Goal(false);

		Task task = new Task();
		Refinement goal = new Goal(false);
		Delegation delegation = new Delegation();

		root.addDependency(task);
		root.addDependency(goal);
		root.addDependency(delegation);

		HashSet<Refinement> deps = new HashSet<Refinement>();
		deps.add(delegation);
		deps.add(goal);
		deps.add(task);

		assertTrue(deps.containsAll(root.getDependencies()));
		assertTrue(root.getDependencies().containsAll(deps));
	}

	@Test
	public void shouldGetApplicableDependencies() {
		Goal root = new Goal(false);

		Context context = new Context("c1");
		HashSet<Context> current = new HashSet<Context>();
		current.add(context);
		Task task = new Task();
		Refinement goal = new Goal(false);
		Delegation delegation = new Delegation();
.phase.PhaseInterceptorChain doDefaultLogging
WARNING: Application {http://pragmatic.improv.usp.ime.br/}ChoreographyActorService#{http://pragmatic.improv.usp.ime.br/}webPlan has thrown exception, unwinding now
org.apache.cxf.interceptor.Fault
	at org.apache.cxf.service.invoker.AbstractInvoker.createFault(AbstractInvoker.java:163)
	at org.apache.cxf.jaxws.AbstractJAXWSMethodInvoker.createFault(AbstractJAXWSMethodInvoker.java:267)
	at org.apache.cxf.service.invoker.AbstractInvoker.invoke(AbstractInvoker.java:129)
	at org.apache.cxf.jaxws.AbstractJAXWSMethodInvoker.invoke(AbstractJAXWSMethodInvoker.java:232)
	at org.apache.cxf.jaxws.JAXWSMethodInvoker.invoke(JAXWSMethodInvoker.java:69)
	at org.apache.cxf.service.invoker.AbstractInvoker.invoke(AbstractInvoker.java:75)
	at org.apache.cxf.interceptor.Service
		task.addApplicableContext(context);

		root.addDependency(task);
		root.addDependency(goal);
		root.addDependency(delegation);

		HashSet<Refinement> deps = new HashSet<Refinement>();
		deps.add(task);

		assertEquals(1, deps.size());
		assertTrue(deps.contains(task));
	}

	@Test
	public void shouldBeOrDecompositionOrAndDecomposition() throws Exception {
		Goal or = new Goal(Goal.OR);
		Goal and = new Goal(Goal.AND);

		assertTrue(or.isOrDecomposition());
		assertFalse(or.isAndDecomposition());

		assertFalse(and.isOrDecomposition());
		assertTrue(and.isAndDecomposition());
	}

	@Test
	public void shouldBeAchievable(){

		Goal root = new Goal(Goal.AND);

		Context context = new Context("c1");
		HashSet<Context> current = new HashSet<Context>();
		current.add(context);

		Task task1 = new Task("T1");
		Task task2 = new Task("T2");

		task1.addApplicableContext(context);

        root.setIdentifier("root");
        root.addDependency(task1);
        root.addDependency(task2);

		HashSet<Refinement> deps = new HashSet<Refinement>();
		deps.add(task1);
		deps.add(task2);

		WorkflowPlan plan = root.isAchievable(current, null);
		assertNotEquals(null, plan);

        assertTrue(plan.getTasks().contains(task1.getWorkflowTask()));
        assertTrue(plan.getTasks().contains(task2.getWorkflowTask()));
    }

	@Test
	public void shouldBeUnachievable(){

		Goal root = new Goal(Goal.AND);

		Context context1 = new Context("c1");
		Context context2 = new Context("c2");
		
		HashSet<Context> current = new HashSet<Context>();
		current.add(context1);
		
		Task task1 = new Task();
        task1.setIdentifier("t1");
        Task task2 = new Task();
        task2.setIdentifier("t2");

        task1.addApplicableContext(context2);
        task2.addApplicableContext(context2);

		root.addDependency(task1);
		root.addDependency(task2);

		WorkflowPlan plan = root.isAchievable(current, null);
        assertFalse(plan.isAchievable());
    }
}

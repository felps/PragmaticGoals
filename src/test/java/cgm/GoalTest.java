package cgm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

import cgm.Context;
import cgm.Delegation;
import cgm.Goal;
import cgm.Plan;
import cgm.Refinement;
import cgm.Task;

public class GoalTest {

	@Test
	public void shouldGetDependencies() {
		Goal root = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);

		Task task = new Task();
		Refinement goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
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
		Goal root = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);

		Context context = new Context("c1");
		HashSet<Context> current = new HashSet<Context>();
		current.add(context);
		Task task = new Task();
		Refinement goal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Delegation delegation = new Delegation();

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
		Goal or = new Goal(Goal.OR_DECOMPOSITION);
		Goal and = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);

		assertTrue(or.isOrDecomposition());
		assertFalse(or.isAndDecomposition());

		assertFalse(and.isOrDecomposition());
		assertTrue(and.isAndDecomposition());
	}

	@Test
	public void shouldBeAchievable() {

		Goal root = new Goal(Goal.SERIAL_AND_DECOMPOSITION);

		Context context = new Context("c1");
		HashSet<Context> current = new HashSet<Context>();
		current.add(context);

		Task task1 = new Task();

		Task task2 = new Task();

		task1.addApplicableContext(context);

		root.addDependency(task1);
		root.addDependency(task2);

		HashSet<Refinement> deps = new HashSet<Refinement>();
		deps.add(task1);
		deps.add(task2);

		Plan plan = root.isAchievable(current, null);
		assertNotEquals(null, plan);

		assertTrue(plan.getTasks().contains(task1));
		assertTrue(plan.getTasks().contains(task2));

	}

	@Test
	public void shouldBeUnachievable() {

		Goal root = new Goal(Goal.SERIAL_AND_DECOMPOSITION);

		Context context1 = new Context("c1");
		Context context2 = new Context("c2");

		HashSet<Context> current = new HashSet<Context>();
		current.add(context1);

		Task task1 = new Task();

		Task task2 = new Task();

		task1.addApplicableContext(context2);
		task2.addApplicableContext(context2);

		root.addDependency(task1);
		root.addDependency(task2);

		HashSet<Refinement> deps = new HashSet<Refinement>();
		deps.add(task1);
		deps.add(task2);

		Plan plan = root.isAchievable(current, null);
		assertEquals(null, plan);
	}
}

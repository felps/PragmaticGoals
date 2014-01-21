package cgm;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class GoalTest {

	@Test
	public void shouldGetDependencies(){
		Dependency root = new Goal(false);
		
		Task task = new Task();
		Dependency goal = new Goal(false);
		Delegation delegation = new Delegation();
		
		root.addDependency(task);
		root.addDependency(goal);
		root.addDependency(delegation);
		
		HashSet<Dependency> deps = new HashSet<Dependency>();
		deps.add(delegation);
		deps.add(goal);
		deps.add(task);
		
		assertTrue(deps.containsAll(root.getDependencies()));
	}
	
	@Test
	public void shouldGetApplicableDependencies(){
		Dependency root = new Goal(false);
		
		Context context = new Context();
		
		Task task = new Task();
		Dependency goal = new Goal(false);
		Delegation delegation = new Delegation();
		
		task.setApplicableContext(context);
		
		root.addDependency(task);
		root.addDependency(goal);
		root.addDependency(delegation);
		
		HashSet<Dependency> deps = new HashSet<Dependency>();
		deps.add(task);
		
		assertTrue(deps.containsAll(root.getApplicableDependencies(context)));
	}
	
	@Test
	public void shouldBeOrDecompositionOrAndDecomposition() throws Exception {
		Dependency or = new Goal(true);
		Dependency and = new Goal(false);
		
		assertTrue(or.isOrDecomposition());
		assertFalse(or.isAndDecomposition());
		
		assertFalse(and.isOrDecomposition());
		assertTrue(and.isAndDecomposition());
	}



}

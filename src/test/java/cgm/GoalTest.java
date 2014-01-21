package cgm;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class GoalTest {

	@Test
	public void shouldGetDependencies(){
		Goal root = new Goal();
		
		Task task = new Task();
		Goal goal = new Goal();
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
		Goal root = new Goal();
		
		Context context = new Context();
		
		Task task = new Task();
		Goal goal = new Goal();
		Delegation delegation = new Delegation();
		
		task.setApplicableContext(context);
		
		root.addDependency(task);
		root.addDependency(goal);
		root.addDependency(delegation);
		
		HashSet<Dependency> deps = new HashSet<Dependency>();
		deps.add(task);
		
		assertTrue(deps.containsAll(root.getApplicableDependencies(context)));
	}



}

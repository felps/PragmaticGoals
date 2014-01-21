package cgm;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class CGMTest {

	@Test
	public void shouldReturnRootNode() {
		CGM cgm = new CGM();
		Goal root = new Goal();
		cgm.setRoot(root);
		assertEquals(cgm.getRoot(),root);
	}
	
	@Test
	public void shouldGetDependencies(){
		CGM cgm = new CGM();
		Goal root = new Goal();
		
		Task task = new Task();
		Goal goal = new Goal();
		Delegation delegation = new Delegation();
		
		root.addDependency(task);
		root.addDependency(goal);
		root.addDependency(delegation);
		
		HashSet<Dependency> deps = new HashSet();
		deps.add(delegation);
		deps.add(goal);
		deps.add(task);
		
		assertTrue(deps.containsAll(root.getDependencies()));
	}
	
	@Test
	public void shouldGetApplicableDependencies(){
		CGM cgm = new CGM();
		Goal root = new Goal();
		
		Context context = new Context();
		
		Task task = new Task();
		Goal goal = new Goal();
		Delegation delegation = new Delegation();
		
		task.setApplicableContext(context);
		
		root.addDependency(task);
		root.addDependency(goal);
		root.addDependency(delegation);
		
		HashSet<Dependency> deps = new HashSet();
		deps.add(task);
		
		assertTrue(deps.containsAll(root.getApplicableDependencies(context)));
	}


}

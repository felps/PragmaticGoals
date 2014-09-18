package cgm;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class GoalTest {

	@Test
	public void shouldGetDependencies(){
		Refinement root = new Goal(false);
		
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
	public void shouldGetApplicableDependencies(){
		Refinement root = new Goal(false);
		
		Context context = new Context();
		HashSet<Context> current = new HashSet<Context>();
		current.add(context);
		Task task = new Task();
		Refinement goal = new Goal(false);
		Delegation delegation = new Delegation();
		
		task.setApplicableContext(context);
		
		root.addDependency(task);
		root.addDependency(goal);
		root.addDependency(delegation);
		
		HashSet<Refinement> deps = new HashSet<Refinement>();
		deps.add(task);
		
		assertTrue(deps.containsAll(root.getApplicableDependencies(current)));
	}
	
	@Test
	public void shouldBeOrDecompositionOrAndDecomposition() throws Exception {
		Refinement or = new Goal(true);
		Refinement and = new Goal(false);
		
		assertTrue(or.isOrDecomposition());
		assertFalse(or.isAndDecomposition());
		
		assertFalse(and.isOrDecomposition());
		assertTrue(and.isAndDecomposition());
	}



}

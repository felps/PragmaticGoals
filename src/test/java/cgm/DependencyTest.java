package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class DependencyTest {

	@Test
	public void shouldReturnMyType() {
		Goal goal = new Goal();
		assertEquals(Dependency.GOAL, goal.myType());
		
		Task task = new Task();
		assertEquals(Dependency.TASK, task.myType());
		
		Delegation delegation = new Delegation();
		assertEquals(Dependency.DELEGATION, delegation.myType());
	}
	
	

}

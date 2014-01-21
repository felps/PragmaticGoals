package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class DependencyTest {

	@Test
	public void shouldReturnMyType() {
		Goal goal = new Goal();
		Task task = new Task();
		Delegation delegation = new Delegation();

		assertEquals(Dependency.GOAL, goal.myType());
		
		assertEquals(Dependency.TASK, task.myType());
		
		assertEquals(Dependency.DELEGATION, delegation.myType());
	}
	
	@Test
	public void shouldBeApplicable() throws Exception {
		Goal goal = new Goal();
		Task task = new Task();
		Delegation delegation = new Delegation();
		
		Context context = new Context();
		
		task.setApplicableContext(context);
		goal.setApplicableContext(context);
		delegation.setApplicableContext(context);
		
		assertTrue(goal.isApplicable(context));
		assertTrue(task.isApplicable(context));
		assertTrue(delegation.isApplicable(context));
	}

	@Test
	public void shouldBeNotApplicable() throws Exception {
		Goal goal = new Goal();
		Task task = new Task();
		Delegation delegation = new Delegation();
		
		Context context = new Context();
		
		task.setApplicableContext(context);
		goal.setApplicableContext(context);
		delegation.setApplicableContext(context);
		
		Context wrongContext = new Context();
		
		assertFalse(goal.isApplicable(wrongContext));
		assertFalse(task.isApplicable(wrongContext));
		assertFalse(delegation.isApplicable(wrongContext));
	}

}

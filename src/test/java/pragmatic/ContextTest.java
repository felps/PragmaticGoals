package pragmatic;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContextTest {

	@Test
	public void equalsMethodMustBeSymmetric() {
		Context c1 = new Context("c1");
		Context c2 = new Context("c1");
		
		assertTrue(c2.equals(c1));
		assertTrue(c1.equals(c2));
	}

	
	@Test
	public void equalsMethodMustBeReflexive() {
		Context c1 = new Context("c1");
		Context c2 = new Context("c1");
	
		assertTrue(c1.equals(c1));
		assertTrue(c2.equals(c2));
	}

	@Test
	public void equalsMethodMustNotBeCaseSensitive() {
		Context c1 = new Context("c1");
		Context c2 = new Context("C1");
	
		assertTrue(c1.equals(c2));
		assertTrue(c2.equals(c1));
	}
	
	@Test
	public void hashMethodMustNotBeCaseSensitive() {
		Context c1 = new Context("c1");
		Context c2 = new Context("C1");
	
		assertEquals(c1.hashCode(), c2.hashCode());
	}
	
	

	
}

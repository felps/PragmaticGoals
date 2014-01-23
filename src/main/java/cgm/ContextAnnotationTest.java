package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContextAnnotationTest {

	ContextAnnotation annot;
	
	@Test
	public void shouldCreateNewAnnotation() throws Exception {
		annot = new ContextAnnotation("C1", "Context 1");
		
	}

	@Test(expected=Exception.class)
	public void shouldThrowExceptionIfTryingToCreateDuplicate() throws Exception {
		annot = new ContextAnnotation("C2", "Context 2.1");
		annot = new ContextAnnotation("C2", "Context 2.2");
	}

	@Test
	public void shouldNotCreateDupplicateAnnotation() {
		ContextAnnotation annot1 = ContextAnnotation.createContextAnnotation("C3", "Context 3");
		ContextAnnotation annot2 = ContextAnnotation.createContextAnnotation("C3", "Context 3");
		
		assertSame(annot1, annot2);
	}

	@Test
	public void testGetIdentifier() {
		ContextAnnotation annot1 = ContextAnnotation.createContextAnnotation("C4", "Context 4");
		assertEquals("C4", annot1.getIdentifier());
		
	}

	@Test
	public void testGetDescription() {
		ContextAnnotation annot1 = ContextAnnotation.createContextAnnotation("C5", "Context 5");
		assertEquals("Context 5", annot1.getDescription());
		
	}

}

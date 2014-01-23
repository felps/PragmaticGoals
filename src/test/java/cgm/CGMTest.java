package cgm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CGMTest {

	@Test
	public void shouldReturnRootNode() {
		CGM cgm = new CGM();
		Refinement root = new Goal(false);
		cgm.setRoot(root);
		assertEquals(cgm.getRoot(),root);
	}
	

}

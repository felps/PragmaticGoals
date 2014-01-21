package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class CGMTest {

	@Test
	public void shouldReturnRootNode() {
		CGM cgm = new CGM();
		Goal root = new Goal();
		cgm.setRoot(root);
		assertEquals(cgm.getRoot(),root);
	}


}

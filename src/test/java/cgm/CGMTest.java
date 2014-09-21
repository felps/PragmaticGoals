package cgm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CGMTest {

	@Test
	public void shouldReturnRootNode() {
		CGM cgm = new CGM();
		Refinement root = new Goal(false);
		cgm.setRoot(root);
		assertEquals(cgm.getRoot(), root);
	}

	// @Test
	// public void whatOsTheOutputForYamlDump() throws Exception {
	// CGM cgm = new CGM();
	// cgm.setRoot((new Goal(false)));
	// cgm.dumpToYamlFile();
	// }
}

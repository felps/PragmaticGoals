package br.ime.usp.improv.pragmatic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ime.usp.improv.pragmatic.CGM;
import br.ime.usp.improv.pragmatic.Goal;
import br.ime.usp.improv.pragmatic.Refinement;

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
	// CGM pragmatic = new CGM();
	// pragmatic.setRoot((new Goal(false)));
	// pragmatic.dumpToYamlFile();
	// }
}

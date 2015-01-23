package cgm;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

public class InterpretationTest {

	@Test
	public void shouldStoreInterpretationsPerApplicableContext() {
		Interpretation interp = new Interpretation();
		Context context = new Context("C1");
		QualityConstraint qc = new QualityConstraint(context , Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);

		interp.addQualityConstraint(qc);
		HashMap<Context, Set<QualityConstraint>> map =interp.getContextDependentInterpretation();
		
		assertEquals(1, map.keySet().size());
		assertEquals(1, map.get(context).size());
		

	}

}

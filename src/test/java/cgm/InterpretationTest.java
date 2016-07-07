package cgm;

import cgm.metrics.FilterMetric;
import cgm.quality.QualityConstraint;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class InterpretationTest {

	@Test
	public void shouldStoreInterpretationsPerApplicableContext() {
		Interpretation interp = new Interpretation();
		Context context = new Context("C1");
		QualityConstraint qc = new QualityConstraint(context, FilterMetric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);

		interp.addFilterQualityConstraint(qc);
		HashMap<Context, Set<QualityConstraint>> map =interp.getContextDependentInterpretation();
		
		assertEquals(1, map.keySet().size());
		assertEquals(1, map.get(context).size());
		

	}

}

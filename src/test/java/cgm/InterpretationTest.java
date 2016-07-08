package cgm;

import cgm.metrics.Metric;
import cgm.quality.FilterQualityConstraint;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class InterpretationTest {

	@Test
	public void shouldStoreInterpretationsPerApplicableContext() {
		Interpretation interp = new Interpretation();
		Context context = new Context("C1");
		FilterQualityConstraint qc = new FilterQualityConstraint(context, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);

		interp.addFilterQualityConstraint(qc);
		HashMap<Context, Set<FilterQualityConstraint>> map = interp.getContextDependentInterpretation();
		
		assertEquals(1, map.keySet().size());
		assertEquals(1, map.get(context).size());
		

	}

}

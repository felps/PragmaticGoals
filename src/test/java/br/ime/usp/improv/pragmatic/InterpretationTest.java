package br.ime.usp.improv.pragmatic;

import org.junit.Test;

import br.ime.usp.improv.pragmatic.Comparison;
import br.ime.usp.improv.pragmatic.Context;
import br.ime.usp.improv.pragmatic.Interpretation;
import br.ime.usp.improv.pragmatic.metrics.Metric;
import br.ime.usp.improv.pragmatic.quality.FilterQualityConstraint;

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

package cgm;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Set;

import metrics.ExecutionTimeSec;

import org.junit.Test;

import cgm.Comparison;
import cgm.Context;
import cgm.Interpretation;
import cgm.QualityConstraint;

public class InterpretationTest {

	@Test
	public void shouldStoreInterpretationsPerApplicableContext() {
		Interpretation interp = new Interpretation();
		Context context = new Context("C1");
		QualityConstraint qc = new QualityConstraint(context, (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);

		interp.addQualityConstraint(qc);
		HashMap<Context, Set<QualityConstraint>> map = interp.getContextDependentInterpretation();

		assertEquals(1, map.keySet().size());
		assertEquals(1, map.get(context).size());

	}

}

package cgm;

import cgm.metrics.Metric;
import cgm.quality.FilterQualityConstraint;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PragmaticGoalTest {

	@Test
	public void shouldGetDifferentQualityConstraintsForDifferentContexts() {
		Context aContext = new Context("c1");
		Context anotherContext = new Context("c2");

		FilterQualityConstraint aQC = new FilterQualityConstraint(aContext, Metric.METERS, 30, Comparison.LESS_OR_EQUAL_TO);
		FilterQualityConstraint anotherQC = new FilterQualityConstraint(anotherContext, Metric.METERS, 60,
				Comparison.LESS_OR_EQUAL_TO);

		Pragmatic goal = new Pragmatic(false);

		goal.getInterpretation().addFilterQualityConstraint(aQC);
		goal.getInterpretation().addFilterQualityConstraint(anotherQC);

		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(aContext);
		assertTrue(goal.getInterpretation().getQualityConstraints(fullContext).contains(aQC));

		HashSet<Context> anotherFullContext = new HashSet<Context>();
		anotherFullContext.add(anotherContext);
		assertTrue(goal.getInterpretation().getQualityConstraints(anotherFullContext).contains(anotherQC));

		assertFalse(goal.getInterpretation().getQualityConstraints(fullContext).contains(anotherQC));
		assertFalse(goal.getInterpretation().getQualityConstraints(anotherFullContext).contains(aQC));

	}

}

package cgm;

import cgm.metrics.FilterMetric;
import cgm.quality.QualityConstraint;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PragmaticGoalTest {

	@Test
	public void shouldGetDifferentQualityConstraintsForDifferentContexts() {
		Context aContext = new Context("c1");
		Context anotherContext = new Context("c2");

		QualityConstraint aQC = new QualityConstraint(aContext, FilterMetric.METERS, 30, Comparison.LESS_OR_EQUAL_TO);
		QualityConstraint anotherQC = new QualityConstraint(anotherContext, FilterMetric.METERS, 60,
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

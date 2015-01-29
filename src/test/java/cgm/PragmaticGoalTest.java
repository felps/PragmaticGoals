package cgm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import metrics.DistanceErrorMargin;

import org.junit.Test;

import cgm.Comparison;
import cgm.Context;
import cgm.Goal;
import cgm.Pragmatic;
import cgm.QualityConstraint;

public class PragmaticGoalTest {

	@Test
	public void shouldGetDifferentQualityConstraintsForDifferentContexts() {
		Context aContext = new Context("c1");
		Context anotherContext = new Context("c2");

		QualityConstraint aQC = new QualityConstraint(aContext, (new DistanceErrorMargin()), 30,
				Comparison.LESS_OR_EQUAL_TO);
		QualityConstraint anotherQC = new QualityConstraint(anotherContext, (new DistanceErrorMargin()), 60,
				Comparison.LESS_OR_EQUAL_TO);

		Pragmatic goal = new Pragmatic(Goal.PARALLEL_AND_DECOMPOSITION);

		goal.getInterpretation().addQualityConstraint(aQC);
		goal.getInterpretation().addQualityConstraint(anotherQC);

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

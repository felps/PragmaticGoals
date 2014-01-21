package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class PragmaticGoalTest {

	@Test
	public void shouldGetDifferentQualityConstraintsForDifferentContexts() {
		Context aContext = new Context();
		Context anotherContext = new Context();
		
		QualityConstraint aQC = new QualityConstraint(aContext,Metric.METERS,30,Comparison.LESS_OR_EQUAL_TO);
		QualityConstraint anotherQC = new QualityConstraint(aContext,Metric.METERS,60,Comparison.LESS_OR_EQUAL_TO);

		Pragmatic goal = new Pragmatic(false);
		
		goal.getInterpretation().addQualityConstraint(aQC, aContext);
		goal.getInterpretation().addQualityConstraint(anotherQC, anotherContext);
		
		assertTrue(goal.getInterpretation().getQualityConstraints(aContext).contains(aQC));
		assertTrue(goal.getInterpretation().getQualityConstraints(anotherContext).contains(anotherQC));
		
		assertFalse(goal.getInterpretation().getQualityConstraints(aContext).contains(anotherQC));
		assertFalse(goal.getInterpretation().getQualityConstraints(anotherContext).contains(aQC));
		
	}

}

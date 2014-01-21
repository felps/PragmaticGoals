package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class PragmaticGoal {

	@Test
	public void shouldGetDifferentQualityConstraintsForDifferentContexts() {
		Context aContext = new Context();
		Context anotherContext = new Context();
		
		QualityConstraint aQC = new QualityConstraint();
		QualityConstraint anotherQC = new QualityConstraint();

		Pragmatic goal = new Pragmatic();
		
		goal.getInterpretation().addQualityConstraint(aQC, aContext);
		goal.getInterpretation().addQualityConstraint(anotherQC, anotherContext);
		
		assertTrue(goal.getInterpretation().getQualityConstraints(aContext).contains(aQC));
		assertTrue(goal.getInterpretation().getQualityConstraints(anotherContext).contains(anotherQC));
		
		assertFalse(goal.getInterpretation().getQualityConstraints(aContext).contains(anotherQC));
		assertFalse(goal.getInterpretation().getQualityConstraints(anotherContext).contains(aQC));
		
	}

}

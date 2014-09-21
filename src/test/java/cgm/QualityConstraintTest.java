package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class QualityConstraintTest {

	@Test
	public void shouldBeBetterThan() {
		QualityConstraint qc = new QualityConstraint(new Context("C1"),Metric.SECONDS,15,Comparison.LESS_THAN);
		assertTrue(qc.abidesByQC(13, Metric.SECONDS));
		assertFalse(qc.abidesByQC(16, Metric.SECONDS));
	}
	
	@Test
	public void shouldBeWorseThan() {
		QualityConstraint qc = new QualityConstraint(new Context("C2"),Metric.SECONDS,15,Comparison.LESS_THAN);
		assertFalse(qc.abidesByQC(16, Metric.SECONDS));
	}
	
	@Test
	public void shouldSelectStricterConstraint() throws DifferentMetricsException{
		QualityConstraint lessStrictQC = new QualityConstraint(new Context("C1"),Metric.SECONDS,15,Comparison.LESS_THAN);
		QualityConstraint moreStrictQC = new QualityConstraint(new Context("C2"),Metric.SECONDS,10,Comparison.LESS_THAN);
		
		assertEquals(moreStrictQC, lessStrictQC.stricterQC(moreStrictQC));
		assertEquals(moreStrictQC, moreStrictQC.stricterQC(lessStrictQC));
	}
}

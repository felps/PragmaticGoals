package cgm;

import cgm.metrics.FilterMetric;
import cgm.metrics.exceptions.DifferentMetricsException;
import cgm.quality.QualityConstraint;
import org.junit.Test;

import static org.junit.Assert.*;

public class QualityConstraintTest {

	@Test
	public void shouldBeBetterThan() {
		QualityConstraint qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15, Comparison.LESS_THAN);
		assertTrue(qc.abidesByQC(13, FilterMetric.SECONDS));
		assertFalse(qc.abidesByQC(16, FilterMetric.SECONDS));
	}

	@Test
	public void shouldBeWorseThan() {
		QualityConstraint qc = new QualityConstraint(new Context("C2"), FilterMetric.SECONDS, 15, Comparison.LESS_THAN);
		assertFalse(qc.abidesByQC(16, FilterMetric.SECONDS));
	}

	@Test
	public void shouldSelectStricterConstraint() throws DifferentMetricsException {
		QualityConstraint lessStrictQC = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_THAN);
		QualityConstraint moreStrictQC = new QualityConstraint(new Context("C2"), FilterMetric.SECONDS, 10,
				Comparison.LESS_THAN);

		assertEquals(moreStrictQC, lessStrictQC.stricterQC(moreStrictQC));
		assertEquals(moreStrictQC, moreStrictQC.stricterQC(lessStrictQC));
	}
	
	@Test
	public void shouldGetCorrectThreshold(){
		QualityConstraint qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertEquals(15, 0.01, qc.getThreshold());
	}
	
	@Test
	public void shouldGetCorrectComparison(){
		QualityConstraint qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertEquals(Comparison.LESS_THAN, qc.getComparison());
	}
	

	@Test
	public void shouldGetCorrectMetric(){
		QualityConstraint qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertEquals(FilterMetric.SECONDS, qc.getMetric());
	}
	
	@Test
	public void shouldGetCorrectContexts(){
		QualityConstraint qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertEquals(new Context("C1"), qc.getApplicableContext());
	}
	
	@Test
	public void shouldAbideByQcIfMetricIsNotAffected(){
		QualityConstraint qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertTrue(qc.abidesByQC(15, FilterMetric.METERS));
	}
	
	@Test
	public void shouldCorrectlyCompareMetrics(){
		QualityConstraint qc;
		
		/* True statements */

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertTrue(qc.abidesByQC(14, FilterMetric.SECONDS));

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(14, FilterMetric.SECONDS));

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(15, FilterMetric.SECONDS));

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.EQUAL_TO);
		assertTrue(qc.abidesByQC(15, FilterMetric.SECONDS));

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.GREATER_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(15, FilterMetric.SECONDS));

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.GREATER_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(16, FilterMetric.SECONDS));

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.GREATER_THAN);
		assertTrue(qc.abidesByQC(16, FilterMetric.SECONDS));

		/* False statements */

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertFalse(qc.abidesByQC(16, FilterMetric.SECONDS));

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_OR_EQUAL_TO);
		assertFalse(qc.abidesByQC(16, FilterMetric.SECONDS));

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.EQUAL_TO);
		assertFalse(qc.abidesByQC(16, FilterMetric.SECONDS));

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.GREATER_OR_EQUAL_TO);
		assertFalse(qc.abidesByQC(14, FilterMetric.SECONDS));

		qc = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.GREATER_THAN);
		assertFalse(qc.abidesByQC(14, FilterMetric.SECONDS));
	
	}
	
	@Test(expected=DifferentMetricsException.class)
	public void shouldComplainAboutDifferentMetrics() throws DifferentMetricsException{
		QualityConstraint lessStrictQC = new QualityConstraint(new Context("C1"), FilterMetric.SECONDS, 15,
				Comparison.LESS_THAN);
		QualityConstraint moreStrictQC = new QualityConstraint(new Context("C2"), FilterMetric.METERS, 10,
				Comparison.LESS_THAN);

		lessStrictQC.stricterQC(moreStrictQC);
	}
	
}

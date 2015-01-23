package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class QualityConstraintTest {

	@Test
	public void shouldBeBetterThan() {
		QualityConstraint qc = new QualityConstraint(new Context("C1"), Metric.SECONDS, 15, Comparison.LESS_THAN);
		assertTrue(qc.abidesByQC(13, Metric.SECONDS));
		assertFalse(qc.abidesByQC(16, Metric.SECONDS));
	}

	@Test
	public void shouldBeWorseThan() {
		QualityConstraint qc = new QualityConstraint(new Context("C2"), Metric.SECONDS, 15, Comparison.LESS_THAN);
		assertFalse(qc.abidesByQC(16, Metric.SECONDS));
	}

	@Test
	public void shouldSelectStricterConstraint() throws DifferentMetricsException {
		QualityConstraint lessStrictQC = new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_THAN);
		QualityConstraint moreStrictQC = new QualityConstraint(new Context("C2"), Metric.SECONDS, 10,
				Comparison.LESS_THAN);

		assertEquals(moreStrictQC, lessStrictQC.stricterQC(moreStrictQC));
		assertEquals(moreStrictQC, moreStrictQC.stricterQC(lessStrictQC));
	}
	
	@Test
	public void shouldGetCorrectThreshold(){
		QualityConstraint qc = new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertEquals(15, 0.01, qc.getThreshold());
	}
	
	@Test
	public void shouldGetCorrectComparison(){
		QualityConstraint qc = new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertEquals(Comparison.LESS_THAN, qc.getComparison());
	}
	

	@Test
	public void shouldGetCorrectMetric(){
		QualityConstraint qc = new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertEquals(Metric.SECONDS, qc.getMetric());
	}
	
	@Test
	public void shouldGetCorrectContexts(){
		QualityConstraint qc = new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertEquals(new Context("C1"), qc.getApplicableContext());
	}
	
	@Test
	public void shouldAbideByQcIfMetricIsNotAffected(){
		QualityConstraint qc = new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertTrue(qc.abidesByQC(15, Metric.METERS));
	}
	
	@Test
	public void shouldCorrectlyCompareMetrics(){
		QualityConstraint qc;
		
		/* True statements */
		
		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertTrue(qc.abidesByQC(14, Metric.SECONDS));
		
		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(14, Metric.SECONDS));
		
		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(15, Metric.SECONDS));
		
		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.EQUAL_TO);
		assertTrue(qc.abidesByQC(15, Metric.SECONDS));
		
		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.GREATER_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(15, Metric.SECONDS));

		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.GREATER_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(16, Metric.SECONDS));

		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.GREATER_THAN);
		assertTrue(qc.abidesByQC(16, Metric.SECONDS));

		/* False statements */ 
		
		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_THAN);
		assertFalse(qc.abidesByQC(16, Metric.SECONDS));
		
		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_OR_EQUAL_TO);
		assertFalse(qc.abidesByQC(16, Metric.SECONDS));
		
		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.EQUAL_TO);
		assertFalse(qc.abidesByQC(16, Metric.SECONDS));
		
		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.GREATER_OR_EQUAL_TO);
		assertFalse(qc.abidesByQC(14, Metric.SECONDS));

		qc= new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.GREATER_THAN);
		assertFalse(qc.abidesByQC(14, Metric.SECONDS));
	
	}
	
	@Test(expected=DifferentMetricsException.class)
	public void shouldComplainAboutDifferentMetrics() throws DifferentMetricsException{
		QualityConstraint lessStrictQC = new QualityConstraint(new Context("C1"), Metric.SECONDS, 15,
				Comparison.LESS_THAN);
		QualityConstraint moreStrictQC = new QualityConstraint(new Context("C2"), Metric.METERS, 10,
				Comparison.LESS_THAN);

		lessStrictQC.stricterQC(moreStrictQC);
	}
	
}

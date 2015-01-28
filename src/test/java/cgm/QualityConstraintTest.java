package cgm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import metrics.DistanceErrorMargin;
import metrics.ExecutionTimeSec;

import org.junit.Test;

public class QualityConstraintTest {

	@Test
	public void shouldBeBetterThan() {
		QualityConstraint qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15,
				Comparison.LESS_THAN);
		assertTrue(qc.abidesByQC(13, (new ExecutionTimeSec())));
		assertFalse(qc.abidesByQC(16, (new ExecutionTimeSec())));
	}

	@Test
	public void shouldBeWorseThan() {
		QualityConstraint qc = new QualityConstraint(new Context("C2"), (new ExecutionTimeSec()), 15,
				Comparison.LESS_THAN);
		assertFalse(qc.abidesByQC(16, (new ExecutionTimeSec())));
	}

	@Test
	public void shouldSelectStricterConstraint() throws DifferentMetricsException {
		QualityConstraint lessStrictQC = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15,
				Comparison.LESS_THAN);
		QualityConstraint moreStrictQC = new QualityConstraint(new Context("C2"), (new ExecutionTimeSec()), 10,
				Comparison.LESS_THAN);

		assertEquals(moreStrictQC, lessStrictQC.stricterQC(moreStrictQC));
		assertEquals(moreStrictQC, moreStrictQC.stricterQC(lessStrictQC));
	}

	@Test
	public void shouldGetCorrectThreshold() {
		QualityConstraint qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15,
				Comparison.LESS_THAN);
		assertEquals(15, 0.01, qc.getThreshold());
	}

	@Test
	public void shouldGetCorrectComparison() {
		QualityConstraint qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15,
				Comparison.LESS_THAN);
		assertEquals(Comparison.LESS_THAN, qc.getComparison());
	}

	@Test
	public void shouldGetCorrectMetric() {
		QualityConstraint qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15,
				Comparison.LESS_THAN);
		assertEquals((new ExecutionTimeSec()), qc.getMetric());
	}

	@Test
	public void shouldGetCorrectContexts() {
		QualityConstraint qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15,
				Comparison.LESS_THAN);
		assertEquals(new Context("C1"), qc.getApplicableContext());
	}

	@Test
	public void shouldAbideByQcIfMetricIsNotAffected() {
		QualityConstraint qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15,
				Comparison.LESS_THAN);
		assertTrue(qc.abidesByQC(15, (new DistanceErrorMargin())));
	}

	@Test
	public void shouldCorrectlyCompareMetrics() {
		QualityConstraint qc;

		/* True statements */

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.LESS_THAN);
		assertTrue(qc.abidesByQC(14, (new ExecutionTimeSec())));

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(14, (new ExecutionTimeSec())));

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(15, (new ExecutionTimeSec())));

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.EQUAL_TO);
		assertTrue(qc.abidesByQC(15, (new ExecutionTimeSec())));

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.GREATER_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(15, (new ExecutionTimeSec())));

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.GREATER_OR_EQUAL_TO);
		assertTrue(qc.abidesByQC(16, (new ExecutionTimeSec())));

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.GREATER_THAN);
		assertTrue(qc.abidesByQC(16, (new ExecutionTimeSec())));

		/* False statements */

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.LESS_THAN);
		assertFalse(qc.abidesByQC(16, (new ExecutionTimeSec())));

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.LESS_OR_EQUAL_TO);
		assertFalse(qc.abidesByQC(16, (new ExecutionTimeSec())));

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.EQUAL_TO);
		assertFalse(qc.abidesByQC(16, (new ExecutionTimeSec())));

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.GREATER_OR_EQUAL_TO);
		assertFalse(qc.abidesByQC(14, (new ExecutionTimeSec())));

		qc = new QualityConstraint(new Context("C1"), (new ExecutionTimeSec()), 15, Comparison.GREATER_THAN);
		assertFalse(qc.abidesByQC(14, (new ExecutionTimeSec())));

	}

	@Test(expected = DifferentMetricsException.class)
	public void shouldComplainAboutDifferentMetrics() throws DifferentMetricsException {
		ExecutionTimeSec metric = new ExecutionTimeSec();
		// metric.setName("secs");
		QualityConstraint lessStrictQC = new QualityConstraint(new Context("C1"), metric, 15, Comparison.LESS_THAN);

		QualityConstraint moreStrictQC = new QualityConstraint(new Context("C2"), (new DistanceErrorMargin()), 10,
				Comparison.LESS_THAN);

		lessStrictQC.stricterQC(moreStrictQC);
	}

}

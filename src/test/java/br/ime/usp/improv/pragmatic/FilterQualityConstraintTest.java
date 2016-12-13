package br.ime.usp.improv.pragmatic;

import org.junit.Test;

import br.ime.usp.improv.pragmatic.Comparison;
import br.ime.usp.improv.pragmatic.Context;
import br.ime.usp.improv.pragmatic.metrics.Metric;
import br.ime.usp.improv.pragmatic.metrics.exceptions.DifferentMetricsException;
import br.ime.usp.improv.pragmatic.quality.FilterQualityConstraint;

import static org.junit.Assert.*;

public class FilterQualityConstraintTest {

    @Test
    public void shouldBeBetterThan() {
        FilterQualityConstraint qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15, Comparison.LESS_THAN);
        assertTrue(qc.abidesByQC(13, Metric.SECONDS));
        assertFalse(qc.abidesByQC(16, Metric.SECONDS));
    }

    @Test
    public void shouldBeWorseThan() {
        FilterQualityConstraint qc = new FilterQualityConstraint(new Context("C2"), Metric.SECONDS, 15, Comparison.LESS_THAN);
        assertFalse(qc.abidesByQC(16, Metric.SECONDS));
    }

    @Test
    public void shouldSelectStricterConstraint() throws DifferentMetricsException {
        FilterQualityConstraint lessStrictQC = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_THAN);
        FilterQualityConstraint moreStrictQC = new FilterQualityConstraint(new Context("C2"), Metric.SECONDS, 10,
                Comparison.LESS_THAN);

        assertEquals(moreStrictQC, lessStrictQC.stricterQC(moreStrictQC));
        assertEquals(moreStrictQC, moreStrictQC.stricterQC(lessStrictQC));
    }

    @Test
    public void shouldGetCorrectThreshold() {
        FilterQualityConstraint qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_THAN);
        assertEquals(15, 0.01, qc.getThreshold());
    }

    @Test
    public void shouldGetCorrectComparison() {
        FilterQualityConstraint qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_THAN);
        assertEquals(Comparison.LESS_THAN, qc.getComparison());
    }


    @Test
    public void shouldGetCorrectMetric() {
        FilterQualityConstraint qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_THAN);
        assertEquals(Metric.SECONDS, qc.getMetric());
    }

    @Test
    public void shouldGetCorrectContexts() {
        FilterQualityConstraint qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_THAN);
        assertEquals(new Context("C1"), qc.getApplicableContext());
    }

    @Test
    public void shouldAbideByQcIfMetricIsNotAffected() {
        FilterQualityConstraint qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_THAN);
        assertTrue(qc.abidesByQC(15, Metric.METERS));
    }

    @Test
    public void shouldCorrectlyCompareMetrics() {
        FilterQualityConstraint qc;

		/* True statements */

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_THAN);
        assertTrue(qc.abidesByQC(14, Metric.SECONDS));

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_OR_EQUAL_TO);
        assertTrue(qc.abidesByQC(14, Metric.SECONDS));

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_OR_EQUAL_TO);
        assertTrue(qc.abidesByQC(15, Metric.SECONDS));

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.EQUAL_TO);
        assertTrue(qc.abidesByQC(15, Metric.SECONDS));

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.GREATER_OR_EQUAL_TO);
        assertTrue(qc.abidesByQC(15, Metric.SECONDS));

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.GREATER_OR_EQUAL_TO);
        assertTrue(qc.abidesByQC(16, Metric.SECONDS));

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.GREATER_THAN);
        assertTrue(qc.abidesByQC(16, Metric.SECONDS));

		/* False statements */

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_THAN);
        assertFalse(qc.abidesByQC(16, Metric.SECONDS));

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_OR_EQUAL_TO);
        assertFalse(qc.abidesByQC(16, Metric.SECONDS));

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.EQUAL_TO);
        assertFalse(qc.abidesByQC(16, Metric.SECONDS));

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.GREATER_OR_EQUAL_TO);
        assertFalse(qc.abidesByQC(14, Metric.SECONDS));

        qc = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.GREATER_THAN);
        assertFalse(qc.abidesByQC(14, Metric.SECONDS));

    }

    @Test(expected = DifferentMetricsException.class)
    public void shouldComplainAboutDifferentMetrics() throws DifferentMetricsException {
        FilterQualityConstraint lessStrictQC = new FilterQualityConstraint(new Context("C1"), Metric.SECONDS, 15,
                Comparison.LESS_THAN);
        FilterQualityConstraint moreStrictQC = new FilterQualityConstraint(new Context("C2"), Metric.METERS, 10,
                Comparison.LESS_THAN);

        lessStrictQC.stricterQC(moreStrictQC);
    }

}

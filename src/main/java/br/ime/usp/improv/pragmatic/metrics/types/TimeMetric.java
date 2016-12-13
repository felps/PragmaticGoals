package br.ime.usp.improv.pragmatic.metrics.types;

import br.ime.usp.improv.pragmatic.metrics.CompositeMetric;
import br.ime.usp.improv.pragmatic.metrics.Metric;

/**
 * Created by Felipe on 07/07/2016.
 */
public class TimeMetric extends CompositeMetric {

    public String getType() {
        return Metric.TIME;
    }

    public double getSequentialQuality(double metric1, double metric2) {
        return metric1 + metric2;
    }

    public double getParallelQuality(double metric1, double metric2) {
        return Math.max(metric1, metric2);
    }

    public boolean isBetterThan(CompositeMetric candidateMetric) {
        return candidateMetric.getValue() >= this.getValue();
    }
}

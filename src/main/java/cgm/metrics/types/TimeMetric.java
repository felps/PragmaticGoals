package cgm.metrics.types;

import cgm.metrics.CompositeMetric;
import cgm.metrics.Metric;

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

package cgm.metrics.types;

import cgm.metrics.CompositeMetric;
import cgm.metrics.Metric;

/**
 * Created by Felipe on 07/07/2016.
 */
public class Time extends CompositeMetric {

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
        // this.isBetterThan(candidate)?

        // If the candidate time is equal or higher
        // then this is better than the candidate
// else, this is worse than the candidate
        return candidateMetric.getValue() >= this.getValue();
    }
}

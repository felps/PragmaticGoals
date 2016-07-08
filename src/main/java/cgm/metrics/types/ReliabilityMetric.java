package cgm.metrics.types;

import cgm.metrics.CompositeMetric;
import cgm.metrics.Metric;

/**
 * Created by Felipe on 07/07/2016.
 */
public class ReliabilityMetric extends CompositeMetric {
    public String getType() {
        return Metric.RELIABILITY;
    }

    public double getSequentialQuality(double metric1, double metric2) {
        return 0;
    }

    public double getParallelQuality(double metric1, double metric2) {
        return 0;
    }

    public boolean isBetterThan(CompositeMetric candidateMetric) {
        // this.isBetterThan(candidate)?

        // If the candidate reliability is lower
        // then this is better than the candidate
// else, this is worse than the candidate
        return candidateMetric.getValue() < this.getValue();
    }

}

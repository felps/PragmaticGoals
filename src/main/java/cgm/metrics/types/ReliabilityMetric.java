package cgm.metrics.types;

import cgm.metrics.CompositeMetric;
import cgm.metrics.Metric;

/**
 * Created by Felipe on 07/07/2016.
 *
 * Reliability is a composite metric indicating how likely it is for a task or workflo to be correctly executed
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
        return candidateMetric.getValue() < this.getValue();
    }

    @Override
    public void setValue(double value) {
        if (value > 1)
            System.err.println("reliability cannot be greater than one.");
        else super.setValue(value);
    }
}

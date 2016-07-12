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
        return candidateMetric.getValue() < this.getValue();
    }

    @Override
    public void setValue(double value) {
        if (value > 1)
            System.err.println("reliability cannot be greater than one.");
        else this.value = value;
    }
}

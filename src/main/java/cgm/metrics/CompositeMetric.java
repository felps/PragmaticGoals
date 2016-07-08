package cgm.metrics;

import cgm.metrics.exceptions.DifferentMetricsException;

/**
 * Created by Felipe on 07/07/2016.
 */
public abstract class CompositeMetric extends Metric {

    public double value;

    public abstract String getType();

    public double getValue() {
        return value;
    }
    public abstract double getSequentialQuality(double metric1, double metric2);

    public double getSequentialQuality(CompositeMetric metric1, CompositeMetric metric2) throws DifferentMetricsException {
        if (metric1.getClass().equals(metric2.getClass()))
            return getSequentialQuality(metric1.getValue(), metric2.getValue());
        else throw new DifferentMetricsException();
    }

    public abstract double getParallelQuality(double metric1, double metric2);

    public double getParallelQuality(CompositeMetric metric1, CompositeMetric metric2) throws DifferentMetricsException {
        if (metric1.getClass().equals(metric2.getClass()))
            return getParallelQuality(metric1.getValue(), metric2.getValue());
        else throw new DifferentMetricsException();
    }

    public abstract boolean isBetterThan(CompositeMetric currentMetric);
}

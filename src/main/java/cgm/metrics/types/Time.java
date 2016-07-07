package cgm.metrics.types;

import cgm.metrics.FilterMetric;

/**
 * Created by Felipe on 07/07/2016.
 */
public class Time extends FilterMetric {

    public String getType() {
        return FilterMetric.METERS;
    }

    public double getSequentialQuality(double metric1, double metric2) {
        return metric1 + metric2;
    }

    public double getParallelQuality(double metric1, double metric2) {
        return Math.max(metric1, metric2);
    }
}

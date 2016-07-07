package cgm.metrics.types;

import cgm.metrics.CompositeMetric;
import cgm.metrics.FilterMetric;

/**
 * Created by Felipe on 07/07/2016.
 */
public class Reliability extends FilterMetric {
    public String getType() {
        return CompositeMetric.RELIABILTY;
    }

    public double getSequentialQuality(double metric1, double metric2) {
        return 0;
    }

    public double getParallelQuality(double metric1, double metric2) {
        return 0;
    }
}

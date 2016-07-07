package cgm.metrics;

/**
 * Created by Felipe on 07/07/2016.
 */
public abstract class CompositeMetric {
    public static final String RELIABILTY = "RELIABILITY";
    public static final String TIME = "SECONDS";

    public abstract String getType();

    public abstract double getSequentialQuality(double metric1, double metric2);

    public abstract double getParallelQuality(double metric1, double metric2);


}

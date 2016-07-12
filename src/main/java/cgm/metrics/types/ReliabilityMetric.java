package cgm.metrics.types;

import cgm.metrics.CompositeMetric;
import cgm.metrics.Metric;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Felipe on 07/07/2016.
 *
 * Reliability is a composite metric indicating how likely it is for a task or workflo to be correctly executed
 */
public class ReliabilityMetric extends CompositeMetric {
    private Logger logger = LogManager.getLogger();

    public String getType() {
        return Metric.RELIABILITY;
    }

    //TODO implement serial reliability calculations
    public double getSequentialQuality(double metric1, double metric2) {
        return 0;
    }

    //TODO implement parallel reliability calculations
    public double getParallelQuality(double metric1, double metric2) {
        return 0;
    }

    public boolean isBetterThan(CompositeMetric candidateMetric) {
        return candidateMetric.getValue() < this.getValue();
    }

    @Override
    public void setValue(double value) {
        if (value > 1) {
            logger.error("reliability cannot be greater than one.");
            (new Exception()).printStackTrace();
        }
        else super.setValue(value);
    }
}

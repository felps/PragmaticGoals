package br.ime.usp.improv.pragmatic.metrics.types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.ime.usp.improv.pragmatic.metrics.CompositeMetric;
import br.ime.usp.improv.pragmatic.metrics.Metric;

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

    public double getSequentialQuality(double metric1, double metric2) {
        return metric1 * metric2;
    }

    public double getParallelQuality(double metric1, double metric2) {
        return metric1 * metric2;
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

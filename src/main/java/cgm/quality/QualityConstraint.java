package cgm.quality;

import cgm.Comparison;
import cgm.Context;
import cgm.metrics.exceptions.DifferentMetricsException;

public abstract class QualityConstraint {
    protected Context applicableContext;
    protected String metric;
    protected double threshold;
    protected int comparison;

    public QualityConstraint(String metric, Context applicable, int comparison, double value) {
        this.metric = metric;
        this.applicableContext = applicable;
        this.comparison = comparison;
        this.threshold = value;
    }

    public Context getApplicableContext() {
        return applicableContext;
    }

    public String getMetric() {
        return metric;
    }

    public double getThreshold() {
        return this.threshold;
    }

    public int getComparison() {
        return comparison;
    }

    public boolean abidesByQC(double value, String metric) {
        if (metric.contentEquals(this.getMetric())) {
            if (!compare(value)) {
                return false;
            }
        }
        return true;
    }

    public abstract QualityConstraint stricterQC(QualityConstraint QualityConstraint) throws DifferentMetricsException;

    protected boolean compare(double value) {
        switch (this.comparison) {
            case Comparison.GREATER_THAN:
                return value > this.threshold;
            case Comparison.GREATER_OR_EQUAL_TO:
                return value >= this.threshold;
            case Comparison.EQUAL_TO:
                return value == this.threshold;
            case Comparison.LESS_OR_EQUAL_TO:
                return value <= this.threshold;
            case Comparison.LESS_THAN:
                return value < this.threshold;

        }
        return false;
    }
}

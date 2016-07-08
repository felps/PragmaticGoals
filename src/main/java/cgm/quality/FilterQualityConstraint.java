package cgm.quality;

import cgm.Comparison;
import cgm.Context;
import cgm.metrics.exceptions.DifferentMetricsException;

public class FilterQualityConstraint {
	private Context applicableContext;
	private String metric;
	private double threshold;
	private int comparison;

	public FilterQualityConstraint(Context applicable, String metric, double value, int comparison) {
		this.applicableContext = applicable;
		this.metric = metric;
		this.threshold = value;
		this.comparison = comparison;
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

	public boolean abidesByQC(float value, String metric) {
		if (metric.contentEquals(this.metric)) {
			if (!compare(value)) {
				return false;
			}
		}
		return true;
	}

	private boolean compare(double value) {
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

	public FilterQualityConstraint stricterQC(FilterQualityConstraint filterQualityConstraint) throws DifferentMetricsException {
		if (filterQualityConstraint.comparison == this.comparison) {

			if (filterQualityConstraint.metric.contentEquals(this.metric)) {

				if (filterQualityConstraint.compare(this.threshold))
					return this;
				else
					return filterQualityConstraint;
			} else
				throw (new DifferentMetricsException());
		} 
		return null;
	}
}

package gm.cgm;

import metrics.Metric;

public class QualityConstraint {
	private Context applicableContext;
	private Metric metric;
	private double threshold;
	private int comparison;

	public Context getApplicableContext() {
		return applicableContext;
	}

	public Metric getMetric() {
		return metric;
	}

	public double getThreshold() {
		return this.threshold;
	}

	public int getComparison() {
		return comparison;
	}

	public QualityConstraint(Context applicable, Metric metric, double value, int comparison) {
		this.applicableContext = applicable;
		this.metric = metric;
		this.threshold = value;
		this.comparison = comparison;
	}

	public boolean abidesByQC(float value, Metric metric) {
		if (metric.equals(this.metric)) {
			if (!compare(value)) {
				return false;
			}
		}
		return true;
	}

	private boolean compare(double value) {
		switch (this.comparison) {
			case Comparison.GREATER_THAN :
				if (value > this.threshold)
					return true;
				else
					return false;
			case Comparison.GREATER_OR_EQUAL_TO :
				if (value >= this.threshold)
					return true;
				else
					return false;
			case Comparison.EQUAL_TO :
				if (value == this.threshold)
					return true;
				else
					return false;
			case Comparison.LESS_OR_EQUAL_TO :
				if (value <= this.threshold)
					return true;
				else
					return false;
			case Comparison.LESS_THAN :
				if (value < this.threshold)
					return true;
				else
					return false;

		}
		return false;
	}

	public QualityConstraint stricterQC(QualityConstraint qualityConstraint) throws DifferentMetricsException {
		if (qualityConstraint.metric.getName().contentEquals(this.metric.getName())) {

			if (qualityConstraint.comparison == this.comparison) {
				if (qualityConstraint.compare(this.threshold))
					return this;
				else
					return qualityConstraint;
			}
		} else
			throw (new DifferentMetricsException());
		return null;
	}
}

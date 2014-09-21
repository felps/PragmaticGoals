package cgm;

public class QualityConstraint {
	private Context applicableContext;

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

	private String metric;
	private double threshold;
	private int comparison;

	public QualityConstraint(Context applicable, String metric, double value,
			int comparison) {
		this.applicableContext = applicable;
		this.metric = metric;
		this.threshold = value;
		this.comparison = comparison;
	}

	public boolean abidesByQC(float value, String metric) {
		if (metric.contentEquals(this.metric)) {
			if (compare(value)) {
				return true;
			}
		}
		return false;
	}

	private boolean compare(double value) {
		switch (this.comparison) {
		case Comparison.GREATER_THAN:
			if (value > this.threshold)
				return true;
			else
				return false;
		case Comparison.GREATER_OR_EQUAL_TO:
			if (value >= this.threshold)
				return true;
			else
				return false;
		case Comparison.EQUAL_TO:
			if (value == this.threshold)
				return true;
			else
				return false;
		case Comparison.LESS_OR_EQUAL_TO:
			if (value <= this.threshold)
				return true;
			else
				return false;
		case Comparison.LESS_THAN:
			if (value < this.threshold)
				return true;
			else
				return false;

		}
		return false;
	}

	public QualityConstraint stricterQC(QualityConstraint qualityConstraint) throws DifferentMetricsException{
		if (qualityConstraint.comparison == this.comparison) {
		
			if (qualityConstraint.metric.contentEquals(this.metric)) {
			
				if (qualityConstraint.compare(this.threshold))
					return this;
				else
					return qualityConstraint;
			}
		} else throw (new DifferentMetricsException());
		return null;
	}
}

package cgm;

public class QualityConstraint {
	private Context applicableContext;

	public Context getApplicableContext() {
		return applicableContext;
	}

	public String getMetric() {
		return metric;
	}

	public float getThreshold() {
		return this.threshold;
	}

	public String getComparison() {
		return comparison;
	}

	private String metric;
	private float threshold;
	private String comparison;

	public QualityConstraint(Context applicable, String metric, float value,
			String comparison) {
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

	private boolean compare(float value) {
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

	public QualityConstraint stricterQC(QualityConstraint qualityConstraint) {
		if (qualityConstraint.comparison.contentEquals(this.comparison)) {
		
			if (qualityConstraint.metric.contentEquals(this.metric)) {
			
				if (qualityConstraint.compare(this.threshold))
					return this;
				else
					return qualityConstraint;
			}
		}
		return null;
	}
}

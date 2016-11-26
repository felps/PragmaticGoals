package pragmatic.quality;

import pragmatic.Context;
import pragmatic.metrics.exceptions.DifferentMetricsException;

public class FilterQualityConstraint extends QualityConstraint {

	public FilterQualityConstraint(Context applicable, String metric, double value, int comparison) {
		super(metric, applicable, comparison, value);
	}

	public QualityConstraint stricterQC(QualityConstraint filterQualityConstraint) throws DifferentMetricsException {
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

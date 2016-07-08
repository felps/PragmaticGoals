package cgm.quality;

import cgm.Context;
import cgm.metrics.exceptions.DifferentMetricsException;

/**
 * Created by Felipe on 08/07/2016.
 */
public class CompositeQualityConstraint extends QualityConstraint {

    public CompositeQualityConstraint(String metric, Context applicable, int comparison, double value) {
        super(metric, applicable, comparison, value);
    }

    @Override
    public QualityConstraint stricterQC(QualityConstraint QualityConstraint) throws DifferentMetricsException {
        return null;
    }
}

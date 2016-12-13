package br.ime.usp.improv.pragmatic.quality;

import br.ime.usp.improv.pragmatic.Context;
import br.ime.usp.improv.pragmatic.metrics.exceptions.DifferentMetricsException;

/**
 * Created by Felipe on 08/07/2016.
 *
 * A composite quality constraint regards the workflow execution as a whole and not
 * just the task at hand.
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

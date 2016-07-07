package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.metrics.FilterMetric;

/**
 * Created by Felipe on 07/07/2016.
 */
public class SequentialAnnotation extends RuntimeAnnotation {

    public int getType() {
        return Sequential;
    }

    public void handleRefinement(Refinement refinement) {

    }

    public double getQuality(FilterMetric metric) {
        return 0;
    }
}

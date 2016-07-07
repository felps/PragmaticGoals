package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.metrics.FilterMetric;

/**
 * Created by Felipe on 29/06/2016.
 */
public class ParallelAnd extends RuntimeAnnotation {
    public int getType() {
        return RuntimeAnnotation.Sequential;
    }

    public void handleRefinement(Refinement refinement) {

    }

    public double getQuality(FilterMetric metric) {
        return 0;
    }
}

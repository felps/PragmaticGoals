package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.metrics.FilterMetric;

import java.util.HashMap;

/**
 * Created by Felipe on 29/06/2016.
 */
public abstract class RuntimeAnnotation {

    public static final int Try = 0;
    public static final int Sequential = 1;
    public static final int Interleaved = 2;
    public static final int SequentiallyIterated = 3;
    public static final int InterleavedIterated = 4;
    public static final int Alternative = 5;
    public static final int skip = 6;


    private HashMap<Integer, Refinement> sequence;

    public RuntimeAnnotation() {
        sequence = new HashMap<Integer, Refinement>();
    }

    public abstract int getType();

    public void includeRefinement(Refinement refinement, int position) {
        sequence.put(position, refinement);
        handleRefinement(refinement);
    }

    public abstract void handleRefinement(Refinement refinement);

    public abstract double getQuality(FilterMetric metric);

}

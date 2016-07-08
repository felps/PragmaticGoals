package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.workflow.Plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    private List<Refinement> sequence;

    public RuntimeAnnotation() {
        sequence = Collections.synchronizedList(new ArrayList<Refinement>());
    }

    public List<Refinement> getRefinements() {
        return sequence;
    }

    public void includeRefinement(Refinement refinement, int position) {
        sequence.add(refinement);
    }

    public abstract int getType();

    public abstract List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches);

}
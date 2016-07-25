package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.workflow.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 25/07/2016.
 */
public class InterleavedCardinalAnnotation extends CardinalityAnnotation {

    @Override
    public List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {
        int i;
        ArrayList<Plan> list = new ArrayList<Plan>();

        if (getRefinements().size() == 0)
            return null;

        Plan fullPlan = clonePlan(approaches.get(getRefinements().get(0)), 0);
        Plan originalVersion = approaches.get(getRefinements().get(0));

        for (i = 1; i < super.iterations; i++) {
            Plan plan = clonePlan(originalVersion, i);
            fullPlan.addParallel(plan);
        }
        list.add(fullPlan);
        return list;
    }

}

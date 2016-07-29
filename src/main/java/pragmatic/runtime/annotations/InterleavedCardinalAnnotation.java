package pragmatic.runtime.annotations;

import pragmatic.Refinement;
import pragmatic.workflow.Plan;

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

        Plan originalVersion = approaches.get(getRefinements().get(0));
        Plan originalPlan = clonePlan(originalVersion, 0);

        if (originalPlan != null) {
            Plan fullPlan = clonePlan(originalPlan, 0);
            fullPlan.setAchievable(originalVersion.isAchievable());

            for (i = 1; i < super.iterations; i++) {
                Plan plan = clonePlan(originalPlan, i);
                fullPlan.addParallel(plan);
            }
            list.add(fullPlan);
        }
        return list;
    }

}

package pragmatic.runtime.annotations;

import pragmatic.Refinement;
import pragmatic.workflow.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 25/07/2016.
 */
public class SequentialCardinalAnnotation extends CardinalityAnnotation {

    @Override
    public List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {
        int i;
        ArrayList<Plan> list = new ArrayList<Plan>();

        if (getRefinements().size() == 0)
            return null;

        Plan fullPlan = new Plan();
        Plan originalClone = approaches.get(getRefinements().get(0));

        if(originalClone!=null) {
            fullPlan.setAchievable(originalClone.isAchievable());
            for (i = 0; i < super.iterations; i++) {
                Plan plan = clonePlan(originalClone, i);
                fullPlan.addSerial(plan);
            }
        } else fullPlan.setAchievable(false);
        list.add(fullPlan);
        return list;
    }

}

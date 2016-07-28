package pragmatic.runtime.annotations;

import pragmatic.Refinement;
import pragmatic.workflow.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 25/07/2016.
 */
public class TryAnnotation extends RuntimeAnnotation {

    @Override
    public List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {
        Refinement tryRequirement = getRefinements().get(0);
        Refinement thenRequirement = getRefinements().get(1);
        Refinement elseRequirement = getRefinements().get(2);

        Plan tryPlan = approaches.get(tryRequirement);
        Plan thenPlan = approaches.get(thenRequirement);
        Plan elsePlan = approaches.get(elseRequirement);

        if(tryPlan == null && elsePlan == null){
            return null;
        }

        ArrayList<Plan> plans = new ArrayList<>(1);
        if(tryPlan == null && elsePlan != null){
            plans.add(elsePlan);
            return plans;
        }

        if (tryPlan.isAchievable()) {
            tryPlan.addSerial(thenPlan);
            tryPlan.setAchievable(thenPlan.isAchievable());
        } else {
            if(elsePlan != null) {
                tryPlan.addSerial(elsePlan);
                tryPlan.setAchievable(elsePlan.isAchievable());
            } else return null;
        }

        plans.add(tryPlan);

        return plans;
    }
}

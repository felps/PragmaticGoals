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


        if (tryPlan.isAchievable()) {
            tryPlan.addSerial(thenPlan);
        } else {
            tryPlan.addSerial(elsePlan);
        }

        ArrayList<Plan> plans = new ArrayList<>(1);
        plans.add(tryPlan);

        return plans;
    }
}

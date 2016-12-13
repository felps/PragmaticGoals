package br.ime.usp.improv.pragmatic.runtime.annotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.ime.usp.improv.pragmatic.Refinement;
import br.ime.usp.improv.pragmatic.WorkflowPlan;

/**
 * Created by Felipe on 25/07/2016.
 */
public class TryAnnotation extends RuntimeAnnotation {

    @Override
    public List<WorkflowPlan> getPossiblePlans(Map<Refinement, WorkflowPlan> approaches) {
        Refinement tryRequirement = getRefinements().get(0);
        Refinement thenRequirement = getRefinements().get(1);
        Refinement elseRequirement = getRefinements().get(2);

        WorkflowPlan tryPlan = approaches.get(tryRequirement);
        WorkflowPlan thenPlan = approaches.get(thenRequirement);
        WorkflowPlan elsePlan = approaches.get(elseRequirement);

        if(tryPlan == null && elsePlan == null){
            return null;
        }

        ArrayList<WorkflowPlan> plans = new ArrayList<>(1);
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

package br.ime.usp.improv.pragmatic.runtime.annotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.ime.usp.improv.pragmatic.Refinement;
import br.ime.usp.improv.pragmatic.WorkflowPlan;

/**
 * Created by Felipe on 25/07/2016.
 */
public class InterleavedCardinalAnnotation extends CardinalityAnnotation {

    @Override
    public List<WorkflowPlan> getPossiblePlans(Map<Refinement, WorkflowPlan> approaches) {
        int i;
        ArrayList<WorkflowPlan> list = new ArrayList<WorkflowPlan>();

        if (getRefinements().size() == 0)
            return null;

        WorkflowPlan originalVersion = approaches.get(getRefinements().get(0));
        WorkflowPlan originalPlan = clonePlan(originalVersion, 0);

        if (originalPlan != null) {
            WorkflowPlan fullPlan = clonePlan(originalPlan, 0);
            fullPlan.setAchievable(originalVersion.isAchievable());

            for (i = 1; i < super.iterations; i++) {
                WorkflowPlan plan = clonePlan(originalPlan, i);
                fullPlan.addParallel(plan);
            }
            list.add(fullPlan);
        }
        return list;
    }

}

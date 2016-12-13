package br.ime.usp.improv.pragmatic.runtime.annotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.ime.usp.improv.pragmatic.Refinement;
import br.ime.usp.improv.pragmatic.WorkflowPlan;

/**
 * Created by Felipe on 25/07/2016.
 */
public class SequentialCardinalAnnotation extends CardinalityAnnotation {

    @Override
    public List<WorkflowPlan> getPossiblePlans(Map<Refinement, WorkflowPlan> approaches) {
        int i;
        ArrayList<WorkflowPlan> list = new ArrayList<WorkflowPlan>();

        if (getRefinements().size() == 0)
            return null;

        WorkflowPlan fullPlan = new WorkflowPlan();
        WorkflowPlan originalClone = approaches.get(getRefinements().get(0));

        if(originalClone!=null) {
            fullPlan.setAchievable(originalClone.isAchievable());
            for (i = 0; i < super.iterations; i++) {
                WorkflowPlan plan = clonePlan(originalClone, i);
                fullPlan.addSerial(plan);
            }
        } else fullPlan.setAchievable(false);
        list.add(fullPlan);
        return list;
    }

}

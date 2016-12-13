package br.ime.usp.improv.pragmatic;

import java.util.Set;

/**
 * Created by fpont_000 on 28/07/2016.
 */
public class Skip extends Refinement {

    public Skip() {
        setTimeConsumed(0);
        try {
            setReliability(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public WorkflowPlan isAchievable(Set<Context> current, Interpretation interp) {
        WorkflowPlan plan = new WorkflowPlan();
        plan.setAchievable(true);
        plan.setTimeConsumed(0);
        plan.setReliability(1);
        return plan;
    }

    @Override
    public int size() {
        return 0;
    }
}

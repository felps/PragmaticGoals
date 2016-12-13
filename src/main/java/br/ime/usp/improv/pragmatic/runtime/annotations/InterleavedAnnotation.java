package br.ime.usp.improv.pragmatic.runtime.annotations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.ime.usp.improv.pragmatic.Refinement;
import br.ime.usp.improv.pragmatic.WorkflowPlan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 07/07/2016.
 */
public class InterleavedAnnotation extends RuntimeAnnotation {
    Logger logger = LogManager.getLogger();

    public int getType() {
        return RuntimeAnnotation.Interleaved;
    }

    @Override
    public List<WorkflowPlan> getPossiblePlans(Map<Refinement, WorkflowPlan> approaches) {
        List<WorkflowPlan> list = new ArrayList<WorkflowPlan>();
        list.add(getPlan(approaches.values()));
        logger.debug("I am the interleaved experiment.runtime annotation and I have devised " + list.size() + " plans ");

        return list;
    }

    private WorkflowPlan getPlan(Collection<WorkflowPlan> refinementPlans) {
        WorkflowPlan completePlan = new WorkflowPlan();
        completePlan.setAchievable(true);
        logger.debug("I am the interleaved experiment.runtime annotation and I have been given " + refinementPlans.size() + " plans");
        for (WorkflowPlan refinementPlan : refinementPlans) {
            completePlan.addParallel(refinementPlan);
        }
        return completePlan;
    }


}

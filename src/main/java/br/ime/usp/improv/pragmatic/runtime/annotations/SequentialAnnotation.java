package br.ime.usp.improv.pragmatic.runtime.annotations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.ime.usp.improv.pragmatic.Goal;
import br.ime.usp.improv.pragmatic.Refinement;
import br.ime.usp.improv.pragmatic.WorkflowPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 07/07/2016.
 */
public class SequentialAnnotation extends RuntimeAnnotation {
    private static final Logger logger = LogManager.getLogger(Goal.class);

    @Override
    public synchronized List<WorkflowPlan> getPossiblePlans(Map<Refinement, WorkflowPlan> approaches) {

        List<Refinement> consideredRefinements;
        List<WorkflowPlan> list = new ArrayList<WorkflowPlan>();

        WorkflowPlan fullPlan;
            consideredRefinements = getRefinements();
            fullPlan = getPlan(approaches, consideredRefinements);
            logger.debug("I am SequentialAnnotation and I found a plan for this refinement with {} tasks.", fullPlan.getTasks().size());
            logger.debug("I am SequentialAnnotation and the plan achievability is " + fullPlan.isAchievable());
            list.add(fullPlan);
            return list;
    }

    private WorkflowPlan getPlan(Map<Refinement, WorkflowPlan> approaches, List<Refinement> consideredRefinements) {
        WorkflowPlan fullPlan = new WorkflowPlan();
        int i;
        fullPlan.setAchievable(true);

        for (i = 0; i < consideredRefinements.size(); i++) {
            WorkflowPlan dependencyPlan = approaches.get(consideredRefinements.get(i));
            if (dependencyPlan == null) {
                List<WorkflowPlan> emptyPlanList = new ArrayList<WorkflowPlan>(1);
                WorkflowPlan unachievable = new WorkflowPlan();
                unachievable.setAchievable(false);
                logger.debug("I am SequentialAnnotation and there is no plan available for this refinement");
                return unachievable;
            }
            if (!dependencyPlan.isAchievable())
                fullPlan.setAchievable(false);

            logger.debug("Adding {} tasks to plan previously with {} tasks", dependencyPlan.getTasks().size(), fullPlan.getTasks().size());
            fullPlan.addSerial(dependencyPlan);
        }
        return fullPlan;
    }
}

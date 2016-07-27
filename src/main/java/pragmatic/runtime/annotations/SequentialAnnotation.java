package pragmatic.runtime.annotations;

import pragmatic.Goal;
import pragmatic.Refinement;
import pragmatic.workflow.Plan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 07/07/2016.
 */
public class SequentialAnnotation extends RuntimeAnnotation {
    private static final Logger logger = LogManager.getLogger(Goal.class);

    @Override
    public synchronized List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {

        List<Refinement> consideredRefinements;
        List<Plan> list = new ArrayList<Plan>();

        Plan fullPlan;
            consideredRefinements = getRefinements();
            fullPlan = getPlan(approaches, consideredRefinements);
            logger.debug("I am SequentialAnnotation and I found a plan for this refinement with {} tasks.", fullPlan.getTasks().size());
            logger.debug("I am SequentialAnnotation and the plan achievability is " + fullPlan.isAchievable());
            list.add(fullPlan);
            return list;
    }

    private Plan getPlan(Map<Refinement, Plan> approaches, List<Refinement> consideredRefinements) {
        Plan fullPlan = new Plan();
        int i;
        fullPlan.setAchievable(true);

        for (i = 0; i < consideredRefinements.size(); i++) {
            Plan dependencyPlan = approaches.get(consideredRefinements.get(i));
            if (dependencyPlan == null) {
                List<Plan> emptyPlanList = new ArrayList<Plan>(1);
                Plan unachievable = new Plan();
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
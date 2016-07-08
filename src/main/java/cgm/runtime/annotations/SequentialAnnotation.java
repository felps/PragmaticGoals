package cgm.runtime.annotations;

import cgm.Goal;
import cgm.Refinement;
import cgm.metrics.types.ReliabilityMetric;
import cgm.metrics.types.TimeMetric;
import cgm.workflow.Plan;
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

    public int getType() {
        return Sequential;
    }

    @Override
    public synchronized List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {
        int i;
        Plan fullPlan = new Plan();
        TimeMetric time = new TimeMetric();
        ReliabilityMetric reliability = new ReliabilityMetric();

        double fullTime = 0;
        double fullReliability = 1;
        boolean achievable = true;

        for (i = 0; i < getRefinements().size(); i++) {
            Plan dependencyPlan = approaches.get(getRefinements().get(i));
            if (dependencyPlan == null) {
                List<Plan> emptyPlanList = new ArrayList<Plan>(1);
                Plan unachievable = new Plan();
                emptyPlanList.add(unachievable);
                unachievable.setAchievable(false);
                logger.debug("I am SequentialAnnotation and there is no plan available for this refinement");
                return emptyPlanList;
            }
            if (!dependencyPlan.isAchievable())
                achievable = false;

            logger.debug("Adding {} tasks to plan previously with {} tasks", dependencyPlan.getTasks().size(), fullPlan.getTasks().size());
            fullPlan.addSerial(dependencyPlan);
            fullReliability = reliability.getSequentialQuality(fullReliability, dependencyPlan.getReliability());
            fullTime = time.getSequentialQuality(fullTime, dependencyPlan.getTimeConsumed());
        }

        fullPlan.setTimeConsumed(fullTime);
        fullPlan.setReliability(fullReliability);
        List<Plan> list = new ArrayList<Plan>();
        fullPlan.setAchievable(achievable);
        logger.debug("I am SequentialAnnotation and I found a plan for this refinement with {} tasks.", fullPlan.getTasks().size());
        logger.debug("I am SequentialAnnotation and the plan achievability is " + achievable);

        list.add(fullPlan);

        return list;
    }


}

package cgm.runtime.annotations;

import cgm.Goal;
import cgm.Refinement;
import cgm.workflow.Plan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {
        List<Plan> list = new ArrayList<Plan>();
        if (goalType == Goal.AND) {
            list.add(getPlan(approaches.values()));
        } else {
            if (goalType == Goal.AND) {
                list.add(getPlan(approaches.values()));
            }
        }
        logger.debug("I am the interleaved experiment.runtime annotation and I have devised " + list.size() + " plans ");

        return list;
    }

    private Plan getPlan(Collection<Plan> approaches) {
        Plan completePlan = new Plan();

        completePlan.setAchievable(true);

        logger.debug("I am the interleaved experiment.runtime annotation and I have been given " + approaches.size() + " plans");
        for (Plan refinementPlan : approaches) {
            completePlan.addParallel(refinementPlan);
        }

        return completePlan;
    }


}

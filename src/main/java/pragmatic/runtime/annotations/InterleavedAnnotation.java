package pragmatic.runtime.annotations;

import pragmatic.Refinement;
import pragmatic.workflow.Plan;
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
        list.add(getPlan(approaches.values()));
        logger.debug("I am the interleaved experiment.runtime annotation and I have devised " + list.size() + " plans ");

        return list;
    }

    private Plan getPlan(Collection<Plan> refinementPlans) {
        Plan completePlan = new Plan();
        completePlan.setAchievable(true);
        logger.debug("I am the interleaved experiment.runtime annotation and I have been given " + refinementPlans.size() + " plans");
        for (Plan refinementPlan : refinementPlans) {
            completePlan.addParallel(refinementPlan);
        }
        return completePlan;
    }


}

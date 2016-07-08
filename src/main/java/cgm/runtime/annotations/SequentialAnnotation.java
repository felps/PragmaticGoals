package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.metrics.types.Reliability;
import cgm.metrics.types.Time;
import cgm.workflow.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 07/07/2016.
 */
public class SequentialAnnotation extends RuntimeAnnotation {

    public int getType() {
        return Sequential;
    }

    @Override
    public synchronized List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {
        int i;
        Plan fullPlan = new Plan();
        Time time = new Time();
        Reliability reliability = new Reliability();

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
                return emptyPlanList;
            }
            if (!dependencyPlan.isAchievable())
                achievable = false;
            if (dependencyPlan != null) {
                fullPlan.addSerial(dependencyPlan);
                fullTime = time.getSequentialQuality(fullTime, dependencyPlan.getTimeConsumed());
                fullReliability = reliability.getSequentialQuality(fullReliability, dependencyPlan.getReliability());
            }
        }

        List<Plan> list = new ArrayList<Plan>();
        fullPlan.setAchievable(achievable);
        list.add(fullPlan);

        return list;
    }


}

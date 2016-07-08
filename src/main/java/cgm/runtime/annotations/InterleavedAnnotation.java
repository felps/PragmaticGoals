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
public class InterleavedAnnotation extends RuntimeAnnotation {
    public int getType() {
        return RuntimeAnnotation.Interleaved;
    }

    @Override
    public List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {
        List<Plan> list = new ArrayList<Plan>();
        Plan completePlan = new Plan();

        completePlan.setAchievable(true);

        Time time = new Time();
        Reliability reliability = new Reliability();

        //System.out.println("I am the interleaved experiment.runtime annotation and I have been given " + approaches.size() + " plans");
        for (Refinement ref : getRefinements()) {
            Plan refinementPlan = approaches.get(ref);
            completePlan.addParallel(refinementPlan);

            completePlan.setTimeConsumed(time.getParallelQuality(completePlan.getTimeConsumed(), refinementPlan.getTimeConsumed()));
            completePlan.setReliability(reliability.getParallelQuality(completePlan.getReliability(), refinementPlan.getReliability()));

            //System.out.println("I am the interleaved experiment.runtime annotation and I have added the approach for " + ref.getIdentifier() + " dependency");
            //System.out.println("It has " + refinementPlan.getTasks().size() + " tasks");
        }

        list.add(completePlan);

        //System.out.println("I am the interleaved experiment.runtime annotation and I have devised " + list.size() + " plans with " + completePlan.getTasks().size() + " tasks");
        return list;
    }


}

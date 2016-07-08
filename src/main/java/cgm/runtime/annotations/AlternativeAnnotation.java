package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.workflow.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 07/07/2016.
 */
public class AlternativeAnnotation extends RuntimeAnnotation {
    public int getType() {
        return RuntimeAnnotation.Alternative;
    }

    @Override
    public List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {
        List<Plan> alternatives = new ArrayList<Plan>();
        for (Refinement ref : getRefinements()) {
            alternatives.add(approaches.get(ref));
            System.out.println(ref.getIdentifier() + ": " + approaches.get(ref).getTasks().size());
        }
        return alternatives;
    }

}

package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.workflow.Plan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 07/07/2016.
 */
public class AlternativeAnnotation extends RuntimeAnnotation {
    Logger logger = LogManager.getLogger();

    public int getType() {
        return RuntimeAnnotation.Alternative;
    }

    @Override
    public List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {
        List<Plan> alternatives = new ArrayList<Plan>();
        for (Refinement ref : getRefinements()) {
            alternatives.add(approaches.get(ref));
        }
        return alternatives;
    }

}

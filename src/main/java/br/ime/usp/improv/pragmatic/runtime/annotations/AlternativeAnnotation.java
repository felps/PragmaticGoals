package br.ime.usp.improv.pragmatic.runtime.annotations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.ime.usp.improv.pragmatic.Refinement;
import br.ime.usp.improv.pragmatic.WorkflowPlan;

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
    public List<WorkflowPlan> getPossiblePlans(Map<Refinement, WorkflowPlan> approaches) {
        List<WorkflowPlan> alternatives = new ArrayList<WorkflowPlan>();
        alternatives.addAll(approaches.values());
        return alternatives;
    }

}

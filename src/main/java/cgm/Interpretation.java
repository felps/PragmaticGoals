package cgm;

import cgm.metrics.Metric;
import cgm.quality.CompositeQualityConstraint;
import cgm.quality.FilterQualityConstraint;
import cgm.workflow.Plan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Interpretation {

    private static final Logger logger = LogManager.getLogger(Interpretation.class);

    private HashMap<Context, Set<FilterQualityConstraint>> contextDependentInterpretation;
    private HashSet<FilterQualityConstraint> qualityConstraints;
    private CompositeQualityConstraint compositeQC;

    public Interpretation() {
        contextDependentInterpretation = new HashMap<Context, Set<FilterQualityConstraint>>();
        qualityConstraints = new HashSet<FilterQualityConstraint>();
        compositeQC = null;
    }

    public HashMap<Context, Set<FilterQualityConstraint>> getContextDependentInterpretation() {
        return contextDependentInterpretation;
    }

    public CompositeQualityConstraint getCompositeQC() {
        return compositeQC;
    }

    public void addCompositeQualityConstraint(CompositeQualityConstraint compositeConstraint) throws Exception {
        if (compositeQC == null) {
            compositeQC = compositeConstraint;
        } else {
            throw new PreviousCompositeConstraintException("Composite exception already set");
        }

    }

    public void addFilterQualityConstraint(FilterQualityConstraint constraint) {
        qualityConstraints.add(constraint);

        Context context = constraint.getApplicableContext();

        if (contextDependentInterpretation.containsKey(context)) {
            contextDependentInterpretation.get(context).add(constraint);
        } else {
            HashSet<FilterQualityConstraint> constraintSet = new HashSet<FilterQualityConstraint>();
            constraintSet.add(constraint);
            contextDependentInterpretation.put(context, constraintSet);
        }
    }

    public Set<FilterQualityConstraint> getQualityConstraints(Set<Context> current) {
        HashSet<FilterQualityConstraint> allQCs = new HashSet<FilterQualityConstraint>();
        if (current != null)
            for (Context context : current) {
                if (contextDependentInterpretation.containsKey(context)) {
                    allQCs.addAll(contextDependentInterpretation.get(context));
                }
            }
        else if (contextDependentInterpretation.containsKey(null)) {
            allQCs.addAll(contextDependentInterpretation.get(null));
        }
        return allQCs;
    }

    public void merge(Interpretation interp) {
        if (interp == null)
            return;
        for (FilterQualityConstraint filterQualityConstraint : interp.getAllQualityConstraints()) {
            addFilterQualityConstraint(filterQualityConstraint);
        }
    }

    private HashSet<FilterQualityConstraint> getAllQualityConstraints() {
        return qualityConstraints;
    }

    public boolean withinLimits(Plan approach) {
        if (approach == null) {
            logger.debug("You have presented me nothing!");
            return false;
        } else if (compositeQC == null) {
            //logger.debug("I have no composite quality constraint so anything is fine, really");
            return true;
        } else {
            logger.debug("does this option work for me? {}!! Is {} {} better than {} {}? ",
                    compositeQC.abidesByQC(approach.getTimeConsumed(), Metric.TIME), compositeQC.getMetric(),
                    approach.getTimeConsumed(), compositeQC.getMetric(), compositeQC.getThreshold(), compositeQC.getMetric());
            return compositeQC.abidesByQC(approach.getTimeConsumed(), Metric.TIME);
        }
    }
}

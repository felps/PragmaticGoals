package cgm;

import cgm.metrics.CompositeMetric;
import cgm.runtime.annotations.AlternativeAnnotation;
import cgm.runtime.annotations.RuntimeAnnotation;
import cgm.runtime.annotations.SequentialAnnotation;
import cgm.workflow.Plan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Goal extends Refinement {

    public final static boolean OR = true;
    public final static boolean AND = false;
    private static final Logger logger = LogManager.getLogger();
    private final boolean isOrDecomposition;

    protected ArrayList<Refinement> dependencies = new ArrayList<Refinement>();
    protected HashMap<Context, List<Refinement>> dependenciesPerContext = new HashMap<>();

    private RuntimeAnnotation runtimeAnnotation;

    public Goal(boolean isOrDecomposition) {
        super();
        this.isOrDecomposition = isOrDecomposition;
        if (isOrDecomposition()) {
            runtimeAnnotation = new AlternativeAnnotation();
        } else {
            runtimeAnnotation = new SequentialAnnotation();
        }
        runtimeAnnotation.setGoalType(isOrDecomposition());
    }

    public Goal(boolean isOrDecomposition, RuntimeAnnotation annotation) {
        super();
        this.isOrDecomposition = isOrDecomposition;
        runtimeAnnotation = annotation;
        runtimeAnnotation.setGoalType(isOrDecomposition());
    }

    public List<Refinement> getDependencies() {
        return dependencies;
    }

    public Set<Refinement> getApplicableDependencies(Set<Context> current) {

        HashSet<Refinement> applicableDeps = new HashSet<Refinement>();
        if (dependenciesPerContext.containsKey(null))
            applicableDeps.addAll(dependenciesPerContext.get(null));
        for (Context context : current) {
            if (dependenciesPerContext.containsKey(context))
                applicableDeps.addAll(dependenciesPerContext.get(context));
        }
        return applicableDeps;
    }

    public RuntimeAnnotation getRuntimeAnnotation() {
        return runtimeAnnotation;
    }

    public void setRuntimeAnnotation(RuntimeAnnotation runtimeAnnotation) {
        this.runtimeAnnotation = runtimeAnnotation;
        runtimeAnnotation.setGoalType(isOrDecomposition());
    }

	public boolean isOrDecomposition() {
		return isOrDecomposition;	}

	public boolean isAndDecomposition() {
		return !isOrDecomposition;	}

	@Override
    public Plan isAchievable(Set<Context> current, Interpretation interp) {
        if (current == null) {
            current = new HashSet<>();
            current.add(null);
        }
        if (!this.isApplicable(current)) {
            return null;
        } else {
            Plan plan;
            HashMap<Refinement, Plan> refinementPlans = getRefinementPlans(current, interp);
            List<Plan> approaches = getRuntimeAnnotation().getPossiblePlans(refinementPlans);

            Plan chosenApproach = null;

            for (Plan currentApproach : approaches) {
                if (currentApproach != null) {
                    if (chosenApproach == null) {
                        chosenApproach = currentApproach;
                        logger.debug("I am {} and i chose an approach with {} reliability and {} time", getIdentifier(), getReliability(), getTimeConsumed());
                    } else {
                        if (!chosenApproach.isAchievable()) {
                            if (currentApproach.isAchievable())
                                chosenApproach = currentApproach;
                            else
                                chosenApproach = chooseBetterPlan(interp, chosenApproach, currentApproach);
                        } else if (chosenApproach.isAchievable()) {
                            chosenApproach = chooseBetterPlan(interp, chosenApproach, currentApproach);
                        }
                    }

                    if (interp != null && !interp.withinLimits(chosenApproach)) {
                        logger.debug("Yet it is unachievable");
                        chosenApproach.setAchievable(false);
                    } else if (chosenApproach.isAchievable()) {
                        logger.debug("and it can be achieved!");
                    } else logger.debug("Yet it is unachievable 2");
                }
            }
            return chosenApproach;
        }
    }

    private Plan chooseBetterPlan(Interpretation interp, Plan chosenApproach, Plan approach) {
        if (interp != null && interp.getCompositeQC() != null) {
            String compositeMetric = interp.getCompositeQC().getMetric();
            CompositeMetric currentMetric = chosenApproach.getQualityMetrics(compositeMetric);
            CompositeMetric candidate = approach.getQualityMetrics(compositeMetric);
            if (candidate != null) {
                if (candidate.isBetterThan(currentMetric)) {
                    chosenApproach = approach;
                    logger.debug("I am {} and i switched to an approach with {} reliability and {} time", getIdentifier(), getReliability(), getTimeConsumed());
                } else
                    logger.debug("I reviewed my options {} and did not choose an approach with {} reliability and {} time", getIdentifier(), getReliability(), getTimeConsumed());
            }
        }
        return chosenApproach;
    }

    public void addDependency(Refinement goal) {
        dependencies.add(goal);
        for (Context context : goal.getApplicableContexts()) {
            if (!dependenciesPerContext.containsKey(context)) {
                ArrayList<Refinement> refinementArrayList = new ArrayList<>();
                dependenciesPerContext.put(context, refinementArrayList);
                dependenciesPerContext.put(context, refinementArrayList);
            }
            dependenciesPerContext.get(context).add(goal);
        }
        getRuntimeAnnotation().includeRefinement(goal, getRuntimeAnnotation().getRefinements().size());
    }

    private HashMap<Refinement, Plan> getRefinementPlans(Set<Context> current, Interpretation interp) {
        Plan plan;
        HashMap<Refinement, Plan> approaches = new HashMap<>();

        for (Refinement dep : getApplicableDependencies(current)) {
            plan = dep.isAchievable(current, interp);
            if (plan != null) {
                approaches.put(dep, plan);
                if (plan.isAchievable())
                    logger.debug("I am " + getIdentifier() + " and i found a way to achieve" +
                            " the dependency " + dep.getIdentifier() + " with " + plan.getTasks().size() + " tasks.");
                else
                    logger.debug("I am " + getIdentifier() + " and the best i can do to achieve" +
                            " the dependency " + dep.getIdentifier() + " will require" + plan.getTasks().size() + " tasks.");
            }
        }

        return approaches;
    }

    public int size() {
        int amount = 1;
        for (Refinement ref : dependencies) {
            amount += ref.size();
        }
        return amount;
    }

}

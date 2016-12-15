package br.ime.usp.improv.pragmatic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.ime.usp.improv.pragmatic.metrics.CompositeMetric;
import br.ime.usp.improv.pragmatic.runtime.annotations.AlternativeAnnotation;
import br.ime.usp.improv.pragmatic.runtime.annotations.RuntimeAnnotation;
import br.ime.usp.improv.pragmatic.runtime.annotations.SequentialAnnotation;

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

    /**
     * Given a context set "current", which dependencies can be applied?
     * <p>
     * Answer: Those that can always be applied (null context) and those
     * applicable to an active context
     */
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
    public WorkflowPlan isAchievable(Set<Context> current, Interpretation interp) {
        if (current == null) {
            current = new HashSet<>();
            current.add(null);
        }
        if (!this.isApplicable(current)) {
            return null;
        } else {
            WorkflowPlan plan;
            HashMap<Refinement, WorkflowPlan> refinementPlans = getRefinementPlans(current, interp);
            List<WorkflowPlan> approaches = getRuntimeAnnotation().getPossiblePlans(refinementPlans);

            WorkflowPlan chosenApproach = null;
            if(approaches == null) return null;

            for (WorkflowPlan currentApproach : approaches) {
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

    private WorkflowPlan chooseBetterPlan(Interpretation interp, WorkflowPlan chosenApproach, WorkflowPlan approach) {
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
    	goal.isExecutedBy(this.getExecutingActor());
        dependencies.add(goal);
        for (Context context : goal.getApplicableContexts()) {
            if (!dependenciesPerContext.containsKey(context)) {
                ArrayList<Refinement> refinementArrayList = new ArrayList<>();
                dependenciesPerContext.put(context, refinementArrayList);
            }
            dependenciesPerContext.get(context).add(goal);
        }
        getRuntimeAnnotation().includeRefinement(goal, getRuntimeAnnotation().getRefinements().size());
    }

    private HashMap<Refinement, WorkflowPlan> getRefinementPlans(Set<Context> current, Interpretation interp) {
        WorkflowPlan plan;
        HashMap<Refinement, WorkflowPlan> approaches = new HashMap<>();

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
    
    @Override
    public void isExecutedBy(String executedBy) {
    	for (Refinement refinement : dependencies) {
			refinement.isExecutedBy(executedBy);
		}
    	super.isExecutedBy(executedBy);
    }

    public int size() {
        int amount = 1;
        for (Refinement ref : dependencies) {
            amount += ref.size();
        }
        return amount;
    }

}

package cgm;

import cgm.metrics.CompositeMetric;
import cgm.runtime.annotations.AlternativeAnnotation;
import cgm.runtime.annotations.RuntimeAnnotation;
import cgm.runtime.annotations.SequentialAnnotation;
import cgm.workflow.Plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Goal extends Refinement {

    public final static boolean OR = true;
    public final static boolean AND = false;
    private final boolean isOrDecomposition;

    private RuntimeAnnotation runtimeAnnotation;

    public Goal(boolean isOrDecomposition) {
        super();
        dependencies = new ArrayList<Refinement>();
        this.isOrDecomposition = isOrDecomposition;
        if (isOrDecomposition()) {
            runtimeAnnotation = new AlternativeAnnotation();
        } else {
            runtimeAnnotation = new SequentialAnnotation();
        }
    }

    public Goal(boolean isOrDecomposition, RuntimeAnnotation annotation) {
        super();
        dependencies = new ArrayList<Refinement>();
        this.isOrDecomposition = isOrDecomposition;
        runtimeAnnotation = annotation;
    }

    public RuntimeAnnotation getRuntimeAnnotation() {
        return runtimeAnnotation;
    }

    public void setRuntimeAnnotation(RuntimeAnnotation runtimeAnnotation) {
        this.runtimeAnnotation = runtimeAnnotation;
    }

    @Override
    public int myType() {
        return Refinement.GOAL;	}

	public boolean isOrDecomposition() {
		return isOrDecomposition;	}

	public boolean isAndDecomposition() {
		return !isOrDecomposition;	}

	@Override
    public Plan isAchievable(Set<Context> current, Interpretation interp, String compositeMetric) {
        if (!this.isApplicable(current)) {
            return null;
        } else {
            Plan plan;
            HashMap<Refinement, Plan> refinementPlans = getRefinementPlans(current, interp, compositeMetric);
            List<Plan> approaches = getRuntimeAnnotation().getPossiblePlans(refinementPlans);

            Plan chosenApproach = null;

            for (Plan approach : approaches) {
                if (chosenApproach == null || (!chosenApproach.isAchievable() && approach.isAchievable())) {
                    chosenApproach = approach;
                } else {
                    CompositeMetric currentMetric = chosenApproach.getQualityMetrics(compositeMetric);
                    CompositeMetric candidate = approach.getQualityMetrics(compositeMetric);
                    if (candidate != null) {
                        if (candidate.isBetterThan(currentMetric))
                            chosenApproach = approach;
                    }
                }
                if (interp != null && !interp.withinLimits(chosenApproach, compositeMetric)) {
                    chosenApproach.setAchievable(false);
                }
            }
            return chosenApproach;
        }
    }

    @Override
    public void addDependency(Refinement goal) {
        dependencies.add(goal);
        getRuntimeAnnotation().includeRefinement(goal, getRuntimeAnnotation().getRefinements().size());
    }

    private HashMap<Refinement, Plan> getRefinementPlans(Set<Context> current, Interpretation interp, String compositeMetric) {
        Plan plan;
        HashMap<Refinement, Plan> approaches = new HashMap<Refinement, Plan>();

        for (Refinement dep : getApplicableDependencies(current)) {
            plan = dep.isAchievable(current, interp, compositeMetric);
            if (plan != null)
                approaches.put(dep, plan);
//            if(plan.isAchievable())
//                System.out.println("I am " + getIdentifier() + " and i found a way to achieve"+
//                    " the dependency " + dep.getIdentifier() + " with " + plan.getTasks().size() + " tasks.");
//            else
//                System.out.println("I am " + getIdentifier() + " and the best i can do to achieve"+
//                        " the dependency " + dep.getIdentifier() + " will require" + plan.getTasks().size() + " tasks.");
        }

        return approaches;
    }
}

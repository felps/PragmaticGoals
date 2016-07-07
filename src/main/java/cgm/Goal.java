package cgm;

import cgm.runtime.annotations.ParallelAnd;
import cgm.runtime.annotations.ParallelOr;
import cgm.runtime.annotations.RuntimeAnnotation;
import cgm.workflow.Plan;

import java.util.HashSet;
import java.util.Set;

public class Goal extends Refinement {

    public final static boolean OR = true;
    public final static boolean AND = false;

    public RuntimeAnnotation runtimeAnnotation;

    public Goal(boolean isOrDecomposition) {
        super();
        dependencies = new HashSet<Refinement>();
        this.isOrDecomposition = isOrDecomposition;
        if (isOrDecomposition()) {
            runtimeAnnotation = new ParallelOr();
        } else {
            runtimeAnnotation = new ParallelAnd();
        }
    }

    public Goal(boolean isOrDecomposition, RuntimeAnnotation annotation) {
        super();
        dependencies = new HashSet<Refinement>();
        this.isOrDecomposition = isOrDecomposition;
        runtimeAnnotation = annotation;
    }

    @Override
    public int myType() {
        return Refinement.GOAL;	}

	public boolean isOrDecomposition() {
		return isOrDecomposition;	}

	public boolean isAndDecomposition() {
		return !isOrDecomposition;	}

	@Override
	public Plan isAchievable(Set<Context> current, Interpretation interp) {
		if (!this.isApplicable(current)) {
            return null;
        } else {
            Plan complete, plan;
            complete = new Plan();
			for (Refinement dep : getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, interp);
				if (plan != null) {
                    if (isOrDecomposition()) {
                        if (runtimeAnnotation.getType() == RuntimeAnnotation.Interleaved) {
                            if (plan.getQuality() > complete.getQuality())
                                complete = plan;
                            // Consider perhaps both
                        }
                    } else {
                        if (getRuntimeAnnotation().getType() == RuntimeAnnotation.Sequential)
                            complete.addSerial(plan);
                    }
                } else {
                    return null;
                }
			}
			if (complete.getTasks().size() > 0)
				return complete;
			else
				return null;
		}
	}

    public RuntimeAnnotation getRuntimeAnnotation() {
        return runtimeAnnotation;
    }
}

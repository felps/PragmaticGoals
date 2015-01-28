package cgm;

import java.util.HashSet;
import java.util.Set;

public class Goal extends Refinement {

	public final static int OR_DECOMPOSITION = 0;
	public final static int SERIAL_AND_DECOMPOSITION = 1;
	public final static int PARALLEL_AND_DECOMPOSITION = 2;
	
	public final static boolean OR = true;
	public final static boolean AND = false;
	protected int decompositionType;
	
	public Goal(int decompositionType) {
		super();
		dependencies = new HashSet<Refinement>();
		this.decompositionType = decompositionType;
	}

	@Override
	public int myType() {
		return Refinement.GOAL;
	}

	public boolean isOrDecomposition() {
		if (decompositionType==OR_DECOMPOSITION)
			return true;
		else
			return false;
	}

	public boolean isAndDecomposition() {
		return !isOrDecomposition();
	}
	
	public boolean isSerialAndDecomposition(){
		return(decompositionType==SERIAL_AND_DECOMPOSITION);
	}

	public boolean isParallelAndDecomposition(){
		return(decompositionType==PARALLEL_AND_DECOMPOSITION);
	}
	
	@Override
	public Plan isAchievable(Set<Context> current, Interpretation interp) {
		if (!this.isApplicable(current)) {
			return null;
		}

		if (isOrDecomposition()) {
			Plan plan;
			for (Refinement dep : getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, interp);
				if (plan != null) {
					return plan;
				}
			}
			return null;
		} else {
			Plan complete, plan;
			complete = new Plan();
			for (Refinement dep : getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, interp);
				if (plan != null) {
					complete.add(plan);
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
}

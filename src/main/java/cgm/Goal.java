package cgm;

import java.util.HashSet;
import java.util.Set;

public class Goal extends Refinement {

	public final static boolean OR = true;
	public final static boolean AND = false;

	public Goal(boolean isOrDecomposition) {
		super();
		dependencies = new HashSet<Refinement>();
		this.isOrDecomposition = isOrDecomposition;
	}

	@Override
	public int myType() {
		return Refinement.GOAL;
	}

	public boolean isOrDecomposition() {
		return isOrDecomposition;
	}

	public boolean isAndDecomposition() {
		return !isOrDecomposition;
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
		}

		if (isAndDecomposition()) {
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
		return null;
	}

	public void parseFromYamlFile() {

	}

	// public void dumpToYamlFile(){
	// YamlHandler yaml = new YamlHandler();
	// yaml.dumpToYamlFile(this);
	//
	// }

}

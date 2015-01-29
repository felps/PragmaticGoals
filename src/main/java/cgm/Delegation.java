package cgm;

import java.util.Set;

import workflow.datatypes.Workflow;

public class Delegation extends Refinement {

	@Override
	public int myType() {
		return Refinement.DELEGATION;
	}

	@Override
	public Plan isAchievable(Set<Context> current, Interpretation interp) {
		return null;
	}

	@Override
	public Workflow workflow(Set<Context> context) {
		return null;
	}
}

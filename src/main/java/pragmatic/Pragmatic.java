package pragmatic;

import pragmatic.workflow.Plan;

import java.util.Set;

public class Pragmatic extends Goal {

	private Interpretation interp;

	public Pragmatic(boolean isOrDecomposition) {
		super(isOrDecomposition);
		interp = new Interpretation();
	}

	public Interpretation getInterpretation() {
		return interp;
	}

	@Override
	public Plan isAchievable(Set<Context> current, Interpretation interp) {
		Interpretation newInterp = new Interpretation();
		newInterp.merge(this.interp);
		newInterp.merge(interp);
		return super.isAchievable(current, newInterp);
	}
}

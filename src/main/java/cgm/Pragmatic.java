package cgm;

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
		interp.merge(this.interp);
		return super.isAchievable(current, interp);
	}
}

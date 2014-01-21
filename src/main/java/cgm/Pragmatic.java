package cgm;

public class Pragmatic extends Goal {

	private Interpretation interp;

	public Pragmatic(boolean isOrDecomposition) {
		super(isOrDecomposition);
		interp = new Interpretation();
	}

	public Interpretation getInterpretation() {
		return interp;
	}

}

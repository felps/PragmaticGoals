package cgm;

import java.util.HashSet;

public class Goal extends Refinement{

	public Goal(boolean isOrDecomposition) {
		dependencies = new HashSet<Refinement>();
		this.isOrDecomposition = isOrDecomposition;
	}
	
	@Override
	public String myType() {
		return Refinement.GOAL;
	}

	

}

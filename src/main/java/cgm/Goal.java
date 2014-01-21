package cgm;

import java.util.HashSet;

public class Goal extends Dependency{

	public Goal(boolean isOrDecomposition) {
		dependencies = new HashSet<Dependency>();
		this.isOrDecomposition = isOrDecomposition;
	}
	
	@Override
	public String myType() {
		return Dependency.GOAL;
	}

	

}

package cgm;

import java.util.HashSet;
import java.util.Set;

public class Goal extends Dependency{

	HashSet<Dependency> dependencies;
	
	public Goal() {
		dependencies = new HashSet<Dependency>();
	}
	
	public void addDependency(Dependency goal) {
		dependencies.add(goal);
	}

	public Set<Dependency> getDependencies() {
		return dependencies;
	}

	public Set<Dependency> getApplicableDependencies(Context context) {
		
		HashSet<Dependency> applicableDeps = new HashSet<Dependency>();
		
		for (Dependency dep : dependencies) {
			if(dep.getApplicableContext() == context){
				applicableDeps.add(dep);
			}
		}
		return applicableDeps;
	}

	@Override
	public String myType() {
		return Dependency.GOAL;
	}

	

}

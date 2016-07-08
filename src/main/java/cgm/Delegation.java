package cgm;

import cgm.workflow.Plan;

import java.util.Set;

public class Delegation extends Refinement {

	@Override
	public int myType() {
		return Refinement.DELEGATION;
	}

	public void parseFromYamlFile() {

	}

	@Override
    public Plan isAchievable(Set<Context> current, Interpretation interp) {
        return null;
    }

    @Override
    public void addDependency(Refinement goal) {
        dependencies.add(goal);
    }
    // public void dumpToYamlFile(){
	// YamlHandler yaml = new YamlHandler();
	// yaml.dumpToYamlFile(this);
	// }
}

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
	// public void dumpToYamlFile(){
	// YamlHandler yaml = new YamlHandler();
	// yaml.dumpToYamlFile(this);
	// }
}

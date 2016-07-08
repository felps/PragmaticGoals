package cgm;

import cgm.workflow.Plan;

import java.util.Set;

public class CGM {

	private Refinement rootGoal;

	public Refinement getRoot() {
		return rootGoal;
	}

	public void setRoot(Refinement root) {
		this.rootGoal = root;

	}

	public Plan isAchievable(Set<Context> current, Interpretation interp) {
		return rootGoal.isAchievable(current, interp, null);
	}


	// public void dumpToYamlFile(){
	// YamlHandler yaml = new YamlHandler();
	// yaml.dumpToYamlFile(this);
	//
	// }
}

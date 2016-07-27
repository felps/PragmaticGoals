package pragmatic;

import pragmatic.workflow.Plan;

import java.util.Set;

public class CGM {

	private Refinement rootGoal;

	public int size() {
		return rootGoal.size();
	}
	public Refinement getRoot() {
		return rootGoal;
	}

	public void setRoot(Refinement root) {
		this.rootGoal = root;

	}

	public Plan isAchievable(Set<Context> current, Interpretation interp) {
		return rootGoal.isAchievable(current, interp);
	}


	// public void dumpToYamlFile(){
	// YamlHandler yaml = new YamlHandler();
	// yaml.dumpToYamlFile(this);
	//
	// }
}
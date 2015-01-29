package cgm;

import java.util.Set;

import workflow.datatypes.Workflow;

public class CGM {

	private Refinement rootGoal;

	public void setRoot(Refinement root) {
		this.rootGoal = root;

	}

	public Refinement getRoot() {
		return rootGoal;
	}

	public Plan isAchievable(Set<Context> current, Interpretation interp) {
		return rootGoal.isAchievable(current, interp);
	}

	public Workflow convertToWorkflow(Set<Context> context) throws EmptyWorkflow {
		return rootGoal.workflow(context);
	}

	// public void dumpToYamlFile(){
	// YamlHandler yaml = new YamlHandler();
	// yaml.dumpToYamlFile(this);
	//
	// }
}

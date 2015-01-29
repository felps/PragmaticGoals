package gm.cgm;

import gm.GM;

import java.util.Set;

import workflow.datatypes.Workflow;

public class CGM extends GM {

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

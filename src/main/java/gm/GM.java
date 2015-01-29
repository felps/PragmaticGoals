package gm;

import gm.cgm.EmptyWorkflow;
import gm.cgm.Refinement;
import workflow.datatypes.Workflow;

public class GM {

	protected Refinement rootGoal;

	public void setRoot(Refinement root) {
		this.rootGoal = root;

	}

	public Refinement getRoot() {
		return rootGoal;
	}

	public Workflow convertToWorkflow() throws EmptyWorkflow {
		return rootGoal.workflow(null);
	}
}

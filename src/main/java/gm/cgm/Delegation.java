package gm.cgm;

import java.util.HashMap;
import java.util.Set;

import metrics.Metric;
import workflow.datatypes.Workflow;

public class Delegation extends Refinement {

	@Override
	public int myType() {
		return Refinement.DELEGATION;
	}

	@Override
	public Plan isAchievable(Set<Context> current, Interpretation interp) {
		return null;
	}

	@Override
	public Workflow workflow(Set<Context> context) {
		return null;
	}

	@Override
	public Set<Task> getTasks() {
		return null;
	}

	@Override
	public void printCGM() {
		// TODO Auto-generated method stub

	}

	@Override
	public HashMap<Metric, Float> getQoS(Set<Context> context) {
		// TODO Auto-generated method stub
		return null;
	}
}

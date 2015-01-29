package cgm;

import java.util.Set;

import workflow.datatypes.Workflow;

public class Plan {

	GM gm;
	public Plan(Task task) {
		gm = new GM();
		gm.setRoot(new Goal(Goal.OR_DECOMPOSITION));
		((Goal) gm.getRoot()).addDependency(task);
	}

	public Plan() {
		gm = new GM();
		gm.setRoot(new Goal(Goal.OR_DECOMPOSITION));
	}

	public void add(Plan plan) {
		((Goal) gm.getRoot()).addDependency(plan.getGM().getRoot());
	}

	public void add(Refinement dependency) {
		((Goal) gm.getRoot()).addDependency(dependency);
	}

	public GM getGM() {
		return gm;
	}

	public Set<Task> getTasks() {
		return gm.getRoot().getTasks();
	}

	public Workflow convertToWorkflow() throws EmptyWorkflow {
		return gm.getRoot().workflow(null);
	}

}

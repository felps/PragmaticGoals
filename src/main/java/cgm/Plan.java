package cgm;

import java.util.HashSet;

public class Plan {

	private HashSet<Task> tasks;

	public Plan(Task task) {
		tasks = new HashSet<Task>();
		tasks.add(task);
	}

	public Plan() {
		tasks = new HashSet<Task>();
	}

	public void add(Plan plan) {
		tasks.addAll(plan.getTasks());
	}

	public HashSet<Task> getTasks() {
		return tasks;
	}

}

package br.ime.usp.improv.pragmatic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import br.ime.usp.improv.pragmatic.workflow.WorkflowTask;

public class ExecutionPlan {

	private WorkflowPlan plan;
	private String rootActor;
	private HashSet<WorkflowTask> pendingTasks;

	public ExecutionPlan(WorkflowPlan originalPlan) {
		this.plan = originalPlan;
		this.pendingTasks = new HashSet<WorkflowTask>();
	}

	public HashSet<WorkflowTask> getPendingTasks() {
		return pendingTasks;
	}

	public WorkflowPlan getPlan() {
		return plan;
	}
	
	public void setPlan(WorkflowPlan plan) {
		this.plan = plan;
	}
	
	public String getRootActor() {
		return rootActor;
	}

	public void setRootActor(String actorURL) {
	 this.rootActor = actorURL;
	}

	public void setDone(WorkflowTask task) {
		plan.getInitialTasks().remove(task);
		plan.getTasks().remove(task);
		
		for (WorkflowTask workflowTask : task.getEnabledTasksSet()) {
			if(Collections.disjoint(workflowTask.getRequiredTasksSet(), plan.getTasks())) {
				plan.getInitialTasks().add(workflowTask);
			}
		}
		plan.getInitialTasks().addAll(task.getEnabledTasksSet());
	}

	public void setDone(HashSet<WorkflowTask> tasks) {
		for (WorkflowTask task : tasks) {
			this.setDone(task);
		}
	}

	public void addPendingTask(WorkflowTask task) {
		this.getPlan().getInitialTasks().remove(task);
		pendingTasks.add(task);
		System.out.println("Waiting for someone else to perform task "+task.getIdentifier());
	}
	
	public void completePendingTask(WorkflowTask task) {
		pendingTasks.remove(task);
		setDone(task);
	}
}

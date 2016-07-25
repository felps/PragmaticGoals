package cgm.workflow;

import cgm.Task;

import java.util.HashSet;

/**
 * Created by Felipe on 13/07/2016.
 */
public class TryWorkflowTask extends WorkflowTask {


    private WorkflowTask initialTask;
    private WorkflowTask endTask;
    private Boolean achieved;
    private HashSet<WorkflowTask> rescueTasks = new HashSet<>();

    public TryWorkflowTask(Task task) {
        super(task);
    }

    @Override
    public HashSet<WorkflowTask> getEnabledTasksSet() {
        return getEnabledTasksSet();
    }

    public HashSet<WorkflowTask> getRescueTasks() {
        return rescueTasks;
    }

    public Boolean getAchieved() {
        return achieved;
    }

    public void setAchieved(Boolean achieved) {
        this.achieved = achieved;
    }

    public void addRescuePlan(Plan rescuePlan) {
        for (WorkflowTask task : rescuePlan.getInitialTasks()) {
            task.requires(this);

        }
        rescueTasks.addAll(rescuePlan.getTasks());
    }
}

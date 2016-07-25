package cgm.workflow;

import cgm.Task;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Felipe on 13/07/2016.
 */
public class TryPlan extends Plan {
    private WorkflowTask endTask;
    private TryWorkflowTask initialTask;


    public TryPlan(Task task) {
        super(task);
        initialTask = new TryWorkflowTask(task);
        endTask = new WorkflowTask(task);
        endTask.requires(initialTask);
        getInitialTasks().add(initialTask);
        getFinalTasks().add(endTask);
    }

    @Override
    public void add(WorkflowTask newTask, Set<WorkflowTask> dependencies) {
        dependencies.add(initialTask);
        endTask.requires(newTask);
        super.add(newTask, dependencies);
    }

    @Override
    public Set<WorkflowTask> getInitialTasks() {
        HashSet<WorkflowTask> taskHashSet = new HashSet<>();
        taskHashSet.add(initialTask);
        return taskHashSet;
    }

    @Override
    public Set<WorkflowTask> getFinalTasks() {
        HashSet<WorkflowTask> taskHashSet = new HashSet<>();
        taskHashSet.add(endTask);
        return taskHashSet;
    }
}
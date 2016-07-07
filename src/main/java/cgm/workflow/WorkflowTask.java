package cgm.workflow;

import java.util.HashSet;

/**
 * Created by Felipe on 07/07/2016.
 */
public class WorkflowTask {
    private String id;
    private HashSet<WorkflowTask> requires;
    private HashSet<WorkflowTask> enables;


    public WorkflowTask(String id) {
        requires = new HashSet<WorkflowTask>();
        enables = new HashSet<WorkflowTask>();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getIdentifier() {
        return id;
    }

    public void requires(WorkflowTask task) {
        task.enables(this);
        requires.add(task);
    }

    private void enables(WorkflowTask task) {
        enables.add(task);
    }

    public HashSet<WorkflowTask> getEnabledTasksSet() {
        return enables;
    }

    public HashSet<WorkflowTask> getRequiredTasksSet() {
        return requires;
    }

    public void requires(HashSet<WorkflowTask> taskSet) {
        for (WorkflowTask task : taskSet) {
            this.requires(task);
        }
    }


}

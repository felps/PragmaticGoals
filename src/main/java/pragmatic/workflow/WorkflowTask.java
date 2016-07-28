package pragmatic.workflow;

import pragmatic.Task;
import pragmatic.metrics.CompositeMetric;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Felipe on 07/07/2016.
 *
 * Workflow task is a node in the workflow structure
 */
public class WorkflowTask {

    private String id;
    private int iterationCopy;
    private HashSet<WorkflowTask> requires;
    private HashSet<WorkflowTask> enables;
    private Map<CompositeMetric, Double> qualityMeasures;
    private Task originalTask;

    public WorkflowTask(Task task) {
        requires = new HashSet<>();
        enables = new HashSet<>();
        this.id = task.getIdentifier();
        originalTask = task;
    }

    public Task getOriginalTask() {
        return originalTask;
    }

    public String getId() {
        if (iterationCopy == 0) {
            return id;
        } else return id + iterationCopy;
    }

    public String getIdentifier() {
        return id;
    }

    public void setIdentifier(String identifier) {
        this.id = identifier;
    }

    public int getIterationCopy() {
        return iterationCopy;
    }

    public void setIterationCopy(int iterationCopy) {
        this.iterationCopy = iterationCopy;
    }

    void requires(WorkflowTask task) {
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

    void requires(Set<WorkflowTask> taskSet) {
        for (WorkflowTask task : taskSet) {
            this.requires(task);
        }
    }

    public Map<CompositeMetric, Double> getQualityMeasures() {
        return qualityMeasures;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof WorkflowTask){
            WorkflowTask wf = (WorkflowTask) obj;
            if(wf.getOriginalTask().equals(this.getOriginalTask()) &&
                    wf.getIterationCopy() == this.getIterationCopy() &&
                    wf.getIdentifier().equals(this.getIdentifier())){
                        return true;
            }
        }
        return false;
    }
}

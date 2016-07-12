package cgm.workflow;

import cgm.Task;
import cgm.metrics.CompositeMetric;

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
    private HashSet<WorkflowTask> requires;
    private HashSet<WorkflowTask> enables;
    private Map<CompositeMetric, Double> qualityMeasures;

    public WorkflowTask(Task task) {
        requires = new HashSet<>();
        enables = new HashSet<>();
        this.id = task.getIdentifier();
    }

    public String getId() {
        return id;
    }

    public String getIdentifier() {
        return id;
    }

    public void setIdentifier(String identifier) {
        this.id = identifier;
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
//
//    @Override
//    public boolean equals(Object obj) {
//        if(obj.getClass().equals(this.getClass())) {
//            return id.equals(((WorkflowTask) obj).id);
//        } else return false;
//    }
}

package cgm.workflow;

import cgm.Task;
import cgm.metrics.CompositeMetric;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 07/07/2016.
 */
public class WorkflowTask {
    private String id;
    private HashSet<WorkflowTask> requires;
    private HashSet<WorkflowTask> enables;
    private Map<CompositeMetric, Double> qualityMeasures;
    private Task originalTask;

    public WorkflowTask(Task task) {
        requires = new HashSet<WorkflowTask>();
        enables = new HashSet<WorkflowTask>();
        this.id = id;
        this.originalTask = task;
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

    public void requires(List<WorkflowTask> taskSet) {
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
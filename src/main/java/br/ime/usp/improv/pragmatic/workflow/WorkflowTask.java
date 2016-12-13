package br.ime.usp.improv.pragmatic.workflow;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import br.ime.usp.improv.pragmatic.Task;
import br.ime.usp.improv.pragmatic.metrics.CompositeMetric;

/**
 * Created by Felipe on 07/07/2016.
 *
 * Workflow task is a node in the workflow structure
 */
public class WorkflowTask implements Serializable, Comparable {

    private String id;
    private int iterationCopy;
    private ConcurrentSkipListSet<WorkflowTask> requires;
    private ConcurrentSkipListSet<WorkflowTask> enables;
    private HashMap<CompositeMetric, Double> qualityMeasures;
    private Task originalTask;

    public WorkflowTask(Task task) {
        requires = new ConcurrentSkipListSet<>();
        enables = new ConcurrentSkipListSet<>();
        this.id = task.getIdentifier();
        originalTask = task;
        qualityMeasures = new HashMap<>();
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

    public void requires(WorkflowTask task) {
        task.enables(this);
        requires.add(task);
    }

    private void enables(WorkflowTask task) {
        enables.add(task);
    }

    public ConcurrentSkipListSet<WorkflowTask> getEnabledTasksSet() {
        return enables;
    }

    public ConcurrentSkipListSet<WorkflowTask> getRequiredTasksSet() {
        return requires;
    }

    public void requires(Set<WorkflowTask> taskSet) {
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

	@Override
	public int compareTo(Object o) {
		if(this.getClass() == o.getClass()){
			return this.getId().compareTo(((WorkflowTask) o).getId());
		}
		throw new ClassCastException();
	}
}

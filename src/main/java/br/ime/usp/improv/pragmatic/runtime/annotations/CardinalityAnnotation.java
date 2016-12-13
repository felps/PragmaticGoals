package br.ime.usp.improv.pragmatic.runtime.annotations;

import br.ime.usp.improv.pragmatic.WorkflowPlan;
import br.ime.usp.improv.pragmatic.workflow.WorkflowTask;

/**
 * Created by Felipe on 12/07/2016.
 */
public abstract class CardinalityAnnotation extends RuntimeAnnotation {
    protected int iterations = 1;
    private boolean sequential = false;

    public int getType() {
        if (isSequential())
            return RuntimeAnnotation.SequentiallyIterated;
        else
            return RuntimeAnnotation.InterleavedIterated;
    }

    public boolean isSequential() {
        return sequential;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public WorkflowPlan clonePlan(WorkflowPlan originalPlan, int iterationCopy) {
        WorkflowPlan clonedPlan = new WorkflowPlan();

        for (WorkflowTask wfTask : originalPlan.getTasks()) {
            WorkflowTask clonedWorkflowTask = new WorkflowTask(wfTask.getOriginalTask());
            clonedWorkflowTask.setIterationCopy(iterationCopy);

            clonedPlan.getTasksMap().put(clonedWorkflowTask.getId(), clonedWorkflowTask);

            if (originalPlan.getInitialTasks().contains(wfTask)) {
                clonedPlan.getInitialTasks().add(clonedWorkflowTask);
            }
            if (originalPlan.getFinalTasks().contains(wfTask)) {
                clonedPlan.getFinalTasks().add(clonedWorkflowTask);
            }
        }

        clonedPlan.getQualityMeasures().putAll(originalPlan.getQualityMeasures());

        clonedPlan.setAchievable(originalPlan.isAchievable());
        return clonedPlan;
    }
}

package cgm.runtime.annotations;

import cgm.Refinement;
import cgm.workflow.Plan;
import cgm.workflow.WorkflowTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Felipe on 12/07/2016.
 */
public class CardinalityAnnotation extends RuntimeAnnotation {
    private boolean sequential = false;
    private int iterations = 1;

    @Override
    public int getType() {
        if (isSequential())
            return RuntimeAnnotation.SequentiallyIterated;
        else
            return RuntimeAnnotation.InterleavedIterated;
    }

    @Override
    public List<Plan> getPossiblePlans(Map<Refinement, Plan> approaches) {
        int i;
        ArrayList<Plan> list = new ArrayList<Plan>();

        if (getRefinements().size() == 0)
            return null;

        Plan fullPlan = approaches.get(getRefinements().get(0));
        for (i = 1; i < iterations; i++) {
            Plan plan = clonePlan(fullPlan);
            if (isSequential())
                fullPlan.addSerial(plan);
            else
                fullPlan.addParallel(plan);
        }
        list.add(fullPlan);
        return list;
    }

    public boolean isSequential() {
        return sequential;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    private Plan clonePlan(Plan originalPlan) {
        int i = 0;
        Plan clonedPlan = new Plan();
        for (WorkflowTask wfTask : originalPlan.getTasks()) {
            WorkflowTask clonedWorkflowTask = new WorkflowTask(wfTask.getOriginalTask());
            clonedWorkflowTask.setIterationCopy(i++);

            clonedPlan.getTasksMap().put(clonedWorkflowTask.getId(), clonedWorkflowTask);

            if (originalPlan.getInitialTasks().contains(wfTask)) {
                clonedPlan.getInitialTasks().add(clonedWorkflowTask);
            }
            if (originalPlan.getFinalTasks().contains(wfTask)) {
                clonedPlan.getFinalTasks().add(clonedWorkflowTask);
            }
        }

        for (WorkflowTask wfTask : originalPlan.getTasks()) {

            clonedPlan.getQualityMeasures().putAll(originalPlan.getQualityMeasures());
        }

        return clonedPlan;
    }
}

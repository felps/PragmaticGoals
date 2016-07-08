package cgm.workflow;

import cgm.Task;
import cgm.metrics.CompositeMetric;

import java.util.*;

public class Plan {

    private HashMap<String, WorkflowTask> tasks;
    private List<WorkflowTask> initialTasks;
    private List<WorkflowTask> finalTasks;
    private HashMap<String, CompositeMetric> qualityMeasures;
    private boolean achievable;
    private double reliability;
    private double time;

    public Plan(Task task) {
        createMaps();
        add(task.getWorkflowTask(), null);
    }

    public Plan(WorkflowTask task) {
        createMaps();
        add(task, null);
    }

    public Plan() {
        createMaps();
    }

    private void createMaps() {
        tasks = new HashMap<String, WorkflowTask>();
        initialTasks = Collections.synchronizedList(new ArrayList<WorkflowTask>());
        finalTasks = Collections.synchronizedList(new ArrayList<WorkflowTask>());
        qualityMeasures = new HashMap<String, CompositeMetric>(2);
    }

    public boolean isAchievable() {
        return achievable;
    }

    public void setAchievable(boolean achievable) {
        this.achievable = achievable;
    }

    public double getTimeConsumed() {
        return time;
    }

    public void setTimeConsumed(double timeConsumedInSeconds) {
        time = timeConsumedInSeconds;
    }

    public double getReliability() {
        return reliability;
    }

    public void setReliability(double reliability) {
        if (reliability > 1)
            System.err.println("reliability cannot be greater than one.");
        else
            this.reliability = reliability;
    }

    @Deprecated
    public void add(Plan plan) {
        this.addParallel(plan);
    }

    public void add(WorkflowTask newTask, Set<WorkflowTask> dependencies) {
        tasks.put(newTask.getId(), newTask);
        finalTasks.add(newTask);
        if (dependencies == null) {
            initialTasks.add(newTask);
        } else if (dependencies.size() == 0) {
            initialTasks.add(newTask);
        } else {
            for (WorkflowTask task : dependencies) {
                newTask.requires(task);
                if (finalTasks.contains(task)) {
                    finalTasks.remove(task);
                    finalTasks.add(newTask);
                }
            }
        }
    }
    public Collection<WorkflowTask> getTasks() {
        return tasks.values();
    }

    public HashMap<String, WorkflowTask> getTasksMap() {
        return tasks;
    }

    public double getQualityMetricsValue(String metric) {
        return qualityMeasures.get(metric).getValue();
    }

    public CompositeMetric getQualityMetrics(String metric) {
        return qualityMeasures.get(metric);
    }

    public synchronized void addSerial(Plan plan) {
        if (plan == null)
            return;
        for (WorkflowTask task : plan.getInitialTasks()) {
            task.requires(this.getFinalTasks());
        }
        checkFinalTasks();
        finalTasks.addAll(plan.getFinalTasks());
        for (WorkflowTask task : plan.getTasks()) {
            tasks.put(task.getId(), task);
        }
    }

    private synchronized void checkFinalTasks() {
        int i;
        for (i = 0; i < getFinalTasks().size(); i++) {
            WorkflowTask task = getFinalTasks().get(i);
            if (!task.getEnabledTasksSet().isEmpty()) {
                removeFinalTask(task);
            }
        }
    }

    private boolean removeFinalTask(WorkflowTask task) {
        return finalTasks.remove(task);
    }

    public void addParallel(Plan plan) {
        initialTasks.addAll(plan.getInitialTasks());
        tasks.putAll(plan.getTasksMap());
        finalTasks.addAll(plan.getFinalTasks());
    }

    public List<WorkflowTask> getInitialTasks() {
        return initialTasks;
    }

    public List<WorkflowTask> getFinalTasks() {
        return finalTasks;
    }
}

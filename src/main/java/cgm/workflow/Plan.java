package cgm.workflow;

import cgm.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Plan {

    private HashMap<String, WorkflowTask> tasks;
    private HashSet<WorkflowTask> initialTasks;
    private HashSet<WorkflowTask> finalTasks;

    public Plan(Task task) {
        WorkflowTask worfkflowTask = task.getWorkflowTask();
        createMaps();
        add(worfkflowTask, null);
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
        initialTasks = new HashSet<WorkflowTask>();
        finalTasks = new HashSet<WorkflowTask>();
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

    public double getQuality() {
        // TODO implement get quality
        return 0;
    }

    public synchronized void addSerial(Plan plan) {
        for (WorkflowTask task : plan.getInitialTasks()) {
            task.requires(this.getFinalTasks());
        }
        checkFinalTasks();
        finalTasks.addAll(plan.getFinalTasks());
        for (WorkflowTask task : plan.getTasks()) {
            tasks.put(task.getId(), task);
        }
    }

    private void checkFinalTasks() {
        for (WorkflowTask task : getFinalTasks()) {
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
        for (WorkflowTask task : plan.getTasks()) {
            tasks.put(task.getId(), task);
        }
        finalTasks.addAll(plan.getFinalTasks());
    }

    public HashSet<WorkflowTask> getInitialTasks() {
        return initialTasks;
    }

    public HashSet<WorkflowTask> getFinalTasks() {
        return finalTasks;
    }
}

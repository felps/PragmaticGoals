package cgm.workflow;

import cgm.Task;
import cgm.metrics.CompositeMetric;
import cgm.metrics.Metric;
import cgm.metrics.types.ReliabilityMetric;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Plan {

    Logger logger = LogManager.getLogger();

    // TODO modificar funcao addSerial e Addparallel para atualizar as qualityMeasures do plano

    private HashMap<String, WorkflowTask> tasks;
    private List<WorkflowTask> initialTasks;
    private List<WorkflowTask> finalTasks;
    private HashMap<String, CompositeMetric> qualityMeasures;
    private boolean achievable;

    public Plan(Task task) {
        createMaps();
        add(task.getWorkflowTask(), null);
        try {
            setReliability(task.getReliability());
        } catch (Exception e) {
            logger.error("cgm.Task Class: Out of Bounds reliability value!");
            e.printStackTrace();
        }
        setTimeConsumed(task.getTimeConsumed());

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
        try {
            return getMapValue(Metric.TIME);
        } catch (NonExistingMetricException e) {
            logger.warn("Time was not previously set. Inferring 0.");
            return 0;
        }
    }

    public void setTimeConsumed(double timeConsumedInSeconds) {
        setMapValue(timeConsumedInSeconds, Metric.TIME);
    }

    public double getReliability() {
        try {
            return getMapValue(Metric.RELIABILITY);
        } catch (NonExistingMetricException e) {
            logger.warn("Reliability was not previously set. Inferring 100%");
            return 1;
        }
    }

    public void setReliability(double reliability) {
        setMapValue(reliability, Metric.RELIABILITY);
    }

    private void setMapValue(double reliability, String key) {
        if (qualityMeasures.containsKey(key))
            qualityMeasures.get(key).setValue(reliability);
        else {
            ReliabilityMetric reliabilityMetric = new ReliabilityMetric();
            reliabilityMetric.setValue(reliability);
            qualityMeasures.put(key, reliabilityMetric);
        }
    }

    private double getMapValue(String key) throws NonExistingMetricException {
        if (qualityMeasures.containsKey(key))
            return qualityMeasures.get(key).getValue();
        else {
            throw new NonExistingMetricException();
        }
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
        boolean emptyInitial = this.getTasks().isEmpty();
        if (plan == null)
            return;

        for (WorkflowTask task : plan.getInitialTasks()) {
            task.requires(this.getFinalTasks());
            getInitialTasks().remove(task);
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

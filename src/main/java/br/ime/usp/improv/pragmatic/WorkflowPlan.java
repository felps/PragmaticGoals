package br.ime.usp.improv.pragmatic;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.ime.usp.improv.pragmatic.metrics.CompositeMetric;
import br.ime.usp.improv.pragmatic.metrics.Metric;
import br.ime.usp.improv.pragmatic.metrics.types.ReliabilityMetric;
import br.ime.usp.improv.pragmatic.metrics.types.TimeMetric;
import br.ime.usp.improv.pragmatic.workflow.NonExistingMetricException;
import br.ime.usp.improv.pragmatic.workflow.WorkflowTask;

public class WorkflowPlan implements Serializable {

    private transient Logger logger;
    private int iterationCopy;
    private HashMap<String, String> actorEndpoints;
    private HashMap<String, WorkflowTask> tasks;
    private ConcurrentSkipListSet<WorkflowTask> initialTasks;
    private ConcurrentSkipListSet<WorkflowTask> finalTasks;
    private HashMap<String, CompositeMetric> qualityMeasures;
    private boolean achievable;
    
    public WorkflowPlan(Task task) {
        try {
            createMaps();
            add(task.getWorkflowTask(), null);
            setReliability(task.getReliability());
            setTimeConsumed(task.getTimeConsumed());
        } catch (Exception e) {
            log().error("pragmatic.Task Class: Out of Bounds reliability value!");
            e.printStackTrace();
        }
    }

    public WorkflowPlan() {
        createMaps();
    }

    public int getIterationCopy() {
        return iterationCopy;
    }

    public void setIterationCopy(int iterationCopy) {
        this.iterationCopy = iterationCopy;
    }

    public HashMap<String, CompositeMetric> getQualityMeasures() {
        return qualityMeasures;
    }

    private void createMaps() {
        tasks = new HashMap<String, WorkflowTask>();
        initialTasks = new ConcurrentSkipListSet<>();
        actorEndpoints = new HashMap<String, String>();
        finalTasks = new ConcurrentSkipListSet<>();
        qualityMeasures = new HashMap<String, CompositeMetric>(2);
        setAchievable(true);
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
            log().warn("Time was not previously set. Inferring 0.");
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
            log().warn("Reliability was not previously set. Inferring 100%");
            setMapValue(1, Metric.RELIABILITY);
            return 1;
        }
    }

    public void setReliability(double reliability) {
        setMapValue(reliability, Metric.RELIABILITY);
    }

    private void setMapValue(double value, String key) {
        if (qualityMeasures.containsKey(key))
            qualityMeasures.get(key).setValue(value);
        else if (key == Metric.RELIABILITY) {
            ReliabilityMetric reliabilityMetric = new ReliabilityMetric();
            reliabilityMetric.setValue(value);
            qualityMeasures.put(key, reliabilityMetric);
        } else if (key == Metric.TIME) {
            TimeMetric timeMetric = new TimeMetric();
            timeMetric.setValue(value);
            qualityMeasures.put(key, timeMetric);
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
    public void add(WorkflowPlan plan) {
        this.addParallel(plan);
    }

    public void add(WorkflowTask newTask, Set<WorkflowTask> dependencies) {
        tasks.put(newTask.getId() + "it" + iterationCopy, newTask);
        finalTasks.add(newTask);
        if (dependencies == null) {
            if (!initialTasks.contains(newTask))
            initialTasks.add(newTask);
        } else if (dependencies.size() == 0) {
            if (!initialTasks.contains(newTask))
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

    public synchronized void addSerial(WorkflowPlan plan) {
    	this.actorEndpoints.putAll(plan.getActors());
        boolean emptyInitial = this.getTasks().isEmpty();
        if (plan == null)
            return;

        CompositeMetric reliabilityMetric = getQualityMetric();
        double planReliability = plan.getReliability();
        double myReliability = this.getReliability();
        double reliability = reliabilityMetric.getSequentialQuality(myReliability, planReliability);
        CompositeMetric timeMetric = getTimeMetric();
        double planTimeConsumed = plan.getTimeConsumed();
        double myTimeConsumed = this.getTimeConsumed();
        double time = timeMetric.getSequentialQuality(myTimeConsumed, planTimeConsumed);

        this.setReliability(reliability);
        this.setTimeConsumed(time);

        if (this.getInitialTasks().size() == 0) {
            getInitialTasks().addAll(plan.getInitialTasks());
        } else {
            for (WorkflowTask task : plan.getInitialTasks()) {
                task.requires(this.getFinalTasks());
                getInitialTasks().remove(task);
            }
            checkFinalTasks();
        }

        finalTasks.addAll(plan.getFinalTasks());
        for (WorkflowTask task : plan.getTasks()) {
            tasks.put(task.getId(), task);
        }
    }

    public Map<String, String> getActors() {
		return actorEndpoints;
	}

	private CompositeMetric getTimeMetric() {
        if (qualityMeasures.get(Metric.TIME) == null) {
            TimeMetric timeMetric = new TimeMetric();
            timeMetric.setValue(getTimeConsumed());
            qualityMeasures.put(Metric.TIME, timeMetric);
        }

        return qualityMeasures.get(Metric.TIME);
    }

    private CompositeMetric getQualityMetric() {
        if (qualityMeasures.get(Metric.RELIABILITY) == null) {
            ReliabilityMetric reliabilityMetric = new ReliabilityMetric();
            reliabilityMetric.setValue(getReliability());
            qualityMeasures.put(Metric.RELIABILITY, reliabilityMetric);
        }
        return qualityMeasures.get(Metric.RELIABILITY);

    }

    private synchronized void checkFinalTasks() {
        int i;
        Set<WorkflowTask> tasksToBeRemoved = new HashSet<>();
        for (WorkflowTask task : getFinalTasks()) {
//            if (!task.getEnabledTasksSet().isEmpty()) {
                tasksToBeRemoved.add(task);
//            }
        }
        removeFinalTask(tasksToBeRemoved);
    }

    private boolean removeFinalTask(WorkflowTask task) {
        return finalTasks.remove(task);
    }

    private boolean removeFinalTask(Set<WorkflowTask> task) {
        return finalTasks.removeAll(task);
    }

    public void addParallel(WorkflowPlan plan) {
    	this.actorEndpoints.putAll(plan.getActors());
        
        CompositeMetric reliabilityMetric = getQualityMetric();
        double reliability1 = plan.getReliability();
        double reliability2 = this.getReliability();
        double reliability = reliabilityMetric.getParallelQuality(reliability1, reliability2);
        CompositeMetric timeMetric = getTimeMetric();
        double time = timeMetric.getParallelQuality(plan.getTimeConsumed(), this.getTimeConsumed());

        this.setReliability(reliability);
        this.setTimeConsumed(time);

        initialTasks.addAll(plan.getInitialTasks());
        tasks.putAll(plan.getTasksMap());
        finalTasks.addAll(plan.getFinalTasks());
    }

    public Set<WorkflowTask> getInitialTasks() {
        return initialTasks;
    }

    public Set<WorkflowTask> getFinalTasks() {
        return finalTasks;
    }

    public Logger log() {
    	if (logger ==null) logger = LogManager.getLogger();
    	return logger;
    }
}

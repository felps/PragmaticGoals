package br.ime.usp.improv.pragmatic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.ime.usp.improv.pragmatic.metrics.Metric;
import br.ime.usp.improv.pragmatic.metrics.exceptions.MetricNotFoundException;
import br.ime.usp.improv.pragmatic.quality.FilterQualityConstraint;
import br.ime.usp.improv.pragmatic.workflow.WorkflowTask;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Task extends Refinement implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3085695630993169882L;
	private transient Logger logger = LogManager.getLogger();
    private HashMap<String, HashMap<Context, Float>> providedQualityLevels;
	private boolean lessIsMore;
    private WorkflowTask workflowTask;
    private String executedBy;

    public Task(boolean lessIsMore) {
        providedQualityLevels = new HashMap<String, HashMap<Context, Float>>();
        this.lessIsMore = lessIsMore;
        workflowTask = new WorkflowTask(this);
        try {
            setReliability(1.0);
            setTimeConsumed(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Task() {
		providedQualityLevels = new HashMap<String, HashMap<Context, Float>>();
		this.lessIsMore = false;
        workflowTask = new WorkflowTask(this);
        try {
            setReliability(1.0);
            setTimeConsumed(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Task(String id) {
        providedQualityLevels = new HashMap<String, HashMap<Context, Float>>();
        this.lessIsMore = false;
        workflowTask = new WorkflowTask(this);
        this.setIdentifier(id);
    }

	public void setProvidedQuality(Context context, String metric, double value) {
		HashMap<Context, Float> map;

		if (providedQualityLevels.containsKey(metric)) {
            if (metric == Metric.TIME) {
                setTimeConsumed(value);
            } else if (metric == Metric.RELIABILITY) {
                try {
                    setReliability(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            map = providedQualityLevels.get(metric);
			map.put(context, new Float(value));
		} else {
			map = new HashMap<Context, Float>();
			map.put(context, new Float(value));
			providedQualityLevels.put(metric, map);
		}
	}

	public float myProvidedQuality(String metric, Set<Context> current) throws MetricNotFoundException {
		float myQuality = 0;
		boolean set = false;

        if (providedQualityLevels.get(metric) != null && providedQualityLevels.get(metric).containsKey(null)) {
            myQuality = providedQualityLevels.get(metric).get(null);
            set = true;
        }
        for (Context context : current) {
            if (providedQualityLevels.get(metric) != null && providedQualityLevels.get(metric).get(context) != null) {
                if (!set) {
					myQuality = providedQualityLevels.get(metric).get(context).floatValue();
					set = true;
				} else {
					if (lessIsMore) {
						if (myQuality > providedQualityLevels.get(metric).get(context).floatValue()) {
							myQuality = providedQualityLevels.get(metric).get(context).floatValue();
						}
					} else if (myQuality < providedQualityLevels.get(metric).get(context).floatValue()) {
						myQuality = providedQualityLevels.get(metric).get(context).floatValue();
					}
				}
			}

		}
		if (!set)
			throw (new MetricNotFoundException());
		return myQuality;
	}

	public boolean abidesByInterpretation(Interpretation interp, Set<Context> current) {
		boolean feasible = true;

		if (interp==null){
			return true;
		}
        for (FilterQualityConstraint qc : interp.getQualityConstraints(current)) {
            try {
                if (!qc.abidesByQC(myProvidedQuality(qc.getMetric(), current), qc.getMetric())) {
					feasible = false;
				}
			} catch (MetricNotFoundException e) {
			}
		}
		if (interp.getQualityConstraints(null) != null)
            for (FilterQualityConstraint qc : interp.getQualityConstraints(null)) {
                try {
                    if (!qc.abidesByQC(myProvidedQuality(qc.getMetric(), current), qc.getMetric())) {
						feasible = false;
					}
				} catch (MetricNotFoundException e) {
				}
			}
		return feasible;
	}

    @Override
    public WorkflowPlan isAchievable(Set<Context> current, Interpretation interp) {
        WorkflowPlan plan = new WorkflowPlan(this);
        
        if (!this.isApplicable(current)) {
            logger.debug("I am " + getIdentifier() + " but i am not achievable!");
            return null;
        }
        if (abidesByInterpretation(interp, current)) {
            plan.setAchievable(true);
            logger.debug("I am " + getIdentifier() + " and i am achievable!");
            return plan;
        } else {
            plan.setAchievable(false);
            logger.debug("I am " + getIdentifier() + " but i am not achievable!");
            return plan;
        }
    }

    public WorkflowTask getWorkflowTask() {
        return new WorkflowTask(this);
    }

    @Override
    public void setIdentifier(String identifier) {
        super.setIdentifier(identifier);
        workflowTask.setIdentifier(identifier);
    }

    @Override
    public int size() {
        return 1;
    }
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Task){
    		return getIdentifier().contentEquals(((Task)obj).getIdentifier());
    	}
    	return super.equals(obj);
    }

	public String isExecutedBy() {
		return executedBy;
	}

	public void setExecutedBy(String executedBy) {
		this.executedBy = executedBy;
	}
}
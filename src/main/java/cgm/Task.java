package cgm;

import java.util.HashMap;
import java.util.Set;

import metrics.Metric;

public class Task extends Refinement {

	private HashMap<Metric, HashMap<Context, Float>> providedQualityLevels;

	@Override
	public int myType() {
		return Refinement.TASK;
	}

	public Task() {
		providedQualityLevels = new HashMap<Metric, HashMap<Context, Float>>();
	}

	public void setProvidedQuality(Context context, Metric metric, double value) {
		HashMap<Context, Float> map;

		if (providedQualityLevels.containsKey(metric)) {
			map = providedQualityLevels.get(metric);
			map.put(context, new Float(value));
		} else {
			map = new HashMap<Context, Float>();
			map.put(context, new Float(value));
			providedQualityLevels.put(metric, map);
		}
	}

	public float myProvidedQuality(Metric metric, Set<Context> current) throws MetricNotFoundException {
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
				} else if (myQuality < providedQualityLevels.get(metric).get(context).floatValue()) {
					myQuality = providedQualityLevels.get(metric).get(context).floatValue();
				}
			}
		}

		if (!set)
			throw (new MetricNotFoundException());
		return myQuality;
	}

	public boolean abidesByInterpretation(Interpretation interp, Set<Context> current) {
		boolean feasible = true;

		if (interp == null) {
			return true;
		}
		for (QualityConstraint qc : interp.getQualityConstraints(current)) {
			try {
				if (!qc.abidesByQC(myProvidedQuality(qc.getMetric(), current), qc.getMetric())) {
					feasible = false;
				}
			} catch (MetricNotFoundException e) {
			}
		}
		if (interp.getQualityConstraints(null) != null)
			for (QualityConstraint qc : interp.getQualityConstraints(null)) {
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
	public Plan isAchievable(Set<Context> current, Interpretation interp) {
		if (!this.isApplicable(current)) {
			return null;
		}
		if (abidesByInterpretation(interp, current)) {
			return new Plan(this);
		} else {
			return null;
		}
	}
}

package gm.cgm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import metrics.Metric;
import workflow.datatypes.Workflow;

public class Goal extends Refinement {

	public final static int OR_DECOMPOSITION = 0;
	public final static int SERIAL_AND_DECOMPOSITION = 1;
	public final static int PARALLEL_AND_DECOMPOSITION = 2;

	public final static boolean OR = true;
	public final static boolean AND = false;
	protected int decompositionType;
	protected boolean isOrDecomposition = false;
	protected HashMap<Integer, Refinement> dependencies;
	private HashMap<Metric, Float> myQoS;

	public Goal(int decompositionType) {
		super();
		dependencies = new HashMap<Integer, Refinement>();
		this.decompositionType = decompositionType;
		myQoS = new HashMap<Metric, Float>();
	}

	@Override
	public int myType() {
		return Refinement.GOAL;
	}

	public boolean isOrDecomposition() {
		if (decompositionType == OR_DECOMPOSITION)
			return true;
		else
			return false;
	}

	public boolean isAndDecomposition() {
		return !isOrDecomposition();
	}

	public boolean isSerialAndDecomposition() {
		return (decompositionType == SERIAL_AND_DECOMPOSITION);
	}

	public boolean isParallelAndDecomposition() {
		return (decompositionType == PARALLEL_AND_DECOMPOSITION);
	}

	public List<Refinement> getApplicableDependencies(Set<Context> current) {

		if (current == null) {
			current = setUpNullContext();
		}

		List<Refinement> applicableDeps = new ArrayList<Refinement>();

		for (int i = 0; i < dependencies.size(); i++) {
			Refinement dep = dependencies.get(i);
			HashSet<Context> applicable = dep.getApplicableContext();
			for (Context context : current) {
				if (applicable.contains(context) || applicable.contains(null)) {
					applicableDeps.add(dep);
				}
			}
		}
		return applicableDeps;
	}

	@Override
	public Plan isAchievable(Set<Context> current, Interpretation interp) {
		if (!this.isApplicable(current)) {
			return null;
		}

		Plan complete, plan;
		complete = new Plan();

		if (isOrDecomposition()) {
			for (Refinement dep : getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, interp);
				if (plan != null) {
					return plan;
				}
			}
			return null;
		} else {
			for (Refinement dep : getApplicableDependencies(current)) {

				plan = dep.isAchievable(current, interp);

				if (plan == null) {
					return null;
				}

				HashMap<Metric, Float> qosSet = plan.getQoS();
				for (Metric metric : qosSet.keySet()) {
					accumulateQoS(metric, qosSet.get(metric));
				}

				if (interp != null) {
					for (QualityConstraint qc : interp.getQualityConstraints(current)) {
						Metric currentMetric = qc.getMetric();
						float threshold = plan.getQoS().get(currentMetric);
						if (!qc.abidesByQC(threshold, currentMetric)) {
							return null;
						}
					}
				}
				complete.add(plan);
			}
			if (complete.getTasks().size() > 0)
				return complete;
			else
				return null;
		}
	}

	private HashMap<Metric, Float> accumulateQoS(Metric metric, float value) {

		if (!myQoS.containsKey(metric)) {
			myQoS.put(metric, value);
		} else {
			Float oldQoS = myQoS.get(metric);
			Float newQoS;

			if (isSerialAndDecomposition()) {
				newQoS = metric.serialComposition(oldQoS, value);
				myQoS.put(metric, newQoS);
			} else if (isParallelAndDecomposition()) {
				newQoS = metric.parallelComposition(oldQoS, value);
				myQoS.put(metric, newQoS);
			}

		}
		return myQoS;
	}

	@Override
	public Workflow workflow(Set<Context> context) throws EmptyWorkflow {
		if (context == null) {
			context = setUpNullContext();
		}

		Workflow wf = new Workflow();

		if (isParallelAndDecomposition()) {
			for (Refinement dep : getApplicableDependencies(context)) {
				wf = wf.parallel(dep.workflow(context));
			}
			return wf;

		} else if (isSerialAndDecomposition()) {
			for (Refinement dep : getApplicableDependencies(context)) {
				wf = wf.concat(dep.workflow(context));
			}
			return wf;

		} else if (isOrDecomposition()) {
			for (Refinement dep : getApplicableDependencies(context)) {
				wf = wf.parallel(dep.workflow(context));
				return wf;
			}
		}

		throw new EmptyWorkflow();

	}

	public void addDependency(Refinement refinement) {
		int lastIndex;
		if (!dependencies.values().contains(refinement)) {
			lastIndex = dependencies.size();
			dependencies.put(lastIndex, refinement);
		}
	}

	public List<Refinement> getDependencies() {
		ArrayList<Refinement> returnList = new ArrayList<Refinement>();
		for (int i = 0; i < dependencies.size(); i++) {
			returnList.add(dependencies.get(i));
		}
		return returnList;
	}

	@Override
	public Set<Task> getTasks() {
		HashSet<Task> tasks = new HashSet<Task>();
		for (Refinement dep : dependencies.values()) {
			tasks.addAll(dep.getTasks());
		}
		return tasks;
	}

	public void printCGM() {
		System.out.println("Goal " + getIdentifier() + " { ");
		for (Refinement dep : getDependencies()) {
			dep.printCGM();
		}
		System.out.println(" }  // Goal " + getIdentifier());
	}

	@Override
	public HashMap<Metric, Float> getQoS(Set<Context> context) {
		boolean set = false;
		HashMap<Metric, Float> qos = new HashMap<>();

		for (Refinement dep : getApplicableDependencies(context)) {
			HashMap<Metric, Float> contextualQoS = dep.getQoS(context);
			for (Metric metric : contextualQoS.keySet()) {
				if (!qos.containsKey(metric)) {
					qos.put(metric, contextualQoS.get(metric));
				} else {
					float oldQoS = qos.get(metric);
					float newQoS = contextualQoS.get(metric);
					float composedQoS;
					if (isParallelAndDecomposition()) {
						composedQoS = metric.parallelComposition(oldQoS, newQoS);
					} else if (isSerialAndDecomposition()) {
						composedQoS = metric.serialComposition(oldQoS, newQoS);
					} else { // isOrDecomposition())
						composedQoS = Math.min(oldQoS, newQoS);
					}
					qos.put(metric, composedQoS);
				}
			}
		}
		return qos;
	}
}

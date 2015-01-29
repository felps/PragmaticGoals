package gm.cgm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public Goal(int decompositionType) {
		super();
		dependencies = new HashMap<Integer, Refinement>();
		this.decompositionType = decompositionType;
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

		if (isOrDecomposition()) {
			Plan plan;
			for (Refinement dep : getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, interp);
				if (plan != null) {
					return plan;
				}
			}
			return null;
		} else {
			Plan complete, plan;
			complete = new Plan();
			for (Refinement dep : getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, interp);
				if (plan != null) {
					complete.add(plan);
				} else {
					return null;
				}
			}
			if (complete.getTasks().size() > 0)
				return complete;
			else
				return null;
		}
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
}

package cgm;

import cgm.quality.QualityConstraint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Interpretation {

	private HashMap<Context, Set<QualityConstraint>> contextDependentInterpretation;
	private HashSet<QualityConstraint> qualityConstraints;

	public Interpretation() {
		contextDependentInterpretation = new HashMap<Context, Set<QualityConstraint>>();
		qualityConstraints = new HashSet<QualityConstraint>();
	}

	public HashMap<Context, Set<QualityConstraint>> getContextDependentInterpretation() {
		return contextDependentInterpretation;
	}

	public void addFilterQualityConstraint(QualityConstraint constraint) {
		qualityConstraints.add(constraint);

		Context context = constraint.getApplicableContext();

		if (contextDependentInterpretation.containsKey(context)) {
			contextDependentInterpretation.get(context).add(constraint);
		} else {
			HashSet<QualityConstraint> constraintSet = new HashSet<QualityConstraint>();
			constraintSet.add(constraint);
			contextDependentInterpretation.put(context, constraintSet);
		}
	}

	public Set<QualityConstraint> getQualityConstraints(Set<Context> current) {
		HashSet<QualityConstraint> allQCs = new HashSet<QualityConstraint>();
		if (current != null)
			for (Context context : current) {
				if (contextDependentInterpretation.containsKey(context)) {
					allQCs.addAll(contextDependentInterpretation.get(context));
				}
			}
		else if (contextDependentInterpretation.containsKey(null)) {
			allQCs.addAll(contextDependentInterpretation.get(null));
		}
		return allQCs;
	}

	public void merge(Interpretation interp) {
		if (interp == null)
			return;
		for (QualityConstraint qualityConstraint : interp.getAllQualityConstraints()) {
			addFilterQualityConstraint(qualityConstraint);
		}
	}

	private HashSet<QualityConstraint> getAllQualityConstraints() {
		return qualityConstraints;
	}

}

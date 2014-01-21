package cgm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Interpretation {

	HashMap<Context, Set<QualityConstraint>> contextDependentInterpretation;

	public Interpretation() {
		contextDependentInterpretation = new HashMap<Context, Set<QualityConstraint>>();
	}

	public void addQualityConstraint(QualityConstraint constraint,
			Context context) {
		if (contextDependentInterpretation.containsKey(context)) {
			contextDependentInterpretation.get(context).add(constraint);
		} else {

			HashSet<QualityConstraint> constraintSet = new HashSet<QualityConstraint>();

			constraintSet.add(constraint);

			contextDependentInterpretation.put(context, constraintSet);
		}
	}

	public Set<QualityConstraint> getQualityConstraints(Context context) {

		if (contextDependentInterpretation.containsKey(context)) {
			return contextDependentInterpretation.get(context);
		} else {
			return null;
		}
		
	}

}

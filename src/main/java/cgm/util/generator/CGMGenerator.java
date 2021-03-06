package cgm.util.generator;

import java.util.HashSet;
import java.util.Set;

import cgm.CGM;
import cgm.Comparison;
import cgm.Context;
import cgm.Goal;
import cgm.Metric;
import cgm.Pragmatic;
import cgm.QualityConstraint;
import cgm.Refinement;
import cgm.Task;

public abstract class CGMGenerator {

	public CGM generateCGM(int refinementsAmount, int contextAmount) {
		int i, j;

		HashSet<Context> possibleContexts = new HashSet<Context>();

		for (i = 1; i <= contextAmount; i++) {
			possibleContexts.add(new Context("c" + i));
		}

		CGM cgm = new CGM();

		Refinement cgmRoot = generateDeps(refinementsAmount, possibleContexts);
		cgm.setRoot(cgmRoot);

		return cgm;
	}

	public Refinement generateDeps(int refinementsAmount, Set<Context> possibleContexts) {
		int depAmount = getRefinementsAmount(refinementsAmount);
		if (refinementsAmount == 0)
			return null;

		if (refinementsAmount == 1) {
			return generateTask(possibleContexts);
		}

		Goal root = generateGoal(possibleContexts);

		int fraction = (int) Math.floor((refinementsAmount - 1) / depAmount);

		for (int i = 0; i < depAmount; i++) {
			int temp = fraction;
			if (i == 0)
				temp = fraction + ((refinementsAmount - 1) - fraction * depAmount);
			if (temp != 0) {
				Refinement dependency;
				dependency = generateDeps(temp, possibleContexts);
				root.addDependency(dependency);
			}

		}

		return root;
	}

	protected abstract int getRefinementsAmount(int maxRefinements) ;

	protected abstract Task generateTask(Set<Context> possibleContexts);
	protected abstract Goal generateGoal(Set<Context> possibleContexts);
}

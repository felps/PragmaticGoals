package cgm.util;

import java.util.HashSet;
import java.util.Set;

import cgm.CGM;
import cgm.Context;
import cgm.Goal;
import cgm.Refinement;
import cgm.Task;

public class RandomCGMGenerator {

	public CGM generateRandomCGM(int refinementsAmount, int contextAmount) {
		int i, j;

		HashSet<Context> possibleContexts = new HashSet<Context>();

		for (i = 1; i <= contextAmount; i++) {
			possibleContexts.add(new Context("c" + i));
		}

		CGM cgm = new CGM();

		Refinement cgmRoot = generateRandomDeps(refinementsAmount, possibleContexts);
		cgm.setRoot(cgmRoot);

		return cgm;
	}

	public Refinement generateRandomDeps(int refinementsAmount, Set<Context> possibleContexts) {
		int depAmount = (int) Math.floor(Math.random() * (refinementsAmount - 1)) + 1;
		if (refinementsAmount == 0)
			return null;

		if (refinementsAmount == 1) {
			return new Task();
		}

		Goal root = generateRandomGoal(possibleContexts);

		int fraction = (int) Math.floor((refinementsAmount - 1) / depAmount);

		for (int i = 0; i < depAmount; i++) {
			int temp = fraction;
			if (i == 0)
				temp = fraction + ((refinementsAmount - 1) - fraction * depAmount);
			if (temp != 0) {
				Refinement dependency;
				dependency = generateRandomDeps(temp, possibleContexts);
				root.addDependency(dependency);
			}

		}

		return root;
	}
	private Goal generateRandomGoal(Set<Context> possibleContexts) {
		double isOrDecomposition = Math.random();
		Goal goal;
		if (isOrDecomposition >= 0.5)
			goal = new Goal(true);
		else
			goal = new Goal(false);

		int randomIndex = (int) (Math.random() * possibleContexts.size());
		int run = 0;
		for (Context context : possibleContexts) {
			if (run == randomIndex) {
				goal.addApplicableContext(context);
				break;
			}
			run++;
		}

		return goal;
	}

}

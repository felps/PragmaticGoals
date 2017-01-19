package br.ime.usp.improv.pragmatic.util.generator.pragmatic;

import java.util.HashSet;
import java.util.Set;

import br.ime.usp.improv.pragmatic.*;

public abstract class CGMGenerator {

	public CGM generateCGM(int refinementsAmount, int contextAmount) {
		int i, j;

		HashSet<Context> possibleContexts = new HashSet<>();

		for (i = 1; i <= contextAmount; i++) {
			possibleContexts.add(new Context("c" + i));
		}

		CGM cgm = new CGM();

		Refinement cgmRoot = generateDeps(refinementsAmount, possibleContexts);
		cgm.setRoot(cgmRoot);

		return cgm;
	}

	protected Refinement generateDeps(int refinementsAmount, Set<Context> possibleContexts) {
		int depAmount = getRandomRefinementsUpTo(refinementsAmount);
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

	protected abstract int getRandomRefinementsUpTo(int maxRefinements);

	protected abstract Task generateTask(Set<Context> possibleContexts);
	protected abstract Goal generateGoal(Set<Context> possibleContexts);
}
package cgm.util;

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
			return generateRandomTask();
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

	private Task generateRandomTask() {
		int metric;
		double random = Math.random();
		if (random >= 0.1) {
			metric = 10;
		} else if (random >= 0.2) {
			metric = 20;
		} else if (random >= 0.3) {
			metric = 30;
		} else if (random >= 0.4) {
			metric = 40;
		} else if (random >= 0.5) {
			metric = 50;
		} else if (random >= 0.6) {
			metric = 60;
		} else if (random >= 0.7) {
			metric = 70;
		} else{
			metric = 100;
		}
		
		Task task = new Task();
		task.setProvidedQuality(null, Metric.SECONDS, metric);
		
		return task;
	}
	private Goal generateRandomGoal(Set<Context> possibleContexts) {
		double isOrDecomposition = Math.random();
		Goal goal;
		if (isOrDecomposition >= 0.5)
			goal = new Pragmatic(true);
		else
			goal = new Pragmatic(false);

		int randomIndex = (int) (Math.random() * possibleContexts.size());
		int run = 0;
		for (Context context : possibleContexts) {
			if (run == randomIndex) {
				goal.addApplicableContext(context);
				break;
			}
			run++;
		}

		int metric;
		double random = Math.random();
		if (random >= 0.1) {
			metric = 10;
		} else if (random >= 0.2) {
			metric = 20;
		} else if (random >= 0.3) {
			metric = 30;
		} else if (random >= 0.4) {
			metric = 40;
		} else if (random >= 0.5) {
			metric = 50;
		} else if (random >= 0.6) {
			metric = 60;
		} else if (random >= 0.7) {
			metric = 70;
		} else{
			metric = 100;
		}
		
		Pragmatic representation = (Pragmatic) goal;
		QualityConstraint qc = new QualityConstraint(null, Metric.SECONDS, metric, Comparison.LESS_THAN);
		representation.getInterpretation().addQualityConstraint(qc);
		
		return goal;
	}

}
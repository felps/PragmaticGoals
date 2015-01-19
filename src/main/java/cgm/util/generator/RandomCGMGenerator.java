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

public class RandomCGMGenerator extends CGMGenerator{

	public CGM generateRandomCGM(int refinementsAmount, int contextAmount) {
		return generateCGM(refinementsAmount, contextAmount);
	}

	protected Task generateTask() {
		int metric;
		double random = Math.random();
		if (random >= 0.1) {
			metric = 99;
		} else if (random >= 0.2) {
			metric = 80;
		} else if (random >= 0.3) {
			metric = 70;
		} else if (random >= 0.4) {
			metric = 60;
		} else if (random >= 0.5) {
			metric = 50;
		} else if (random >= 0.6) {
			metric = 40;
		} else if (random >= 0.7) {
			metric = 30;
		} else{
			metric = 9;
		}
		
		Task task = new Task();
		task.setProvidedQuality(null, Metric.SECONDS, metric);
		
		return task;
	}
	
	protected Goal generateGoal(Set<Context> possibleContexts) {
		double isOrDecomposition = Math.random();
		Pragmatic goal;
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
			metric = 99;
		} else if (random >= 0.2) {
			metric = 89;
		} else if (random >= 0.3) {
			metric = 79;
		} else if (random >= 0.4) {
			metric = 69;
		} else if (random >= 0.5) {
			metric = 59;
		} else if (random >= 0.6) {
			metric = 49;
		} else if (random >= 0.7) {
			metric = 39;
		} else{
			metric = 9;
		}
		QualityConstraint qc = new QualityConstraint(null, Metric.SECONDS, metric, Comparison.GREATER_OR_EQUAL_TO);
		goal.getInterpretation().addQualityConstraint(qc);
		
		return goal;
	}

	protected int getRefinementsAmount(int maxRefinements) {
		return (int) Math.floor(Math.random() * (maxRefinements - 1)) + 1;
	}

}

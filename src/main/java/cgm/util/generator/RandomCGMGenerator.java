package cgm.util.generator;

import java.util.HashSet;
import java.util.Set;

import metrics.ExecutionTimeSec;
import metrics.Metric;
import cgm.CGM;
import cgm.Comparison;
import cgm.Context;
import cgm.Goal;
import cgm.Pragmatic;
import cgm.QualityConstraint;
import cgm.Refinement;
import cgm.Task;

public class RandomCGMGenerator extends CGMGenerator{

	public CGM generateRandomCGM(int refinementsAmount, int contextAmount) {
		return generateCGM(refinementsAmount, contextAmount);
	}

	protected Task generateTask(Set<Context> possibleContexts) {
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
		
		int randomIndex = (int) (Math.random() * possibleContexts.size());
		int run = 0;
		
		for (Context context : possibleContexts) {
				task.addApplicableContext(context);
		}
		
		task.setProvidedQuality(null, (new ExecutionTimeSec()), metric);
		
		return task;
	}
	
	protected Goal generateGoal(Set<Context> possibleContexts) {
		double isOrDecomposition = Math.random();
		Pragmatic goal;
		if (isOrDecomposition >= 0.8)
			goal = new Pragmatic(Goal.OR_DECOMPOSITION);
		else
			goal = new Pragmatic(Goal.SERIAL_AND_DECOMPOSITION);

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
			metric = 129;
		} else if (random >= 0.2) {
			metric = 179;
		} else if (random >= 0.3) {
			metric = 159;
		} else if (random >= 0.4) {
			metric = 169;
		} else if (random >= 0.5) {
			metric = 179;
		} else if (random >= 0.6) {
			metric = 189;
		} else if (random >= 0.7) {
			metric = 199;
		} else{
			metric = 9;
		}
		
		QualityConstraint qc = new QualityConstraint(null, (new ExecutionTimeSec()), metric, Comparison.LESS_THAN);
		goal.getInterpretation().addQualityConstraint(qc);
		
		return goal;
	}

	protected int getRefinementsAmount(int maxRefinements) {
		return (int) Math.floor(Math.random() * (maxRefinements - 1)) + 1;
	}

}

package br.ime.usp.improv.pragmatic.util.generator.pragmatic;

import java.util.Set;

import br.ime.usp.improv.pragmatic.*;
import br.ime.usp.improv.pragmatic.metrics.Metric;
import br.ime.usp.improv.pragmatic.quality.FilterQualityConstraint;

public class RandomCGMGenerator extends CGMGenerator{

	int taskNumber = 0;
	int goalNumber = 0;

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

		Task task = new Task("Task" + (taskNumber++));
		
		int randomIndex = (int) (Math.random() * possibleContexts.size());
		int run = 0;
		
		for (Context context : possibleContexts) {
				task.addApplicableContext(context);
		}

		task.setProvidedQuality(null, Metric.SECONDS, metric);
		
		return task;
	}
	
	protected Goal generateGoal(Set<Context> possibleContexts) {
		double isOrDecomposition = Math.random();
		Pragmatic goal;
		if (isOrDecomposition >= 0.8) {
			goal = new Pragmatic(Goal.OR);
			goal.setIdentifier("GoalOR" + (goalNumber++));
		} else {
			goal = new Pragmatic(Goal.AND);
			goal.setIdentifier("GoalAnd" + (goalNumber++));
		}

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

		FilterQualityConstraint qc = new FilterQualityConstraint(null, Metric.SECONDS, metric, Comparison.LESS_THAN);
		goal.getInterpretation().addFilterQualityConstraint(qc);
		
		return goal;
	}

	protected int getRandomRefinementsUpTo(int maxRefinements) {
		return (int) Math.floor(Math.random() * (maxRefinements - 1)) + 1;
	}

}
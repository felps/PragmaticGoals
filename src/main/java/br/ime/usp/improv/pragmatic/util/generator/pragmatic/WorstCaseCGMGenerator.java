package br.ime.usp.improv.pragmatic.util.generator.pragmatic;

import java.util.Set;

import br.ime.usp.improv.pragmatic.*;
import br.ime.usp.improv.pragmatic.metrics.Metric;
import br.ime.usp.improv.pragmatic.quality.FilterQualityConstraint;

public class WorstCaseCGMGenerator extends CGMGenerator {

	@Override
	protected Task generateTask(Set<Context> possibleContexts) {
		Task task = new Task();
		task.setProvidedQuality(null, Metric.SECONDS, 10);
		for (Context context : possibleContexts) {
			task.addApplicableContext(context);
		}
		return task;
	}

	@Override
	protected Goal generateGoal(Set<Context> possibleContexts) {

		FilterQualityConstraint qc;
		
		Pragmatic pragmaticGoal= new Pragmatic(Goal.AND);
		for (Context context : possibleContexts) {
			pragmaticGoal.addApplicableContext(context);
			qc = new FilterQualityConstraint(context, Metric.SECONDS, 100, Comparison.LESS_THAN);
			pragmaticGoal.getInterpretation().addFilterQualityConstraint(qc);
		}

		qc = new FilterQualityConstraint(null, Metric.SECONDS, 100, Comparison.LESS_THAN);
		pragmaticGoal.getInterpretation().addFilterQualityConstraint(qc);
		
		
		return pragmaticGoal;
	}

	@Override
	protected int getRandomRefinementsUpTo(int maxRefinements) {
		return 2;
	}

}


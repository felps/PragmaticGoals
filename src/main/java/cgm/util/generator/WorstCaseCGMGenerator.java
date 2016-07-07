package cgm.util.generator;

import cgm.*;
import cgm.metrics.FilterMetric;
import cgm.quality.QualityConstraint;

import java.util.Set;

public class WorstCaseCGMGenerator extends CGMGenerator {

	@Override
	protected Task generateTask(Set<Context> possibleContexts) {
		Task task = new Task();
		task.setProvidedQuality(null, FilterMetric.SECONDS, 10);
		for (Context context : possibleContexts) {
			task.addApplicableContext(context);
		}
		return task;
	}

	@Override
	protected Goal generateGoal(Set<Context> possibleContexts) {

		QualityConstraint qc;
		
		Pragmatic pragmaticGoal= new Pragmatic(Goal.AND);
		for (Context context : possibleContexts) {
			pragmaticGoal.addApplicableContext(context);
			qc = new QualityConstraint(context, FilterMetric.SECONDS, 100, Comparison.LESS_THAN);
			pragmaticGoal.getInterpretation().addFilterQualityConstraint(qc);
		}

		qc = new QualityConstraint(null, FilterMetric.SECONDS, 100, Comparison.LESS_THAN);
		pragmaticGoal.getInterpretation().addFilterQualityConstraint(qc);
		
		
		return pragmaticGoal;
	}

	@Override
	protected int getRefinementsAmount(int maxRefinements) {
		return 2;
	}

}


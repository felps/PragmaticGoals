package cgm.util.generator;

import java.util.Set;

import cgm.Comparison;
import cgm.Context;
import cgm.Goal;
import cgm.Metric;
import cgm.Pragmatic;
import cgm.QualityConstraint;
import cgm.Task;

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

		QualityConstraint qc;
		
		Pragmatic pragmaticGoal= new Pragmatic(Goal.AND);
		for (Context context : possibleContexts) {
			pragmaticGoal.addApplicableContext(context);
			qc = new QualityConstraint(context, Metric.SECONDS, 100, Comparison.LESS_THAN);
			pragmaticGoal.getInterpretation().addQualityConstraint(qc);
		}
		
		qc = new QualityConstraint(null, Metric.SECONDS, 100, Comparison.LESS_THAN);
		pragmaticGoal.getInterpretation().addQualityConstraint(qc);
		
		
		return pragmaticGoal;
	}

	@Override
	protected int getRefinementsAmount(int maxRefinements) {
		return 2;
	}

}


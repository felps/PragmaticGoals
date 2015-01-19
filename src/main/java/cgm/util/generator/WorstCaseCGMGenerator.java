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
	protected Task generateTask() {
		Task task = new Task();
		task.setProvidedQuality(null, Metric.SECONDS, 10);
		
		return task;
	}

	@Override
	protected Goal generateGoal(Set<Context> possibleContexts) {

		Pragmatic pragmaticGoal= new Pragmatic(Goal.AND);
		
		QualityConstraint qc = new QualityConstraint(null, Metric.SECONDS, 100, Comparison.LESS_THAN);
		pragmaticGoal.getInterpretation().addQualityConstraint(qc);
		
		return pragmaticGoal;
	}

	@Override
	protected int getRefinementsAmount(int maxRefinements) {
		return 2;
	}

}


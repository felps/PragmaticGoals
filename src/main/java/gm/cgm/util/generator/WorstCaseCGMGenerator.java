package gm.cgm.util.generator;

import gm.cgm.Comparison;
import gm.cgm.Context;
import gm.cgm.Goal;
import gm.cgm.Pragmatic;
import gm.cgm.QualityConstraint;
import gm.cgm.Task;

import java.util.Set;

import metrics.ExecutionTimeSec;

public class WorstCaseCGMGenerator extends CGMGenerator {

	@Override
	protected Task generateTask(Set<Context> possibleContexts) {
		Task task = new Task();
		task.setProvidedQuality(null, (new ExecutionTimeSec()), 10);
		for (Context context : possibleContexts) {
			task.addApplicableContext(context);
		}
		return task;
	}

	@Override
	protected Goal generateGoal(Set<Context> possibleContexts) {

		QualityConstraint qc;
		
		Pragmatic pragmaticGoal= new Pragmatic(Goal.SERIAL_AND_DECOMPOSITION);
		for (Context context : possibleContexts) {
			pragmaticGoal.addApplicableContext(context);
			qc = new QualityConstraint(context, (new ExecutionTimeSec()), 100, Comparison.LESS_THAN);
			pragmaticGoal.getInterpretation().addQualityConstraint(qc);
		}
		
		qc = new QualityConstraint(null, (new ExecutionTimeSec()), 100, Comparison.LESS_THAN);
		pragmaticGoal.getInterpretation().addQualityConstraint(qc);
		
		
		return pragmaticGoal;
	}

	@Override
	protected int getRefinementsAmount(int maxRefinements) {
		return 2;
	}

}


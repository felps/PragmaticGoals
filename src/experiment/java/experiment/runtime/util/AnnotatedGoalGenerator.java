package experiment.runtime.util;

import pragmatic.*;
import pragmatic.metrics.Metric;
import pragmatic.quality.FilterQualityConstraint;
import pragmatic.runtime.annotations.RuntimeAnnotation;
import pragmatic.util.generator.pragmatic.CGMGenerator;

import java.util.Set;

/**
 * Created by felps on 27/07/16.
 */
public class AnnotatedGoalGenerator extends CGMGenerator {

    private static int id = 1;
    public int dependenciesPerNode = 2;

    private boolean decomposition;
    private RuntimeAnnotation runtimeAnnotation;

    public int timeConsumedInSeconds = 10;
    public double reliability = 0.999;

    public AnnotatedGoalGenerator(RuntimeAnnotation annotation, boolean goalDecomposition){
        runtimeAnnotation = annotation;
        decomposition = goalDecomposition;
    }

    @Override
    protected Task generateTask(Set<Context> possibleContexts) {
        Task task = new Task("T"+(id++));
        task.setTimeConsumed(timeConsumedInSeconds);
        try {
            task.setReliability(reliability);
        } catch (Exception e) {
        }

        task.addApplicableContext(possibleContexts);

        return task;
    }

    @Override
    protected Goal generateGoal(Set<Context> possibleContexts) {

        FilterQualityConstraint qc;

        Pragmatic pragmaticGoal = new Pragmatic(decomposition);

        try {
            RuntimeAnnotation clone = ((RuntimeAnnotation) runtimeAnnotation.clone());
            pragmaticGoal.setRuntimeAnnotation(clone);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

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
        return dependenciesPerNode;
    }

}



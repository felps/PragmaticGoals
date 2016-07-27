package experiment.runtime.util;

import pragmatic.*;
import pragmatic.metrics.Metric;
import pragmatic.quality.FilterQualityConstraint;
import pragmatic.runtime.annotations.*;
import pragmatic.util.generator.pragmatic.CGMGenerator;

import java.util.Set;

/**
 * Created by felps on 27/07/16.
 */
public class IteratedGoalGenerator extends CGMGenerator {

    private static int id = 1;
    public int dependenciesPerNode = 2;

    private boolean decomposition;
    private RuntimeAnnotation runtimeAnnotation;

    public int timeConsumedInSeconds = 10;
    public double reliability = 0.999;

    public IteratedGoalGenerator(RuntimeAnnotation annotation, boolean goalDecomposition) {
        if(annotation instanceof CardinalityAnnotation) {
            if(goalDecomposition)
                runtimeAnnotation = new SequentialAnnotation();
            else
                runtimeAnnotation = new AlternativeAnnotation();
        }
        decomposition = goalDecomposition;
    }

    @Override
    protected Task generateTask(Set<Context> possibleContexts) {
        Task task = new Task("T" + (id++));
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

        pragmaticGoal.setRuntimeAnnotation(runtimeAnnotation);

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
    protected Refinement generateDeps(int refinementsAmount, Set<Context> possibleContexts) {
        int depAmount = getRandomRefinementsUpTo(refinementsAmount);
        if (refinementsAmount == 0)
            return null;

        if (refinementsAmount == 1) {
            Goal iteratedGoal = new Goal(Goal.AND);
            SequentialCardinalAnnotation iteration = new SequentialCardinalAnnotation();
            iteration.setIterations(1);
            iteratedGoal.setRuntimeAnnotation(iteration);
            iteratedGoal.addDependency(generateTask(possibleContexts));
        }

        Goal root = generateGoal(possibleContexts);

        int fraction = (int) Math.floor((refinementsAmount - 1) / depAmount);

        for (int i = 0; i < depAmount; i++) {
            int temp = fraction;
            if (i == 0)
                temp = fraction + ((refinementsAmount - 1) - fraction * depAmount);
            if (temp != 0) {
                Refinement dependency;
                dependency = generateDeps(temp, possibleContexts);
                root.addDependency(dependency);
            }

        }

        return root;
    }


    @Override
    protected int getRandomRefinementsUpTo(int maxRefinements) {
        return 2;
    }

}


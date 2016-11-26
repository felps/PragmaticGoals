package experiment.runtime.util;

import pragmatic.*;
import pragmatic.metrics.Metric;
import pragmatic.quality.FilterQualityConstraint;
import pragmatic.runtime.annotations.*;

import java.util.Set;

/**
 * Created by felps on 28/07/16.
 */
public class RandomAnnotatedGoalGenerator extends pragmatic.util.generator.pragmatic.CGMGenerator {

    private static int id = 1;

    public int timeConsumedInSeconds = 10;
    public double reliability = 0.999;

    @Override
    protected int getRandomRefinementsUpTo(int maxRefinements) {
        return (int) Math.ceil(Math.random()*maxRefinements);
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
        return generateGoal(possibleContexts, 3);
    }

    @Override
    protected Refinement generateDeps(int refinementsAmount, Set<Context> possibleContexts) {
        int depAmount = getRandomRefinementsUpTo(refinementsAmount);

        if (refinementsAmount == 0)
            return null;

        if (refinementsAmount == 1) {
            return generateTask(possibleContexts);
        }

        Goal root = generateGoal(possibleContexts, depAmount);

        int fraction = (int) Math.floor((refinementsAmount - 1) / depAmount);

        for (int i = 0; i < depAmount; i++) {
            int temp = fraction;
            if (temp != 0) {
                Refinement dependency;
                dependency = generateDeps(temp, possibleContexts);
                root.addDependency(dependency);
            }
        }

        return root;

    }

    private Goal generateGoal(Set<Context> possibleContexts, int depAmount) {

        boolean decomposition = getDecomposition();
        Pragmatic pragmaticGoal = new Pragmatic(decomposition);

        RuntimeAnnotation runtimeAnnotation = getRandomAnnotation(decomposition, depAmount);
        pragmaticGoal.setRuntimeAnnotation(runtimeAnnotation);

        FilterQualityConstraint qc;

        for (Context context : possibleContexts) {
            pragmaticGoal.addApplicableContext(context);
            qc = new FilterQualityConstraint(context, Metric.SECONDS, 100, Comparison.LESS_THAN);
            pragmaticGoal.getInterpretation().addFilterQualityConstraint(qc);
        }

        qc = new FilterQualityConstraint(null, Metric.SECONDS, 100, Comparison.LESS_THAN);
        pragmaticGoal.getInterpretation().addFilterQualityConstraint(qc);

        return pragmaticGoal;

    }

    private RuntimeAnnotation getRandomAnnotation(boolean decomposition, int depAmount) {
        double value = Math.random();

        if (depAmount == 1) {
            if (value < 0.5) {
                // Iterated Serial
                SequentialCardinalAnnotation cardinalAnnotation = new SequentialCardinalAnnotation();
                cardinalAnnotation.setIterations(3);
                return cardinalAnnotation;
            } else {
                // Iterated parallel
                InterleavedCardinalAnnotation cardinalAnnotation = new InterleavedCardinalAnnotation();
                cardinalAnnotation.setIterations(3);
                return cardinalAnnotation;
            }
        }

        if (decomposition == Goal.AND) {
            if (value > 0.5) {
                // AND Seq
                return new SequentialAnnotation();
            } else {
                // AND Int
                return new InterleavedAnnotation();
            }
        } else {
            // Alt OR
            return new AlternativeAnnotation();
        }
    }

    private boolean getDecomposition() {
        return (Math.random() > 0.5);
    }
}

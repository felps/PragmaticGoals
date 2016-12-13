package experiment.runtime.util;

import java.util.Set;

import br.ime.usp.improv.pragmatic.*;
import br.ime.usp.improv.pragmatic.metrics.Metric;
import br.ime.usp.improv.pragmatic.quality.FilterQualityConstraint;
import br.ime.usp.improv.pragmatic.runtime.annotations.*;
import br.ime.usp.improv.pragmatic.util.generator.pragmatic.CGMGenerator;

/**
 * Created by felps on 27/07/16.
 */
public class IteratedGoalGenerator extends CGMGenerator {

    private static int id = 1;
    public int dependenciesPerNode = 2;

    private boolean decomposition;
    private RuntimeAnnotation runtimeAnnotation;
    private CardinalityAnnotation iterationRuntimeAnnotation;

    public int timeConsumedInSeconds = 10;
    public double reliability = 0.999;
    private int iterations = 1;

    public IteratedGoalGenerator(RuntimeAnnotation annotation, boolean goalDecomposition) {
        if(annotation instanceof CardinalityAnnotation) {
            CardinalityAnnotation cardinalityAnnotation = (CardinalityAnnotation) annotation;
            iterationRuntimeAnnotation = cardinalityAnnotation;

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
    protected Refinement generateDeps(int refinementsAmount, Set<Context> possibleContexts) {
        int depAmount = getRandomRefinementsUpTo(refinementsAmount);
        if (refinementsAmount == 0)
            return null;

        if (refinementsAmount <= 2 ) {
            Goal iteratedGoal = new Goal(Goal.OR);
            iterationRuntimeAnnotation.setIterations(iterations);
            RuntimeAnnotation iteration = iterationRuntimeAnnotation;
            iteratedGoal.setRuntimeAnnotation(iteration);
            iteratedGoal.addDependency(generateTask(possibleContexts));
            return iteratedGoal;
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
        return dependenciesPerNode;
    }

}



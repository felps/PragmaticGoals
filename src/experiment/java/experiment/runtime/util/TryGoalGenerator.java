package experiment.runtime.util;

import java.util.Set;

import br.ime.usp.improv.pragmatic.*;
import br.ime.usp.improv.pragmatic.metrics.Metric;
import br.ime.usp.improv.pragmatic.quality.FilterQualityConstraint;
import br.ime.usp.improv.pragmatic.runtime.annotations.RuntimeAnnotation;
import br.ime.usp.improv.pragmatic.runtime.annotations.TryAnnotation;
import br.ime.usp.improv.pragmatic.util.generator.pragmatic.CGMGenerator;

/**
 * Created by Felipe on 29/07/2016.
 */
public class TryGoalGenerator extends CGMGenerator {
    private static int id = 1;
    public int dependenciesPerNode = 3;
    public int timeConsumedInSeconds = 10;
    public double reliability = 0.999;
    private boolean decomposition = Goal.OR;
    private RuntimeAnnotation runtimeAnnotation;

    @Override
    protected int getRandomRefinementsUpTo(int maxRefinements) {
        return dependenciesPerNode;
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
        pragmaticGoal.setIdentifier("G" + (id++));

        RuntimeAnnotation tryAnnotation = new TryAnnotation();
        pragmaticGoal.setRuntimeAnnotation(tryAnnotation);

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
            return new Skip();

        if (refinementsAmount == 1) {
            return generateTask(possibleContexts);
        }

        Goal root = generateGoal(possibleContexts);

        int fraction = (int) Math.floor((refinementsAmount - 1) / depAmount);

        for (int i = 0; i < depAmount; i++) {
            int temp = fraction;
            if (i == 0) {
                temp = fraction + ((refinementsAmount - 1) - fraction * depAmount);
            }
            Refinement dependency;
            dependency = generateDeps(temp, possibleContexts);
            root.addDependency(dependency);
        }

        return root;
    }

}
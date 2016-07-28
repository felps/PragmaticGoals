package experiment.runtime.context.sweep;

import experiment.runtime.util.ScientificalEvaluation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import pragmatic.Context;
import pragmatic.util.generator.pragmatic.CGMGenerator;
import experiment.runtime.util.RandomAnnotatedGoalGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by felps on 28/07/16.
 */
public class RuntimeContextSweep {

    Logger logger = LogManager.getLogger();

    private int minModelSize = 500;
    private int maxModelSize = 10000;
    private int modelStep = 500;
    private int repetitionAmount = 100;
    private int modelAmount = 10;
    private int contexts = 10;


    private int contextSet = 0;

    @Test
    public void scalabilityTestContextSweep() {

        logger.trace("Scalability Evaluation - Context sweep capability with 20 context set");

        CGMGenerator modelGenerator;

        modelGenerator = new RandomAnnotatedGoalGenerator();
        performAnalysis(modelGenerator, "ContextSweep");

    }


    private void performAnalysis(CGMGenerator modelGenerator, String ID) {


        ScientificalEvaluation evaluation = new ScientificalEvaluation(modelGenerator);

        evaluation.experimentId = ID;
        evaluation.minModelSize = minModelSize;
        evaluation.maxModelSize = maxModelSize;
        evaluation.modelStep = modelStep;
        evaluation.repetitions = repetitionAmount;
        evaluation.generatedModelsAmount = modelAmount;
        evaluation.contextAmount = contexts;

        logger.trace( "Beginning " + ID + " evaluation");

        evaluation.executeScientificalEvaluation(logger);

        logger.trace( "End " + ID + " evaluation");
    }


    private Set<Context> generateNextContextSet(int contextAmount) {
        long limit;
        HashSet<Context> contexts = new HashSet<Context>();
        int currentSet = contextSet;
        limit = (long) Math.pow(2, contextAmount);
        for (int i = 0; i < contextAmount; i++) {
            if (currentSet % 2 != 0) {
                contexts.add(new Context("c" + (contextAmount - i)));
                //System.out.println("c" + (contextAmount - i));
            }
            currentSet /= 2;
        }

        //contexts.add(null);
        contextSet++;
        return contexts;
    }
}

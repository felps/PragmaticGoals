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

    private int minModelSize = 100;
    private int maxModelSize = 10000;
    private int modelStep = 100;
    private int repetitionAmount = 1;
    private int modelAmount = 10;
    private int contexts = 10;

    @Test
    public void scalabilityTestContextSweep() {

        System.out.println("Scalability Evaluation - Context sweep capability with 20 context set");

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

        System.out.println("Beginning " + ID + " evaluation");

        logger.trace( "Beginning " + ID + " evaluation");

        evaluation.executeSweep(logger);

        logger.trace( "End " + ID + " evaluation");
    }

}

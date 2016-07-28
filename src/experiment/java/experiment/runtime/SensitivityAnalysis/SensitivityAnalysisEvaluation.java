package experiment.runtime.SensitivityAnalysis;

import experiment.runtime.util.AnnotatedGoalGenerator;
import experiment.runtime.util.IteratedGoalGenerator;
import experiment.runtime.util.ScientificalEvaluation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import pragmatic.Goal;
import pragmatic.runtime.annotations.*;
import pragmatic.util.generator.pragmatic.CGMGenerator;

/**
 * Created by felps on 27/07/16.
 */
public class SensitivityAnalysisEvaluation {

    Logger logger = LogManager.getLogger();

    private int minModelSize = 500;
    private int maxModelSize = 10000;
    private int modelStep = 500;

    private final int repetitionAmount = 50;
    private int maxContexts = 5;


    @Test
    public void sensitivityAnalysisSerialAnd() throws Exception {

        RuntimeAnnotation runtimeAnnotation = new SequentialAnnotation();
        AnnotatedGoalGenerator modelGenerator = new AnnotatedGoalGenerator(runtimeAnnotation, Goal.AND);
        modelGenerator.dependenciesPerNode = 2;
        String ID = "SerialAND";

        performAnalysis(modelGenerator, ID);
    }


    @Test
    public void sensitivityAnalysisParallelAnd() throws Exception {

        RuntimeAnnotation runtimeAnnotation = new InterleavedAnnotation();
        AnnotatedGoalGenerator modelGenerator = new AnnotatedGoalGenerator(runtimeAnnotation, Goal.AND);
        modelGenerator.dependenciesPerNode = 2;
        String ID = "InterleavedAND";

        performAnalysis(modelGenerator, ID);
    }


    @Test
    public void sensitivityAnalysisAlternativeOR() throws Exception {

        RuntimeAnnotation runtimeAnnotation = new AlternativeAnnotation();
        AnnotatedGoalGenerator modelGenerator = new AnnotatedGoalGenerator(runtimeAnnotation, Goal.OR);
        modelGenerator.dependenciesPerNode = 2;
        String ID = "AlternativeOR";

        performAnalysis(modelGenerator, ID);
    }

    //@Test
    public void sensitivityAnalysisTry() throws Exception {

        RuntimeAnnotation runtimeAnnotation = new TryAnnotation();
        AnnotatedGoalGenerator modelGenerator = new AnnotatedGoalGenerator(runtimeAnnotation, Goal.OR);
        modelGenerator.dependenciesPerNode = 3;

        String ID = "Try";

        performAnalysis(modelGenerator, ID);
    }

    @Test
    public void sensitivityAnalysisSerialIteration() throws Exception {

        CardinalityAnnotation runtimeAnnotation = new SequentialCardinalAnnotation();
        runtimeAnnotation.setIterations(3);

        IteratedGoalGenerator modelGenerator = new IteratedGoalGenerator(runtimeAnnotation, Goal.AND);
        modelGenerator.dependenciesPerNode = 2;
        
        String ID = "SerialIteration";

        performAnalysis(modelGenerator, ID);
    }


    @Test
    public void sensitivityAnalysisInterleavedIteration() throws Exception {

        CardinalityAnnotation runtimeAnnotation = new InterleavedCardinalAnnotation();
        runtimeAnnotation.setIterations(3);
        
        IteratedGoalGenerator modelGenerator = new IteratedGoalGenerator(runtimeAnnotation, Goal.AND);
        modelGenerator.dependenciesPerNode = 2;

        String ID = "InterleavedIteration";

        performAnalysis(modelGenerator, ID);
    }


    private void performAnalysis(CGMGenerator modelGenerator, String ID) {


        ScientificalEvaluation evaluation = new ScientificalEvaluation(modelGenerator);

        evaluation.experimentId = ID;
        evaluation.minModelSize = minModelSize;
        evaluation.maxModelSize = maxModelSize;
        evaluation.modelStep = modelStep;
        evaluation.repetitions = repetitionAmount;

        logger.trace( "Beginning " + ID + " evaluation");

        for(evaluation.contextAmount=1; evaluation.contextAmount<= maxContexts; evaluation.contextAmount++)
            evaluation.executeScientificalEvaluation(logger);

        logger.trace( "End " + ID + " evaluation");
    }
}

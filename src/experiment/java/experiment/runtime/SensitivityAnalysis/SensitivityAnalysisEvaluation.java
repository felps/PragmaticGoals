package experiment.runtime.SensitivityAnalysis;

import experiment.runtime.util.AnnotatedGoalGenerator;
import experiment.runtime.util.ScientificalEvaluation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import pragmatic.Goal;
import pragmatic.runtime.annotations.*;

/**
 * Created by felps on 27/07/16.
 */
public class SensitivityAnalysisEvaluation {

    Logger logger = LogManager.getLogger();

    private int minModelSize = 500;
    private int maxModelSize = 10000;
    private int modelStep = 500;

    private final int repetitionAmount = 10;


    @Test
    public void sensitivityAnalysisSerialAnd() throws Exception {

        RuntimeAnnotation seqRuntimeAnnotation = new SequentialAnnotation();
        AnnotatedGoalGenerator modelGenerator = new AnnotatedGoalGenerator(seqRuntimeAnnotation, Goal.AND);
        String ID = "SerialAND";

        performAnalysis(modelGenerator, ID, 2);
    }


    @Test
    public void sensitivityAnalysisParallelAnd() throws Exception {

        RuntimeAnnotation runtimeAnnotation = new InterleavedAnnotation();
        AnnotatedGoalGenerator modelGenerator = new AnnotatedGoalGenerator(runtimeAnnotation, Goal.AND);

        String ID = "InterleavedAND";

        performAnalysis(modelGenerator, ID, 2);
    }


    @Test
    public void sensitivityAnalysisAlternativeOR() throws Exception {

        RuntimeAnnotation runtimeAnnotation = new AlternativeAnnotation();
        AnnotatedGoalGenerator modelGenerator = new AnnotatedGoalGenerator(runtimeAnnotation, Goal.OR);

        String ID = "AlternativeOR";

        performAnalysis(modelGenerator, ID, 2);
    }

    @Test
    public void sensitivityAnalysisTry() throws Exception {

        RuntimeAnnotation runtimeAnnotation = new TryAnnotation();
        AnnotatedGoalGenerator modelGenerator = new AnnotatedGoalGenerator(runtimeAnnotation, Goal.OR);

        String ID = "Try";

        performAnalysis(modelGenerator, ID, 3);
    }

    @Test
    public void sensitivityAnalysisSerialIteration() throws Exception {

        RuntimeAnnotation seqRuntimeAnnotation = new SequentialAnnotation();
        AnnotatedGoalGenerator modelGenerator = new AnnotatedGoalGenerator(seqRuntimeAnnotation, Goal.AND);
        String ID = "SerialAND";

        performAnalysis(modelGenerator, ID, 2);
    }


    private void performAnalysis(AnnotatedGoalGenerator modelGenerator, String ID, int dependenciesPerNode) {
        ScientificalEvaluation evaluation = new ScientificalEvaluation(modelGenerator);


        evaluation.experimentId = ID;
        evaluation.minModelSize = minModelSize;
        evaluation.maxModelSize = maxModelSize;
        evaluation.modelStep = modelStep;
        evaluation.repetitions = repetitionAmount;

        logger.trace( "Beginning " + ID + " evaluation");

        for(evaluation.contextAmount=1;evaluation.contextAmount<=20;evaluation.contextAmount++)
            evaluation.executeScientificalEvaluation(logger);

        logger.trace( "End " + ID + " evaluation");
    }
}

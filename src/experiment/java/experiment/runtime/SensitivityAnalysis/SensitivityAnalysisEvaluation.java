package experiment.runtime.SensitivityAnalysis;

import experiment.runtime.util.AnnotatedGoalGenerator;
import experiment.runtime.util.ScientificalEvaluation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import pragmatic.Goal;
import pragmatic.runtime.annotations.RuntimeAnnotation;
import pragmatic.runtime.annotations.SequentialAnnotation;

/**
 * Created by felps on 27/07/16.
 */
public class SensitivityAnalysisEvaluation {

    Logger logger = LogManager.getLogger();
    private final String experimentsLogFolder = "./logs/experiments/sensitivityAnalysis/";

    @Test
    public void sensitivityAnalysisSerialAnd() throws Exception {
        RuntimeAnnotation seqRuntimeAnnotation = new SequentialAnnotation();
        AnnotatedGoalGenerator modelGenerator = new AnnotatedGoalGenerator(seqRuntimeAnnotation, Goal.AND);

        ScientificalEvaluation evaluation = new ScientificalEvaluation(modelGenerator);

        String ID = "SerialAND";
        evaluation.experimentId = ID;
        evaluation.minModelSize = 500;
        evaluation.maxModelSize = 10000;
        evaluation.modelStep = 500;
        evaluation.repetitions = 100;

        logger.trace( "Beginning serial and evaluation");
        logger.trace( "Beginning serial and evaluation");
        logger.trace("Beginning serial and evaluation");
        logger.trace( "Beginning serial and evaluation");
        logger.trace( "Beginning serial and evaluation");
        logger.trace( "Beginning serial and evaluation");

        for(evaluation.contextAmount=1;evaluation.contextAmount<=20;evaluation.contextAmount++)
            evaluation.executeScientificalEvaluation(logger);

        logger.trace( "End serial and evaluation");
        logger.trace( "End serial and evaluation");
        logger.trace( "End serial and evaluation");
        logger.trace( "End serial and evaluation");
        logger.trace( "End serial and evaluation");

    }
}

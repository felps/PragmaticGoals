package experiment.runtime.util;

import org.apache.logging.log4j.Logger;
import pragmatic.CGM;
import pragmatic.Context;
import pragmatic.util.generator.pragmatic.CGMGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by felps on 27/07/16.
 */
public class ScientificalEvaluation {

    public CGMGenerator generator;

    public String experimentId = "";
    public int generatedModelsAmount = 1;
    public int minModelSize = 1;
    public int modelStep = 1;
    public int maxModelSize = 1;

    public int contextAmount = 10;
    public int repetitions;

    public ScientificalEvaluation(CGMGenerator generator) {
        this.generator = generator;
    }


    public void executeScientificalEvaluation(Logger logger) {
        {
            int i;
            int modelSize;
            boolean achievable = false;

            Set<Context> current = generateContextSet(contextAmount);

            for (modelSize = minModelSize; modelSize <= maxModelSize; modelSize += modelStep) {
                long accumulated = 0;
                for (i = 0; i < generatedModelsAmount; i++) {

                    // Setup Model
                    CGM cgm = generator.generateCGM(modelSize, contextAmount);


                    long start = System.nanoTime();
                    for (int j = 0; j < repetitions; j++) {
                        // Execute test
                        cgm.isAchievable(current, null);
                    }
                    accumulated += (System.nanoTime() - start);

                    if (cgm.isAchievable(current, null) != null) {
                        achievable = true;
                    }

                    if (accumulated < 0)
                        throw new ArithmeticException("TimeMetric evaluation Overflow");
                }

                // Print result
                long timePerExecutionInNs = accumulated / (repetitions* generatedModelsAmount); // TimeMetric in nanosseconds for each execution
                long durationInMs = TimeUnit.MILLISECONDS.convert(timePerExecutionInNs, TimeUnit.NANOSECONDS);

                logger.trace(experimentId + ": " + modelSize + " " + contextAmount + " " + durationInMs);

//                if (achievable) {
//                    logger.trace(experimentId + " achievable");
//                    achievable = false;
//                } else {
//                    logger.trace(experimentId + " unachievable");
//                }

            }
        }
    }


    private Set<Context> generateContextSet(int contextAmount) {

        HashSet<Context> allContexts = new HashSet<Context>();

        for (int contextIndex = 1; contextIndex <= contextAmount; contextIndex++) {
            allContexts.add(new Context("c" + contextIndex));
        }

        return allContexts;
    }
}

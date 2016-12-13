package experiment.runtime.util;

import org.apache.logging.log4j.Logger;

import br.ime.usp.improv.pragmatic.CGM;
import br.ime.usp.improv.pragmatic.Context;
import br.ime.usp.improv.pragmatic.util.generator.pragmatic.CGMGenerator;

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
    public int repetitions = 10;
    private int contextSet = 1;

    public ScientificalEvaluation(CGMGenerator generator) {
        this.generator = generator;
    }


    public void executeScientificalEvaluation(Logger logger) {
        {
            int i;
            int modelSize;
            boolean achievable = false;
            long durationInMs = 0;

            Set<Context> current = generateContextSet(contextAmount);

            for (modelSize = minModelSize; modelSize <= maxModelSize; modelSize += modelStep) {
                long accumulated = 0;
                for (i = 0; i < generatedModelsAmount; i++) {

                    // Setup Model
                    CGM cgm = generator.generateCGM(modelSize, contextAmount);


                    accumulated += performAverageMeasures(current, cgm);

                    if (cgm.isAchievable(current, null) != null) {
                        achievable = true;
                    }

                    if (accumulated < 0)
                        throw new ArithmeticException("TimeMetric evaluation Overflow");


                }
                durationInMs = accumulated / generatedModelsAmount;
                System.out.println(experimentId + ": " + modelSize + " " + contextAmount + " " + durationInMs);

                // Print result
                logger.trace(experimentId + ": " + modelSize + " " + contextAmount + " " + durationInMs);

            }
        }
    }


    public void executeSweep(Logger logger) {
        {
            int i;
            int modelSize;
            long durationInMs = 0;

            Set<Context> current = generateContextSet(contextAmount);


            for (modelSize = minModelSize; modelSize <= maxModelSize; modelSize += modelStep) {
                System.out.println("Test with " + modelSize + " nodes.");
                System.out.println("Test with " + contextAmount + " contexts.");
                long accumulated = 0;
                for (i = 0; i < generatedModelsAmount; i++) {
                    System.out.println("Model " + i + "...");

                    // Setup Model
                    CGM cgm = generator.generateCGM(modelSize, contextAmount);

                    long limit = (long) Math.pow(2, contextAmount);
                    for (int contextIndex = 0; contextIndex < limit; contextIndex++) {
                        accumulated += performAverageMeasures(generateContextSet(contextAmount, contextIndex), cgm);
                    }

                    if (accumulated < 0)
                        throw new ArithmeticException("TimeMetric evaluation Overflow");

                }

                durationInMs = accumulated / (repetitions * generatedModelsAmount); // TimeMetric in nanosseconds for each execution

                System.out.println(experimentId + ": " + modelSize + " " + contextAmount + " " + durationInMs);

                // Print result
                logger.trace(experimentId + ": " + modelSize + " " + contextAmount + " " + durationInMs);
            }
        }
    }

    private long performAverageMeasures(Set<Context> current, CGM cgm) {
        long start = System.nanoTime();
        for (int j = 0; j < repetitions; j++) {
            // Execute test
            cgm.isAchievable(current, null);
        }
        long durationInMs = TimeUnit.MILLISECONDS.convert((System.nanoTime() - start), TimeUnit.NANOSECONDS);

        return durationInMs / repetitions;
    }


    private Set<Context> generateContextSet(int contextAmount) {

        HashSet<Context> allContexts = new HashSet<Context>();

        for (int contextIndex = 1; contextIndex <= contextAmount; contextIndex++) {
            allContexts.add(new Context("c" + contextIndex));
        }

        return allContexts;
    }


    private Set<Context> generateContextSet(int contextAmount, int contextIndex) {
        long limit;
        HashSet<Context> contexts = new HashSet<Context>();

        limit = (long) Math.pow(2, contextAmount);
        for (int i = 0; i < limit; i++) {
            if (contextIndex % 2 != 0) {
                contexts.add(new Context("c" + (contextAmount - i)));
                //System.out.println("c" + (contextAmount - i));
            }
            contextIndex /= 2;
        }
        return contexts;
    }

}

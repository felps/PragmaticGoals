package experiment.pragmatic.Scalability;

import org.apache.logging.log4j.*;
import org.junit.Test;
import pragmatic.CGM;
import pragmatic.Context;
import pragmatic.util.generator.pragmatic.RandomCGMGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ScalabilityEvaluationRandomModels {

    private static final Marker EXP_MARKER = MarkerManager.getMarker("EXPERIMENTS");
    Logger logger = LogManager.getLogger();
    RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

    @Test
    public void scalabilityTestModelSize() {

		int round = 1;
		int contexts = 10;
		for (int model = 10; model < 10000; model += 100) {
            executeScientificalEvaluation("" + 1, contexts, model, 10, 10);
        }
	}

    @Test
    public void scalabilityTestContextSize() {

		int round = 1, model = 1000;
        for (int i = 1; i < 20; i++) {
            int contextAmount = i;
            String message = "Evaluation:\n Contexts: " + contextAmount + " model size: " + model + "\n";

            executeScientificalEvaluation(message, contextAmount, model, 10, 10);
        }
	}

	@Test
	public void shouldGenerateCompleteSetOfContextsWith1Context(){
		Set<Context> current = generateCompleteContextSet(1);
		assertEquals(2, current.size());
		assertTrue(current.contains(new Context("c1")));
		assertTrue(current.contains(new Context("C1")));
	}
	
	@Test
	public void shouldGenerateCompleteSetOfContextsWith2Context(){
		Set<Context> current = generateCompleteContextSet(2);
		assertEquals(3, current.size());
		assertTrue(current.contains(new Context("c1")));
		assertTrue(current.contains(new Context("C1")));
		assertTrue(current.contains(new Context("c2")));
		assertTrue(current.contains(new Context("C2")));
		
	}
	
	@Test
	public void shouldGenerateCompleteSetOfContextsWith4Context(){
		Set<Context> current = generateCompleteContextSet(4);
		assertEquals(5, current.size());
		assertTrue(current.contains(new Context("c1")));
		assertTrue(current.contains(new Context("C1")));	
		assertTrue(current.contains(new Context("c2")));
		assertTrue(current.contains(new Context("C2")));
		assertTrue(current.contains(new Context("c3")));
		assertTrue(current.contains(new Context("C3")));
		assertTrue(current.contains(new Context("c4")));
		assertTrue(current.contains(new Context("C4")));
	}
	
	@Test
	public void shouldGenerateCompleteSetOfContextsWith10Context(){
		Set<Context> current = generateCompleteContextSet(10);
		assertEquals(11, current.size());
		assertTrue(current.contains(new Context("c1")));
		assertTrue(current.contains(new Context("C1")));	
		assertTrue(current.contains(new Context("c2")));
		assertTrue(current.contains(new Context("C2")));
		assertTrue(current.contains(new Context("c3")));
		assertTrue(current.contains(new Context("C3")));
		assertTrue(current.contains(new Context("c4")));
		assertTrue(current.contains(new Context("C4")));
		assertTrue(current.contains(new Context("c5")));
		assertTrue(current.contains(new Context("C5")));
		assertTrue(current.contains(new Context("c6")));
		assertTrue(current.contains(new Context("C6")));
		assertTrue(current.contains(new Context("c7")));
		assertTrue(current.contains(new Context("C7")));
		assertTrue(current.contains(new Context("c8")));
		assertTrue(current.contains(new Context("C8")));
		assertTrue(current.contains(new Context("c9")));
		assertTrue(current.contains(new Context("C9")));
		assertTrue(current.contains(new Context("c10")));
		assertTrue(current.contains(new Context("C10")));
	}

    @Test
    public void scalabilityTestModelAndContextSize() {

        logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "Scalability Evaluation - Varying Model and Context amounts");
        logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "Experiment executed on " + (new Date()).toString());

        executeScientificalEvaluation("", 1, 10, 10, 10);

        for (int contexts = 1; contexts < 30; contexts++) {
            for (int modelSize = 100; modelSize < 5000; modelSize += 100) {
                int modelAmount = 10;
                int repetitions = 10;
                executeScientificalEvaluation("", contexts, modelSize, modelAmount, repetitions);
            }
        }
    }

    @Test
    public void testSingleModelAndContextSize() {

        logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "Scalability Evaluation - Varying Model and Context amounts");
        logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "Experiment executed on " + (new Date()).toString());

        long accumulated = 0;
        boolean achievable = false;

        RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

        Set<Context> current = generateCompleteContextSet(1);

        if (current == null) {
            logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "NULL");
            fail();
        }

        int modelAmount = 100;
        int repetitions = 100;

        for (int i = 0; i < modelAmount; i++) {
            // Setup Model
            CGM cgm = cgmFactory.generateRandomCGM(100, 1);
            //logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "Model size:" + cgm.getRoot().size());
            long start = System.nanoTime();
            for (int j = 0; j < repetitions; j++) {
                // Execute test
                cgm.isAchievable(current, null);
            }
            accumulated += (System.nanoTime() - start);

            if (cgm.isAchievable(current, null) != null) {
                achievable = true;
            }

        }

        // Print result
        long timePerExecutionInNs = accumulated / (modelAmount * repetitions); // TimeMetric in nanosseconds for each execution
        long timeInMs = accumulated / (1000 * modelAmount * repetitions); // TimeMetric in milliseconds

        logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "Resultado: ");

        if (achievable) {
            logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "achievable ");
            achievable = false;
        } else
            logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "unachievable ");

        logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, 10 + " " + 1 + " " + timeInMs + " ms " + timePerExecutionInNs + " ns");


    }

    private void executeScientificalEvaluation(String experimentId, int contextAmount, int modelSize, int modelAmount, int repetitions) {
        boolean achievable = false;

        RandomCGMGenerator cgmFactory = new RandomCGMGenerator();
        Set<Context> current = generateCompleteContextSet(contextAmount);

        if (current == null) {
            logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "NULL");
            fail();
        }

        long accumulated = 0;
        for (int i = 0; i < modelAmount; i++) {
            // Setup Model
            CGM cgm = cgmFactory.generateRandomCGM(modelSize, contextAmount);

            long start = System.nanoTime();
            for (int j = 0; j < repetitions; j++) {
                // Execute test
                cgm.isAchievable(current, null);
            }
            accumulated += (System.nanoTime() - start);

            if (cgm.isAchievable(current, null) != null) {
                achievable = true;
            }

        }

        long timePerExecutionInNs = accumulated / (modelAmount * repetitions); // TimeMetric in nanosseconds for each execution
        long durationInMs = TimeUnit.MILLISECONDS.convert(timePerExecutionInNs, TimeUnit.NANOSECONDS);

        // Print result
        logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, experimentId + " ");

        if (achievable) {
            logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "achievable ");
            achievable = false;
        } else
            logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, "unachievable ");

        logger.log(Level.forName("EXPERIMENT", 350), EXP_MARKER, modelSize + " " + contextAmount + " " + durationInMs + " ms " + timePerExecutionInNs + " ns");

    }


	private Set<Context> generateCompleteContextSet(int contextAmount) {
		HashSet<Context> contexts = new HashSet<Context>();

		for (int contextIndex = 1; contextIndex <= contextAmount; contextIndex++) {
			contexts.add(new Context("c" + contextIndex));
		}

		contexts.add(null);
		return contexts;
	}
}

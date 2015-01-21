package experiment;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cgm.CGM;
import cgm.Context;
import cgm.util.generator.RandomCGMGenerator;

public class ContextSweep {

	private int contextSet;
	

	@Test
	public void shouldPrintAllContextSets() {
		for (Context context : generateNextContextSet(4)) {
			System.out.println(context.getName());
		}
	}

	@Test
	public void shouldGenerateAllContextSetsForSeveralContextSetSizes() {
		for (int i = 0; i < 10; i++) {
			assertEquals(Math.pow(2, i) - 1, generateNextContextSet(i));
		}
	}

	// @Test
	public void scalabilityTestModelAndContextSize() {

		int contextAmount = 20;
		System.out.println("Scalability Evaluation - Context sweep capability with 20 context set");
		System.out.println("Experiment executed on " + (new Date()).toString());

		executeScientificalEvaluation("", 1, 10);

		for (int model = 100; model < 10001; model += 100) {
			executeScientificalEvaluation("", contextAmount, model);
		}
	}

	private void executeScientificalEvaluation(String experimentId, int contextAmount, int modelSize) {
		{
			int models = 100;
			long oneSecond = 1000000000l; // one second equals 10^9 ns
			double totalPossibleContextSets = Math.pow(2, contextAmount) - 1;
			long executions = 0;

			RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

			contextSet = 1;
			for (int j = 0; j < models; j++) {
				executions = 0;
				// Setup Model
				CGM cgm = cgmFactory.generateRandomCGM(modelSize, contextAmount);

				long start = System.nanoTime();
				for (long i = 0; i < totalPossibleContextSets; i++) {
					// To disregard the time of setting up the next context, we
					// calculate the time it took to do so and add it to the
					// start time
					long startPause = System.nanoTime();
					Set<Context> current = generateNextContextSet(contextAmount);
					long pauseLength = System.nanoTime() - startPause;
					start += pauseLength;

					// Execute test
					cgm.isAchievable(current, null);
					if ((System.nanoTime() - start) > oneSecond) {
						break;
					}
					executions++;
				}
				// Reset the context set index
				contextSet = 0;
			}
			//Average executions per second through all models
			double avgExecutions = executions / models ;
			
			// Average coverage of the context sets per second
			double parameterSweepCoverage = (avgExecutions / totalPossibleContextSets) * 100;  

			// Print result
			System.out.print(experimentId + " ");
			System.out.println(modelSize + " " + contextAmount + " " + parameterSweepCoverage);
		}
	}

	private Set<Context> generateNextContextSet(int contextAmount) {
		long limit;
		HashSet<Context> contexts = new HashSet<Context>();
		limit = (long) Math.pow(2, contextAmount);
		for (long i = 2; i <= limit; i *= 2) {
			if (contextSet % i == 0) {
				contexts.add(new Context("c" + i));
			}
		}

		contexts.add(null);
		contextSet++;
		return contexts;
	}

}

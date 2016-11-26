package experiment.pragmatic;

import pragmatic.CGM;
import pragmatic.Context;
import pragmatic.util.generator.pragmatic.RandomCGMGenerator;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ContextSweepUnlimited {

	private int contextSet = 1;

//	@Test
//	public void printAllContexts() throws Exception {
//		for(int i = 0;i<Math.pow(2, 4)-1; i++){
//			System.out.println("Context Set: " + contextSet);
//			Set<Context> set = generateNextContextSet(4);
//		}
//	}
	
	@Test
	public void scalabilityTestContextSweep() {

		int contextAmount = 5;
		System.out.println("Scalability Evaluation - Context sweep capability with 5 context set");
		System.out.println("Experiment executed on " + (new Date()).toString());

		executeScientificalEvaluation("", 1, 10);

		for (int modelSize = 100; modelSize < 10001; modelSize += 100) {
			executeScientificalEvaluation("", contextAmount, modelSize);
		}

		contextAmount = 10;
		System.out.println("Scalability Evaluation - Context sweep capability with 10 context set");
		System.out.println("Experiment executed on " + (new Date()).toString());

		executeScientificalEvaluation("", 1, 10);

		for (int modelSize = 100; modelSize < 10001; modelSize += 100) {
			executeScientificalEvaluation("", contextAmount, modelSize);
		}

		contextAmount = 15;
		System.out.println("Scalability Evaluation - Context sweep capability with 15 context set");
		System.out.println("Experiment executed on " + (new Date()).toString());

		executeScientificalEvaluation("", 1, 10);

		for (int modelSize = 100; modelSize < 10001; modelSize += 100) {
			executeScientificalEvaluation("", contextAmount, modelSize);
		}
		
		contextAmount = 20;
		System.out.println("Scalability Evaluation - Context sweep capability with 20 context set");
		System.out.println("Experiment executed on " + (new Date()).toString());

		executeScientificalEvaluation("", 1, 10);

		for (int modelSize = 100; modelSize < 10001; modelSize += 100) {
			executeScientificalEvaluation("", contextAmount, modelSize);
		}

}

	private void executeScientificalEvaluation(String experimentId, int contextAmount, int modelSize) {
		{
			int models = 10;
			long tenSecond = 10000000000l; // one second equals 10^9 ns
			double totalPossibleContextSets = Math.pow(2, contextAmount) - 1;

			RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

			contextSet = 0;
			long totalExecutions = 0;
			for (int j = 0; j < models; j++) {
				long executions = 0;
				
				// Setup Model
				CGM cgm = cgmFactory.generateRandomCGM(modelSize, contextAmount);

				long start = System.nanoTime();
				boolean timeout = false;
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
					
					executions++;
					
					if ((System.nanoTime() - start) > tenSecond) {
						System.out.println("\"Executing model "+j+" with "+modelSize + " nodes... Partial sweep: " + ((executions/ totalPossibleContextSets) * 100) + "%\"");
						timeout = true;
						break;
					}
				}
				if(!timeout)
					System.out.println("\"Executing model "+j+" with "+modelSize + " nodes... Full sweep: " + ((executions/ totalPossibleContextSets) * 100) + "%\"");
				totalExecutions += executions;
				// Reset the context set index
				contextSet = 0;
			}
			//Average executions within 10 seconds through all models
			double avgExecutions = totalExecutions / models ;
			
			// Average coverage of the context sets within ten seconds
			double parameterSweepCoverage = (avgExecutions / totalPossibleContextSets) * 100;  

			// Print result
			System.out.print(experimentId + " ");
			System.out.println(modelSize + " " + contextAmount + " " + avgExecutions +" "+ parameterSweepCoverage);
		}
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

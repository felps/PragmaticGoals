package experiment.pragmatic;

import pragmatic.CGM;
import pragmatic.Context;
import pragmatic.util.generator.pragmatic.RandomCGMGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ContextSweepFull {

	private int contextSet = 1;

	//@Test
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
			double totalPossibleContextSets = Math.pow(2, contextAmount) - 1;

			RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

			contextSet = 0;
			float totalTime = 0;
			for (int j = 0; j < models; j++) {
				
				// Setup Model
				CGM cgm = cgmFactory.generateRandomCGM(modelSize, contextAmount);

				long start;
				start = System.currentTimeMillis();
				for (long i = 0; i < totalPossibleContextSets; i++) {
					Set<Context> current = generateNextContextSet(contextAmount);
					
					// Execute test
					cgm.isAchievable(current, null);
				}
				
				System.out.println("\"Executing model "+j+" with "+modelSize + " nodes... Full sweep in: " + (System.currentTimeMillis() - start));
				totalTime += (System.currentTimeMillis() - start);
				// Reset the context set index
				contextSet = 0;
			}
			// Print result
			System.out.print(experimentId + " ");
			System.out.println(modelSize + " " + contextAmount + " " + (totalTime/models));
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

package experiment;

import gm.cgm.CGM;
import gm.cgm.Context;
import gm.cgm.util.generator.RandomCGMGenerator;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class ContextSweepFull {

	private int contextSet = 1;
	private HashMap<Integer, HashMap<Integer, CGM>> models;

	@BeforeClass
	public void setUpBeforeClass() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		int refinementsAmount = 10;
		int contextAmount = 1;
		CGM cgm = cgmFactory.generateRandomCGM(refinementsAmount, contextAmount);
		storeModel(refinementsAmount, contextAmount, cgm);

		// Setup Model
		for (refinementsAmount = 200; refinementsAmount < 10000; refinementsAmount += 200) {
			cgm = cgmFactory.generateRandomCGM(refinementsAmount, contextAmount);
			storeModel(refinementsAmount, contextAmount, cgm);

		}

	}
	// @Test
	// public void printAllContexts() throws Exception {
	// for(int i = 0;i<Math.pow(2, 4)-1; i++){
	// System.out.println("Context Set: " + contextSet);
	// Set<Context> set = generateNextContextSet(4);
	// }
	// }

	private void storeModel(int refinementsAmount, int contextAmount, CGM cgm) {
		HashMap<Integer, CGM> modelsContext = new HashMap<Integer, CGM>();
		modelsContext.put(contextAmount, cgm);
		models.put(refinementsAmount, modelsContext);
	}

	private CGM getModel(int refinementsAmount, int contextAmount) {
		HashMap<Integer, CGM> contextualModels = models.get(refinementsAmount);
		CGM cgm = contextualModels.get(contextAmount);
		return cgm;
	}

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
			double totalPossibleContextSets = Math.pow(2, contextAmount) - 1;

			contextSet = 0;
			float totalTime = 0;
			for (int j = 0; j < models; j++) {

				// Setup Model
				CGM cgm = getModel(modelSize, contextAmount);

				long start;
				start = System.currentTimeMillis();
				for (long i = 0; i < totalPossibleContextSets; i++) {
					Set<Context> current = generateNextContextSet(contextAmount);

					// Execute test
					cgm.isAchievable(current, null);
				}

				System.out.println("\"Executing model " + j + " with " + modelSize + " nodes... Full sweep in: "
						+ (System.currentTimeMillis() - start));
				totalTime += (System.currentTimeMillis() - start);
				// Reset the context set index
				contextSet = 0;
			}
			// Print result
			System.out.print(experimentId + " ");
			System.out.println(modelSize + " " + contextAmount + " " + (totalTime / models));
		}
	}

	private Set<Context> generateNextContextSet(int contextAmount) {
		HashSet<Context> contexts = new HashSet<Context>();
		int currentSet = contextSet;
		for (int i = 0; i < contextAmount; i++) {
			if (currentSet % 2 != 0) {
				contexts.add(new Context("c" + (contextAmount - i)));
				// System.out.println("c" + (contextAmount - i));
			}
			currentSet /= 2;
		}

		// contexts.add(null);
		contextSet++;
		return contexts;
	}

}

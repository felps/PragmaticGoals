package experiment;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cgm.CGM;
import cgm.Context;
import cgm.util.RandomCGMGenerator;

public class ScalabilityEvaluation {

	RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

	// @Test
	public void scalabilityTestModelSize() {

		int round = 1;
		int contexts = 10;
		for (int model = 10; model < 10000; model += 100) {
			executeScientificalEvaluation("" + 1, contexts, model);
		}
	}

	// @Test
	public void scalabilityTestContextSize() {

		int round = 1, model = 1000;
		for (int contexts = 10; contexts < 10000; contexts += 100) {
			executeScientificalEvaluation("" + 2, contexts, model);
		}
	}

	@Test
	public void scalabilityTestModelAndContextSize() {

		System.out.println("Scalability Evaluation - Varying Model and Context amounts");
		System.out.println("Experiment executed on " + (new Date()).toString());
		
		int round = 1;
		for (int contexts = 1; contexts < 30; contexts++) {
			for (int model = 100; model < 10000; model += 100) {
				executeScientificalEvaluation("" + 3, contexts, model);
			}
		}
	}
	private void executeScientificalEvaluation(String experimentId, int contextAmount, int modelSize) {
		{
			long accumulated = 0;

			RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

			for (int i = 0; i < 100; i++) {
				// Setup Model
				CGM cgm = cgmFactory.generateRandomCGM(modelSize, contextAmount);

				Set<Context> current = generateRandomContextSet(contextAmount);

				long start = System.nanoTime();
				for (int j = 0; j < 1000; j++) {
					// Execute test
					cgm.isAchievable(current, null);
				}
				accumulated += (System.nanoTime() - start);

				// Print result
			}

			System.out.println("Experiment " + experimentId + " ; " + modelSize + "; " + contextAmount + "; "
					+ (accumulated));
		}
	}

	private Set<Context> generateRandomContextSet(int contextAmount) {
		int randomAmount = (int) Math.floor(Math.random() * (contextAmount + 1));

		HashSet<Context> contexts = new HashSet<Context>();

		while (contexts.size() < randomAmount) {
			int contextIndex = ((int) (Math.random() * contextAmount)) + 1;
			contexts.add(new Context("C" + contextIndex));
		}

		return contexts;
	}
}

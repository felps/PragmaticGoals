package experiment.pragmatic.Scalability;

import cgm.CGM;
import cgm.Context;
import cgm.util.generator.pragmatic.CGMGenerator;
import cgm.util.generator.pragmatic.WorstCaseCGMGenerator;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ScalabilityEvaluationWorstCaseScenarios {

	WorstCaseCGMGenerator cgmFactory = new WorstCaseCGMGenerator();

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

		System.out.println("Scalability Evaluation - Worst Case Model and Varying Context amounts");
		System.out.println("Experiment executed on " + (new Date()).toString());
		
		int round = 1;
		for (int contexts = 1; contexts < 30; contexts++) {
			for (int model = 100; model < 10000; model += 100) {
				executeScientificalEvaluation("", contexts, model);
			}
		}
	}
	
	private void executeScientificalEvaluation(String experimentId, int contextAmount, int modelSize) {
		{
			long accumulated = 0;
			boolean achievable = false;

			CGMGenerator cgmFactory = new WorstCaseCGMGenerator();

			// Setup Model
			CGM cgm = cgmFactory.generateCGM(modelSize, contextAmount);

			Set<Context> current = generateContextSet(contextAmount);

			long start = System.nanoTime();
			for (int j = 0; j < 100; j++) {
				// Execute test
				cgm.isAchievable(current, null);
			}
			accumulated += (System.nanoTime() - start);
			
			if(cgm.isAchievable(current, null) != null){
				achievable = true;
			}

			if (accumulated<0)
				throw new ArithmeticException("TimeMetric evaluation Overflow");
			// Print result
		
			if(achievable){
				System.out.println("achievable");
				achievable = false;
			} else
				System.out.println("unachievable");	
			
			System.out.println(experimentId + " " + modelSize + " " + contextAmount + " "
					+ (accumulated/100));
		}
	}

	private Set<Context> generateContextSet(int contextAmount) {
		
		HashSet<Context> allContexts = new HashSet<Context>();
		
		for(int contextIndex=1;contextIndex<=contextAmount;contextIndex++){
			allContexts .add(new Context("c" + contextIndex));
		}

		return allContexts;
	}
}

package experiment;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cgm.CGM;
import cgm.Context;
import cgm.util.generator.RandomCGMGenerator;

public class ScalabilityEvaluationRandomModels {

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

		System.out.println("Scalability Evaluation - Varying Model and Context amounts");
		System.out.println("Experiment executed on " + (new Date()).toString());

		executeScientificalEvaluation("", 1, 10);
		
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

			RandomCGMGenerator cgmFactory = new RandomCGMGenerator();
			Set<Context> current = generateCompleteContextSet(contextAmount);

			if (current == null) {
				System.out.println("NULL");
				fail();
			}

			for (int i = 0; i < 100; i++) {
				// Setup Model
				CGM cgm = cgmFactory.generateRandomCGM(modelSize, contextAmount);

				long start = System.nanoTime();
				for (int j = 0; j < 100; j++) {
					// Execute test
					cgm.isAchievable(current, null);
				}
				accumulated += (System.nanoTime() - start);

				if (cgm.isAchievable(current, null) != null) {
					achievable = true;
				}

			}

			// Print result
			
			long timePerExecutionInNs = accumulated/10000; // Time in nanosseconds for each execution
			long timeInMs = accumulated/1000; // Time in milliseconds
			
			System.out.print(experimentId + " ");
			
			if (achievable) {
				System.out.print("achievable ");
				achievable = false;
			} else
				System.out.print("unachievable ");
			
			System.out.println(modelSize + " " + contextAmount + " "+ timeInMs + " ms " + timePerExecutionInNs + " ns");
		}
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

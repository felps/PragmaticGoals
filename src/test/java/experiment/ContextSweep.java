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
	private long limit;


	@Test
	public void shouldPrintAllContextSets(){
		for (Context context : generateNextContextSet(4)) {
			System.out.println(context.getName());
		}
	}
	
	@Test
	public void shouldGenerateAllContextSetsForSeveralContextSetSizes(){
		for(int i=0;i<10;i++){
			assertEquals(Math.pow(2, i)-1, generateNextContextSet(i));
		}
	}
	
	//@Test
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
			int executions = 0;

			RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

			contextSet = 1;
			
			for (int i = 0; i < 100; i++) {
				// Setup Model

				CGM cgm = cgmFactory.generateRandomCGM(modelSize, contextAmount);

				executions = 0;
				for (int j = 0; j < 100; j++) {
					Set<Context> current = generateNextContextSet();
					// Execute test
					long start = System.nanoTime();
					cgm.isAchievable(current, null);
					if((System.nanoTime() - start)>60000000000l){
						break;
					}
					executions++;
				}
			}
			// Print result
			double parameterSweepCoverage = (executions/(Math.pow(2, 50)-1))*100; // Time in nanosseconds for each execution
			//long timeInMs = accumulated/1000; // Time in milliseconds
			
			System.out.print(experimentId + " ");
			
			System.out.println(modelSize + " " + contextAmount + " "+ parameterSweepCoverage);
		}
	}


	private Set<Context> generateNextContextSet(int contextAmount) {
		HashSet<Context> contexts = new HashSet<Context>();
		limit = (long) Math.pow(2l, contextAmount);
		for (long i= 2; i <= limit; i*=2) {
			System.out.println("Context set: "+contextSet);
			if(contextSet%i==0){
				contexts.add(new Context("c" + i));
				System.out.println("c"+i);
			}
		}

		contexts.add(null);
		contextSet++;
		return contexts;
	}

}

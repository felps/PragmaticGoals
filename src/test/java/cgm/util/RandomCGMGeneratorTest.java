package cgm.util;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cgm.CGM;
import cgm.Context;
import cgm.Refinement;

public class RandomCGMGeneratorTest {

	@Test
	public void shouldCreateASingleTask() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();
		
		CGM cgm  = cgmFactory.generateCGM(1, 2);
		
		assertTrue(cgm.getRoot().myType() == Refinement.TASK);
	}
	
	@Test
	public void shouldCreateASingleGoalWithASingleTask() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();
		
		CGM cgm  = cgmFactory.generateCGM(2, 2);
		
		assertTrue(cgm.getRoot().myType() == Refinement.GOAL);
		
		for (Refinement dep : cgm.getRoot().getDependencies()) {
			assertTrue(dep.myType() == Refinement.TASK);
		}
	}
	
	@Test
	public void shouldCreateASingleCGMWithTwoLevels() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();
		
		CGM cgm  = cgmFactory.generateCGM(3, 2);
		
		assertEquals(3, countRefinements(cgm.getRoot()));
	}
	
	
	@Test
	public void shouldCreateLargerCGMs() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();
		
		for(int i = 1;i<100;i++){
			CGM cgm  = cgmFactory.generateCGM(3*i, 1);
			assertEquals(3*i, countRefinements(cgm.getRoot()));
		}
	}
	
	
	@Test
	public void shouldCreateAVeryLargeCGM() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();
		
		CGM cgm  = cgmFactory.generateCGM(3000, 1);
		assertEquals(3000, countRefinements(cgm.getRoot()));
	}
	
	@Test
	public void shouldCreateCGMWithManyContexts() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		boolean successHistory = false;
		for (int i = 0; i < 100; i++) {
			CGM cgm  = cgmFactory.generateCGM(1000, 30);
			
			if(30 == collectContexts(cgm.getRoot()).size()){
				successHistory = true;
				break;
			}
						
		}
		
		assertTrue(successHistory);
	}
	
	

	private Set<Context> collectContexts(Refinement root) {
		HashSet<Context> contextSet = new HashSet<Context>();
		
		contextSet.addAll(root.getApplicableContext());
		for (Refinement dep : root.getDependencies()) {
			contextSet.addAll(collectContexts(dep));
		}
		contextSet.remove(null);
		return contextSet;
	}

	private int countRefinements(Refinement refinement) {
		int amount = 1;
		
		for (Refinement dep : refinement.getDependencies()) {
			amount = amount + countRefinements(dep);
		}
		return amount;
	}

}

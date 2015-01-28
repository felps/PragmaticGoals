package cgm.util.generator;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cgm.CGM;
import cgm.Context;
import cgm.Refinement;
import cgm.util.generator.WorstCaseCGMGenerator;

public class WorstCaseCGMGeneratorTest {
	WorstCaseCGMGenerator cgmFactory = new WorstCaseCGMGenerator();
	
	@Test
	public void shouldCreateASingleTask() {
		CGM cgm = cgmFactory.generateCGM(1, 2);

		assertTrue(cgm.getRoot().myType() == Refinement.TASK);
		assertEquals(1, countTreeLevels(cgm.getRoot(), 0));
	}

	@Test
	public void shouldCreateASingleGoalWithASingleTask() {
		CGM cgm = cgmFactory.generateCGM(2, 2);

		assertTrue(cgm.getRoot().myType() == Refinement.GOAL);

		for (Refinement dep : cgm.getRoot().getDependencies()) {
			assertTrue(dep.myType() == Refinement.TASK);
		}
		assertEquals(2, countTreeLevels(cgm.getRoot(), 0));
	}

	@Test
	public void shouldCreateASingleCGMWithTwoLevels() {

		CGM cgm = cgmFactory.generateCGM(3, 2);

		assertEquals(3, countRefinements(cgm.getRoot()));
		assertEquals(2, countTreeLevels(cgm.getRoot(), 0));
	}

	@Test
	public void shouldCreateLargerCGMs() {

		for (int i = 1; i < 100; i++) {
			CGM cgm = cgmFactory.generateCGM(3 * i, 1);
			assertEquals(3 * i, countRefinements(cgm.getRoot()));
		}
	}

	@Test
	public void shouldCreateAVeryLargeCGM() {

		CGM cgm = cgmFactory.generateCGM(4095, 1);
		assertEquals(4095, countRefinements(cgm.getRoot()));
		assertEquals(12, countTreeLevels(cgm.getRoot(), 0));
	}

	@Test
	public void shouldCreateCGMWithManyContexts() {

		CGM cgm = cgmFactory.generateCGM(1000, 30);

		assertEquals(30, collectContexts(cgm.getRoot()).size()); 	
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

	private int countTreeLevels(Refinement refinement, int currentLevel) {
		int amount = 1;
		int maxLevels = 0;

		for (Refinement dep : refinement.getDependencies()) {
			int levelsBelow = countTreeLevels(dep, currentLevel++);
			if (maxLevels < levelsBelow)
				maxLevels = levelsBelow;
		}

		return amount + maxLevels;
	}

}

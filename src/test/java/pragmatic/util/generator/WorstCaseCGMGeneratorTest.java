package pragmatic.util.generator;

import pragmatic.*;
import pragmatic.util.generator.pragmatic.WorstCaseCGMGenerator;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WorstCaseCGMGeneratorTest {
	private WorstCaseCGMGenerator cgmFactory = new WorstCaseCGMGenerator();
	
	@Test
	public void shouldCreateASingleTask() {
		CGM cgm = cgmFactory.generateCGM(1, 2);

		assertTrue(cgm.getRoot() instanceof Task);
		assertEquals(1, countTreeLevels(cgm.getRoot(), 0));
	}

	@Test
	public void shouldCreateASingleGoalWithASingleTask() {
		CGM cgm = cgmFactory.generateCGM(2, 2);

		assertTrue(cgm.getRoot() instanceof Goal);

		for (Refinement dep : ((Goal) cgm.getRoot()).getDependencies()) {
			assertTrue(dep instanceof  Task);
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
		HashSet<Context> contextSet = new HashSet<>();

		contextSet.addAll(root.getApplicableContexts());
		if (root instanceof Goal) {
			for (Refinement dep : ((Goal) root).getDependencies()) {
				contextSet.addAll(collectContexts(dep));
			}
		}
		contextSet.remove(null);
		return contextSet;
	}

	private int countRefinements(Refinement refinement) {
		int amount = 1;

		if (refinement instanceof Goal)
		for (Refinement dep : ((Goal) refinement).getDependencies()) {
			amount = amount + countRefinements(dep);
		}
		return amount;
	}

	private int countTreeLevels(Refinement refinement, int currentLevel) {
		int amount = 1;
		int maxLevels = 0;

		if (refinement instanceof Goal)
		for (Refinement dep : ((Goal) refinement).getDependencies()) {
			int levelsBelow = countTreeLevels(dep, currentLevel++);
			if (maxLevels < levelsBelow)
				maxLevels = levelsBelow;
		}

		return amount + maxLevels;
	}

}

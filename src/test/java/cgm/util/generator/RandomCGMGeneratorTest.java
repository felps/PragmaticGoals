package cgm.util.generator;

import cgm.CGM;
import cgm.Context;
import cgm.Refinement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RandomCGMGeneratorTest {

	Logger logger = LogManager.getLogger();

	@Test
	public void shouldCreateASingleTask() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		CGM cgm = cgmFactory.generateCGM(1, 2);

		assertTrue(cgm.getRoot().myType() == Refinement.TASK);
	}

	@Test
	public void aSingleTaskMayBeAchievable() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		CGM cgm = cgmFactory.generateCGM(1, 2);

		Set<Context> current = new HashSet<Context>();
		current.add(new Context("c1"));
		current.add(new Context("c2"));
		assertTrue(cgm.isAchievable(current, null) != null);
	}

	@Test
	public void shouldCreateASingleGoalWithASingleTask() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		CGM cgm = cgmFactory.generateCGM(2, 2);

		assertTrue(cgm.getRoot().myType() == Refinement.GOAL);

		for (Refinement dep : cgm.getRoot().getDependencies()) {
			assertTrue(dep.myType() == Refinement.TASK);
		}
	}

	@Test
	public void aSingleGoalMayBeApplicable() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		CGM cgm = cgmFactory.generateCGM(2, 2);
		Set<Context> current = new HashSet<Context>();
		current.add(new Context("c1"));
		current.add(new Context("c2"));
		logger.debug("Contexts available:");
		for (Context context : current) {
			logger.debug(">" + context.getName() + "<");
		}

		logger.debug("Contexts applicable:");
		for (Context context : cgm.getRoot().getApplicableContext()) {
			logger.debug(">" + context.getName() + "<");
		}

		for (Context context : current) {
			for (Context applicable : cgm.getRoot().getApplicableContext()) {
				if (applicable.equals(context)) {
					logger.debug("Context valid");
					if (context.equals(applicable))
						logger.debug("Equals not reflexive");
					if (cgm.getRoot().getApplicableContext().contains(context)) {
						logger.debug("And recognized");
					} else
						logger.debug("And NOT recognized");
				}
			}

		}

		assertTrue(cgm.getRoot().isApplicable(current));
	}

	@Test
	public void aSingleGoalMayBeAchievable() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		Set<Context> current = new HashSet<Context>();
		current.add(new Context("c1"));
		current.add(new Context("c2"));
		logger.debug("Contexts available:");
		for (Context context : current) {
			logger.debug(">" + context.getName() + "<");
		}

		boolean isAchievable = false;
		for (int i = 0; i < 10; i++) {
			CGM cgm = cgmFactory.generateCGM(2, 2);
			if (cgm.getRoot().isAchievable(current, null) != null)
				isAchievable = true;
		}

		assertTrue(isAchievable);
	}

	@Test
	public void allGoalsMayBeAchievable() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		Set<Context> current = new HashSet<Context>();
		current.add(new Context("c1"));
		current.add(new Context("c2"));
		logger.debug("Contexts available:");
		for (Context context : current) {
			logger.debug(">" + context.getName() + "<");
		}

		boolean isAchievable = false;
		for (int i = 0; i < 10; i++) {
			CGM cgm = cgmFactory.generateCGM(2, 2);
			if (cgm.getRoot().isAchievable(current, null) != null)
				isAchievable = true;
		}

		assertTrue(isAchievable);
	}

	
	@Test
	public void allGoalsMayBeAchievableEvenWithLotsOfGoals() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		Set<Context> current = new HashSet<Context>();
		current.add(new Context("c1"));
		current.add(new Context("c2"));
		logger.debug("Contexts available:");
		for (Context context : current) {
			logger.debug(">" + context.getName() + "<");
		}

		for (int modelSize = 1;modelSize<100;modelSize++){
			boolean isAchievable = false;
			for (int i = 0; i < 100; i++) {
				CGM cgm = cgmFactory.generateCGM(2*modelSize, 2);
				if (cgm.getRoot().isAchievable(current, null) != null)
					isAchievable = true;
			}
			assertTrue(isAchievable);
		}

	}

	@Test
	public void shouldCreateASingleCGMWithTwoLevels() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		CGM cgm = cgmFactory.generateCGM(3, 2);

		assertEquals(3, countRefinements(cgm.getRoot()));
	}

	@Test
	public void shouldCreateLargerCGMs() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		for (int i = 1; i < 100; i++) {
			CGM cgm = cgmFactory.generateCGM(3 * i, 1);
			assertEquals(3 * i, countRefinements(cgm.getRoot()));
		}
	}

	@Test
	public void shouldCreateAVeryLargeCGM() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		CGM cgm = cgmFactory.generateCGM(3000, 1);
		assertEquals(3000, countRefinements(cgm.getRoot()));
	}

	@Test
	public void shouldCreateCGMWithManyContexts() {
		RandomCGMGenerator cgmFactory = new RandomCGMGenerator();

		boolean successHistory = false;
		for (int i = 0; i < 100; i++) {
			CGM cgm = cgmFactory.generateCGM(1000, 30);

			if (30 == collectContexts(cgm.getRoot()).size()) {
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

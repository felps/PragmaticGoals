package cgm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cgm.util.generator.RandomCGMGenerator;
import cgm.util.generator.RandomCGMGeneratorTest;
import cgm.util.generator.WorstCaseCGMGeneratorTest;

@RunWith(Suite.class)
@SuiteClasses({CGMTest.class, GoalTest.class, PragmaticGoalTest.class, QualityConstraintTest.class,
		RefinementTest.class, TaskTest.class, ContextTest.class, TestEachContext.class, RandomCGMGeneratorTest.class,
		WorstCaseCGMGeneratorTest.class, InterpretationTest.class})
public class CgmFullTest {

}

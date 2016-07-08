package cgm;

import cgm.util.generator.RandomCGMGeneratorTest;
import cgm.util.generator.WorstCaseCGMGeneratorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CGMTest.class, GoalTest.class, PragmaticGoalTest.class, FilterQualityConstraintTest.class,
		RefinementTest.class, TaskTest.class, ContextTest.class, /*TestEachContext.class, */RandomCGMGeneratorTest.class,
		WorstCaseCGMGeneratorTest.class, InterpretationTest.class, FilterQualityConstraintTest.class})
public class CgmFullTest {

}

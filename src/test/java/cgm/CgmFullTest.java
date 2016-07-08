package cgm;

import cgm.util.generator.RandomCGMGeneratorTest;
import cgm.util.generator.WorstCaseCGMGeneratorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CGMTest.class, ContextTest.class, FilterQualityConstraintTest.class, GoalTest.class, InterpretationTest.class,
        PlanTest.class, PragmaticGoalTest.class, RefinementTest.class, TaskTest.class,  /*TestEachContext.class, */
        RandomCGMGeneratorTest.class, WorstCaseCGMGeneratorTest.class})
public class CgmFullTest {

}

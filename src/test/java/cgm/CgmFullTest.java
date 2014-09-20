package cgm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CGMTest.class, GoalTest.class, PragmaticGoalTest.class,
		QualityConstraintTest.class, RefinementTest.class, TaskTest.class })
public class CgmFullTest {

}

package cgm;

import cgm.runtime.annotations.AlternativeAnnotationTest;
import cgm.runtime.annotations.CardinalityAnnotationTest;
import cgm.runtime.annotations.InterleavedAnnotationTest;
import cgm.runtime.annotations.SequentialAnnotationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CGMTest.class, ContextTest.class, FilterQualityConstraintTest.class, GoalTest.class, InterpretationTest.class,
        PlanTest.class, PragmaticGoalTest.class, RefinementTest.class, TaskTest.class, SequentialAnnotationTest.class,
        InterleavedAnnotationTest.class, AlternativeAnnotationTest.class, CardinalityAnnotationTest.class
        /*TestEachContext.class, */})
public class CgmFullTest {

}

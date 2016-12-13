package br.ime.usp.improv.pragmatic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ime.usp.improv.pragmatic.runtime.annotations.AlternativeAnnotationTest;
import br.ime.usp.improv.pragmatic.runtime.annotations.CardinalityAnnotationTest;
import br.ime.usp.improv.pragmatic.runtime.annotations.InterleavedAnnotationTest;
import br.ime.usp.improv.pragmatic.runtime.annotations.SequentialAnnotationTest;

@RunWith(Suite.class)
@SuiteClasses({CGMTest.class, ContextTest.class, FilterQualityConstraintTest.class, GoalTest.class, InterpretationTest.class,
        PlanTest.class, PragmaticGoalTest.class, RefinementTest.class, TaskTest.class, SequentialAnnotationTest.class,
        InterleavedAnnotationTest.class, AlternativeAnnotationTest.class, CardinalityAnnotationTest.class
        /*TestEachContext.class, */})
public class CgmFullTest {

}

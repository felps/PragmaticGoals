package cgm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cgm.util.generator.RandomCGMGeneratorTest;
import cgm.util.generator.WorstCaseCGMGeneratorTest;
import cgm.util.generator.workflow.WorkflowGenerationTest;
import cgm.util.generator.workflow.WorkflowNodesTest;
import cgm.util.generator.workflow.WorkflowTest;

@RunWith(Suite.class)
@SuiteClasses({CGMTest.class, GoalTest.class, PragmaticGoalTest.class, QualityConstraintTest.class,
		RefinementTest.class, TaskTest.class, ContextTest.class, TestEachContext.class, RandomCGMGeneratorTest.class,
		WorstCaseCGMGeneratorTest.class, InterpretationTest.class, WorkflowGenerationTest.class,
		WorkflowNodesTest.class, WorkflowTest.class})
public class CgmFullTest {

}

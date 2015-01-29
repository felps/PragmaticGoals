package cgm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import workflow.WorkflowGenerationTest;
import workflow.datatypes.WorkflowNodesTest;
import workflow.datatypes.WorkflowTest;
import cgm.util.generator.RandomCGMGeneratorTest;
import cgm.util.generator.WorstCaseCGMGeneratorTest;

@RunWith(Suite.class)
@SuiteClasses({CGMTest.class, GoalTest.class, PragmaticGoalTest.class, QualityConstraintTest.class,
		RefinementTest.class, TaskTest.class, ContextTest.class, RandomCGMGeneratorTest.class,
		WorstCaseCGMGeneratorTest.class, InterpretationTest.class, WorkflowGenerationTest.class,
		WorkflowNodesTest.class, WorkflowTest.class, PlanTest.class})
public class CgmFullTest {

}

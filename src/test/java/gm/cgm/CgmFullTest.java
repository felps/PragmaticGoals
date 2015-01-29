package gm.cgm;

import gm.cgm.util.generator.RandomCGMGeneratorTest;
import gm.cgm.util.generator.WorstCaseCGMGeneratorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import workflow.WorkflowGenerationTest;
import workflow.datatypes.WorkflowNodesTest;
import workflow.datatypes.WorkflowTest;

@RunWith(Suite.class)
@SuiteClasses({CGMTest.class, GoalTest.class, PragmaticGoalTest.class, QualityConstraintTest.class,
		RefinementTest.class, TaskTest.class, ContextTest.class, RandomCGMGeneratorTest.class,
		WorstCaseCGMGeneratorTest.class, InterpretationTest.class, WorkflowGenerationTest.class,
		WorkflowNodesTest.class, WorkflowTest.class, PlanTest.class})
public class CgmFullTest {

}

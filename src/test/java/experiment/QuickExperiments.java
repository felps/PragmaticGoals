package experiment;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ExperimentScenarios.class, ExperimentUnB.class, HumanMachineComparison.class, SmallExperiment.class})
public class QuickExperiments {

}

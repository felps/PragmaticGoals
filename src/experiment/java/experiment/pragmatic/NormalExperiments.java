package experiment.pragmatic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Felipe on 13/07/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ContextSweep.class, ContextSweepFull.class, ContextSweepUnlimited.class, ExperimentScenarios.class,
        ExperimentUnB.class, HumanMachineComparison.class, SmallExperiment.class})

public class NormalExperiments {
}

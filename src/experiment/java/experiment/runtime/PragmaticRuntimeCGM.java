package experiment.runtime;

import cgm.Comparison;
import cgm.Goal;
import cgm.Interpretation;
import cgm.Task;
import cgm.metrics.Metric;
import cgm.quality.CompositeQualityConstraint;
import cgm.runtime.annotations.SequentialAnnotation;
import org.junit.Test;

/**
 * Created by Felipe on 07/07/2016.
 */
public class PragmaticRuntimeCGM {
    @Test
    public void achievableSequentialAND() throws Exception {
        Task t1 = new Task();
        t1.setIdentifier("T1");
        t1.setTimeConsumed(120);
        t1.setReliability(0.99);

        Task t2 = new Task();
        t2.setIdentifier("T2");
        t2.setTimeConsumed(120);
        t2.setReliability(0.99);

        Goal root = new Goal(Goal.AND);
        root.setIdentifier("root");
        root.setRuntimeAnnotation(new SequentialAnnotation());
        root.addDependency(t1);
        root.addDependency(t2);

        CompositeQualityConstraint compQC = new CompositeQualityConstraint(Metric.TIME, null, Comparison.LESS_OR_EQUAL_TO, 240);

        Interpretation interp = new Interpretation();
        interp.addCompositeQualityConstraint(compQC);

        root.isAchievable(null, interp);

    }

    @Test
    public void unachievableSequentialAND() {

    }

    @Test
    public void unachievableParallelAND() {

    }

    @Test
    public void achievableParallelOR() {

    }

    @Test
    public void unachievableParallelOR() {

    }

    @Test
    public void achievableAlternativeOR() {

    }

    @Test
    public void unachievableAlternativeOR() {

    }


}

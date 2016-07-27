package experiment.pragmatic;

import pragmatic.*;
import pragmatic.metrics.Metric;
import pragmatic.quality.FilterQualityConstraint;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class SmallExperiment {

	CGM cgm;

	/* Contexts */
	Context c2 = new Context("c2");
	Context c5 = new Context("c5");
	Context c9 = new Context("c9");
	Context c10 = new Context("c10");

	/* Goals */
	Pragmatic locationIsIdentifiedGoal = new Pragmatic(Goal.OR);

	/* Tasks */
	Task considerLastKnownLocationTask = new Task();
	Task identifyLocationByVoiceCallTask = new Task();
	Task accessLocationFromTriangulationTask = new Task();
	Task accessLocationFromGPSTask = new Task();

	@Before
	public void setUp() {
		cgm = new CGM();

		cgm.setRoot(locationIsIdentifiedGoal);

		/* Refinements */

		locationIsIdentifiedGoal.addDependency(considerLastKnownLocationTask);
		locationIsIdentifiedGoal.addDependency(identifyLocationByVoiceCallTask);
		locationIsIdentifiedGoal.addDependency(accessLocationFromGPSTask);
		locationIsIdentifiedGoal.addDependency(accessLocationFromTriangulationTask);

        FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.SECONDS, 150, Comparison.LESS_THAN);
        FilterQualityConstraint qc2 = new FilterQualityConstraint(c10, Metric.SECONDS, 60, Comparison.LESS_THAN);
        // FilterQualityConstraint qc3 = new FilterQualityConstraint(c9, FilterMetric.SECONDS,
        // 1800, Comparison.LESS_THAN);
        // FilterQualityConstraint qc4 = new FilterQualityConstraint(null, FilterMetric.SECONDS,
        // 900, Comparison.LESS_THAN);
        // FilterQualityConstraint qc5 = new FilterQualityConstraint(c10, FilterMetric.SECONDS,
        // 600, Comparison.LESS_THAN);
        // FilterQualityConstraint qc6 = new FilterQualityConstraint(c9, FilterMetric.SECONDS,
        // 1800, Comparison.LESS_THAN);
        locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc1);
        locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc2);

        // locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc3);
        // locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc4);
        // locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc5);
        // locationIsIdentifiedGoal.getInterpretatioln().addFilterQualityConstraint(qc6);

		identifyLocationByVoiceCallTask.addApplicableContext(c2);
		accessLocationFromTriangulationTask.addApplicableContext(c2);
		accessLocationFromGPSTask.addApplicableContext(c5);

        considerLastKnownLocationTask.setProvidedQuality(null, Metric.SECONDS, 120);
        identifyLocationByVoiceCallTask.setProvidedQuality(c2, Metric.SECONDS, 90);
        accessLocationFromTriangulationTask.setProvidedQuality(c2, Metric.SECONDS, 120);
        accessLocationFromGPSTask.setProvidedQuality(c5, Metric.SECONDS, 50);
    }

	@Test
	public void findAchievableScenarios() {
		HashSet<Context> fullContext;

		for (int t2 = 0; t2 < 2; t2++) {
			for (int t5 = 0; t5 < 2; t5++) {
				for (int t9 = 0; t9 < 2; t9++) {
					for (int t10 = 0; t10 < 2; t10++) {
						fullContext = createFullContext(t2, t5, t9, t10);
						if (cgm.isAchievable(fullContext, locationIsIdentifiedGoal.getInterpretation()) != null) {
							System.out.println("Achievable");
						} else
							System.out.println("Not achievable");
					}
				}
			}
		}
	}

	private HashSet<Context> createFullContext(int t2, int t5, int t9, int t10) {
		HashSet<Context> fullContext = new HashSet<Context>();

		System.out.print("Contexto: [");
		if (t2 == 0) {
			fullContext.add(c2);
			System.out.print("c2 ");
		}
		if (t5 == 0) {
			fullContext.add(c5);
			System.out.print("c5 ");
		}
		if (t9 == 0) {
			fullContext.add(c9);
			System.out.print("c9 ");
		}
		if (t10 == 0) {
			fullContext.add(c10);
			System.out.print("c10 ");
		}

		System.out.println("]");
		return fullContext;
	}

}

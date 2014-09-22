package experiment;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import cgm.CGM;
import cgm.Comparison;
import cgm.Context;
import cgm.Goal;
import cgm.Metric;
import cgm.Pragmatic;
import cgm.QualityConstraint;
import cgm.Task;

public class ExperimentUnB {

	CGM cgm;

	/* Contexts */
	Context c1 = new Context("c1");
	Context c2 = new Context("c2");
	Context c3 = new Context("c3");
	Context c4 = new Context("c4");
	Context c5 = new Context("c5");
	Context c6 = new Context("c6");
	Context c7 = new Context("c7");
	Context c8 = new Context("c8");
	Context c9 = new Context("c9");
	Context c10 = new Context("c10");
	Context c11 = new Context("c11");
	Context c12 = new Context("c12");
	Context c13 = new Context("c13");

	@Before
	public void setUp() {
		cgm = new CGM();

		/* Goals */
		Pragmatic respondToEmergencyGoal = new Pragmatic(Goal.AND);
		Pragmatic emergencyIsDetectedGoal = new Pragmatic(Goal.OR);
		Pragmatic centralReceivesInfoGoal = new Pragmatic(Goal.AND);
		Pragmatic locationIsIdentifiedGoal = new Pragmatic(Goal.OR);
		Pragmatic infoIsPreparedGoal = new Pragmatic(Goal.OR);
		Pragmatic isNotifiedAboutEmergencyGoal = new Pragmatic(Goal.OR);
		Goal callForHelpIsAcceptedGoal = new Goal(Goal.AND);
		Goal falseAlarmIsCheckedGoal = new Goal(Goal.OR);
		Goal pIsContacted = new Goal(Goal.AND);
		Goal receivesEmergencyButtonCallGoal = new Goal(Goal.OR);
		Goal situationsAreIdentifiedGoal = new Goal(Goal.AND);
		Goal vitalSignsAreMonitoredGoal = new Goal(Goal.AND);
		Goal infoIsSentToEmergencyGoal = new Goal(Goal.OR);
		Goal setupAutomatedInfoGoal = new Goal(Goal.AND);
		Goal situationDataIsRecoveredGoal = new Goal(Goal.AND);
		Goal contactResponsibleGoal = new Goal(Goal.AND);
		Goal medicalCareReachesGoal = new Goal(Goal.AND);
		Goal ambulanceIsDispatchedToLocationGoal = new Goal(Goal.AND);

		/* Tasks */
		Task notifyCentralBySMSTask = new Task();
		Task notifyCentralByInternetTask = new Task();
		Task acceptEmergencyTask = new Task();
		Task confirmEmergencyByCallTask = new Task();
		Task processDataFromSensorsTask = new Task();
		Task identifySituationTask = new Task();
		Task collectDataFromSensorsTask = new Task();
		Task persistDataToDatabaseTask = new Task();
		Task notifyByMobileVibrationTask = new Task();
		Task notifyBySoundAlertTask = new Task();
		Task notifyByLightAlertTask = new Task();
		Task centralCallTask = new Task();
		Task sendInfoBySMSTask = new Task();
		Task sendInfoByInternetTask = new Task();
		Task considerLastKnownLocationTask = new Task();
		Task identifyLocationByVoiceCallTask = new Task();
		Task accessLocationFromTriangulationTask = new Task();
		Task accessLocationFromGPSTask = new Task();
		Task accessDataFromDatabaseTask = new Task();
		Task getInfoFromResponsibleTask = new Task();
		Task ambulanceDispatchDelegation = new Task();

		cgm.setRoot(respondToEmergencyGoal);

		/* Refinements */

		respondToEmergencyGoal.addDependency(emergencyIsDetectedGoal);
		respondToEmergencyGoal.addDependency(isNotifiedAboutEmergencyGoal);
		respondToEmergencyGoal.addDependency(centralReceivesInfoGoal);
		respondToEmergencyGoal.addDependency(medicalCareReachesGoal);

		emergencyIsDetectedGoal.addDependency(callForHelpIsAcceptedGoal);
		emergencyIsDetectedGoal.addDependency(situationsAreIdentifiedGoal);

		callForHelpIsAcceptedGoal.addDependency(receivesEmergencyButtonCallGoal);
		callForHelpIsAcceptedGoal.addDependency(falseAlarmIsCheckedGoal);

		receivesEmergencyButtonCallGoal.addDependency(notifyCentralBySMSTask);
		receivesEmergencyButtonCallGoal.addDependency(notifyCentralByInternetTask);

		falseAlarmIsCheckedGoal.addDependency(acceptEmergencyTask);
		falseAlarmIsCheckedGoal.addDependency(pIsContacted);

		pIsContacted.addDependency(confirmEmergencyByCallTask);

		situationsAreIdentifiedGoal.addDependency(processDataFromSensorsTask);
		situationsAreIdentifiedGoal.addDependency(vitalSignsAreMonitoredGoal);
		situationsAreIdentifiedGoal.addDependency(identifySituationTask);

		vitalSignsAreMonitoredGoal.addDependency(collectDataFromSensorsTask);
		vitalSignsAreMonitoredGoal.addDependency(persistDataToDatabaseTask);

		isNotifiedAboutEmergencyGoal.addDependency(notifyByMobileVibrationTask);
		isNotifiedAboutEmergencyGoal.addDependency(notifyBySoundAlertTask);
		isNotifiedAboutEmergencyGoal.addDependency(notifyByLightAlertTask);
		isNotifiedAboutEmergencyGoal.addDependency(centralCallTask);

		centralReceivesInfoGoal.addDependency(infoIsSentToEmergencyGoal);
		centralReceivesInfoGoal.addDependency(infoIsPreparedGoal);

		infoIsSentToEmergencyGoal.addDependency(sendInfoBySMSTask);
		infoIsSentToEmergencyGoal.addDependency(sendInfoByInternetTask);

		infoIsPreparedGoal.addDependency(setupAutomatedInfoGoal);
		infoIsPreparedGoal.addDependency(contactResponsibleGoal);

		setupAutomatedInfoGoal.addDependency(locationIsIdentifiedGoal);
		setupAutomatedInfoGoal.addDependency(situationDataIsRecoveredGoal);

		locationIsIdentifiedGoal.addDependency(considerLastKnownLocationTask);
		locationIsIdentifiedGoal.addDependency(identifyLocationByVoiceCallTask);
		locationIsIdentifiedGoal.addDependency(accessLocationFromGPSTask);
		locationIsIdentifiedGoal.addDependency(accessLocationFromTriangulationTask);

		situationDataIsRecoveredGoal.addDependency(accessDataFromDatabaseTask);

		contactResponsibleGoal.addDependency(getInfoFromResponsibleTask);

		ambulanceIsDispatchedToLocationGoal.addDependency(ambulanceDispatchDelegation);

		/* Applicable Contexts */
		
		notifyCentralBySMSTask.addApplicableContext(c2);
		
		notifyCentralByInternetTask.addApplicableContext(c3);
		notifyCentralByInternetTask.addApplicableContext(c4);
		
		acceptEmergencyTask.addNonApplicableContext(c2);
		
		confirmEmergencyByCallTask.addApplicableContext(c2);
		
		notifyByMobileVibrationTask.addApplicableContext(c1);
		
		notifyBySoundAlertTask.addApplicableContext(c6);
		
		notifyByLightAlertTask.addApplicableContext(c7);
		
		centralCallTask.addApplicableContext(c8);
		
		sendInfoBySMSTask.addApplicableContext(c2);
		
		sendInfoByInternetTask.addApplicableContext(c3);
		sendInfoByInternetTask.addApplicableContext(c4);
		
		identifyLocationByVoiceCallTask.addApplicableContext(c2);
		
		accessLocationFromTriangulationTask.addApplicableContext(c2);
		
		accessLocationFromGPSTask.addApplicableContext(c5);
		
		
		
		
		/* Goal interpretations */
		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_THAN);
			QualityConstraint qc3 = new QualityConstraint(c9, Metric.SECONDS, 1800, Comparison.LESS_THAN);
			respondToEmergencyGoal.getInterpretation().addQualityConstraint(qc1);
			respondToEmergencyGoal.getInterpretation().addQualityConstraint(qc2);
			respondToEmergencyGoal.getInterpretation().addQualityConstraint(qc3);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 80,
					Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c3, Metric.FALSE_NEGATIVE_PERCENTAGE, 95,
					Comparison.LESS_THAN);
			emergencyIsDetectedGoal.getInterpretation().addQualityConstraint(qc1);
			emergencyIsDetectedGoal.getInterpretation().addQualityConstraint(qc2);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 60, Comparison.LESS_THAN);
			centralReceivesInfoGoal.getInterpretation().addQualityConstraint(qc1);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_THAN);
			QualityConstraint qc3 = new QualityConstraint(c9, Metric.SECONDS, 1800, Comparison.LESS_THAN);
			QualityConstraint qc4 = new QualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_THAN);
			QualityConstraint qc5 = new QualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_THAN);
			QualityConstraint qc6 = new QualityConstraint(c9, Metric.SECONDS, 1800, Comparison.LESS_THAN);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc1);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc2);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc3);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc4);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc5);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc6);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_THAN);
			infoIsPreparedGoal.getInterpretation().addQualityConstraint(qc1);
			infoIsPreparedGoal.getInterpretation().addQualityConstraint(qc2);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_THAN);
			isNotifiedAboutEmergencyGoal.getInterpretation().addQualityConstraint(qc1);
			isNotifiedAboutEmergencyGoal.getInterpretation().addQualityConstraint(qc2);
		}
	}

	@Test
	public void findAchievableScenarios() {
		HashSet<Context> fullContext;

		for (int t1 = 0; t1 < 2; t1++) {
			for (int t2 = 0; t2 < 2; t2++) {
				for (int t3 = 0; t3 < 2; t3++) {
					for (int t4 = 0; t4 < 2; t4++) {
						for (int t5 = 0; t5 < 2; t5++) {
							for (int t6 = 0; t6 < 2; t6++) {
								for (int t7 = 0; t7 < 2; t7++) {
									for (int t8 = 0; t8 < 2; t8++) {
										for (int t9 = 0; t9 < 2; t9++) {
											for (int t10 = 0; t10 < 2; t10++) {
												for (int t11 = 0; t11 < 2; t11++) {
													for (int t12 = 0; t12 < 2; t12++) {
														for (int t13 = 0; t13 < 2; t13++) {
															fullContext = createFullContext(t1, t2, t3, t4, t5, t6, t7,
																	t8, t9, t10, t11, t12, t13);
															if (cgm.isAchievable(fullContext, null) != null) {
																System.out.println("Achievable");
															} else
																System.out.println("Not achievable");
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private HashSet<Context> createFullContext(int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8, int t9,
			int t10, int t11, int t12, int t13) {
		HashSet<Context> fullContext = new HashSet<Context>();

		System.out.print("Contexto: [");
		if (t1 == 0) {
			fullContext.add(c1);
			System.out.print("c1 ");
		}
		if (t2 == 0) {
			fullContext.add(c2);
			System.out.print("c2 ");
		}
		if (t3 == 0) {
			fullContext.add(c3);
			System.out.print("c3 ");
		}
		if (t4 == 0) {
			fullContext.add(c4);
			System.out.print("c4 ");
		}
		if (t5 == 0) {
			fullContext.add(c5);
			System.out.print("c5 ");
		}
		if (t6 == 0) {
			fullContext.add(c6);
			System.out.print("c6 ");
		}
		if (t7 == 0) {
			fullContext.add(c7);
			System.out.print("c7 ");
		}
		if (t8 == 0) {
			fullContext.add(c8);
			System.out.print("c8 ");
		}
		if (t9 == 0) {
			fullContext.add(c9);
			System.out.print("c9 ");
		}
		if (t10 == 0) {
			fullContext.add(c10);
			System.out.print("c10 ");
		}
		if (t11 == 0) {
			fullContext.add(c11);
			System.out.print("c11 ");
		}
		if (t12 == 0) {
			fullContext.add(c12);
			System.out.print("c12 ");
		}
		if (t13 == 0) {
			fullContext.add(c13);
			System.out.print("c13 ");
		}
		System.out.println("]");
		return fullContext;
	}

}

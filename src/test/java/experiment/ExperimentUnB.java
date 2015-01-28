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

	// Context c13 = new Context("c13");

	@Before
	public void setUp() {
		cgm = new CGM();

		/* Goals */
		Pragmatic respondToEmergencyGoal = new Pragmatic(Goal.PARALLEL_AND_DECOMPOSITION);
		Pragmatic emergencyIsDetectedGoal = new Pragmatic(Goal.OR_DECOMPOSITION);
		Pragmatic centralReceivesInfoGoal = new Pragmatic(Goal.PARALLEL_AND_DECOMPOSITION);
		Pragmatic locationIsIdentifiedGoal = new Pragmatic(Goal.OR_DECOMPOSITION);
		Pragmatic infoIsPreparedGoal = new Pragmatic(Goal.OR_DECOMPOSITION);
		Pragmatic isNotifiedAboutEmergencyGoal = new Pragmatic(Goal.OR_DECOMPOSITION);
		Goal callForHelpIsAcceptedGoal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Goal falseAlarmIsCheckedGoal = new Goal(Goal.OR_DECOMPOSITION);
		Goal pIsContacted = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Goal receivesEmergencyButtonCallGoal = new Goal(Goal.OR_DECOMPOSITION);
		Goal situationsAreIdentifiedGoal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Goal vitalSignsAreMonitoredGoal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Goal infoIsSentToEmergencyGoal = new Goal(Goal.OR_DECOMPOSITION);
		Goal setupAutomatedInfoGoal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Goal situationDataIsRecoveredGoal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Goal contactResponsibleGoal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Goal medicalCareReachesGoal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);
		Goal ambulanceIsDispatchedToLocationGoal = new Goal(Goal.PARALLEL_AND_DECOMPOSITION);

		/* Tasks */
		Task notifyCentralBySMSTask = new Task();
		notifyCentralBySMSTask.setIdentifier("notifyCentralBySMS");

		Task notifyCentralByInternetTask = new Task();
		notifyCentralByInternetTask.setIdentifier("notifyCentralByInternet");

		Task acceptEmergencyTask = new Task();
		acceptEmergencyTask.setIdentifier("acceptEmergency");

		Task confirmEmergencyByCallTask = new Task();
		confirmEmergencyByCallTask.setIdentifier("confirmEmergencyByCall");

		Task processDataFromSensorsTask = new Task();
		processDataFromSensorsTask.setIdentifier("processDataFromSensors");

		Task identifySituationTask = new Task();
		identifySituationTask.setIdentifier("identifySituationTask");

		Task collectDataFromSensorsTask = new Task();
		collectDataFromSensorsTask.setIdentifier("collectDataFromSensorsTask");

		Task persistDataToDatabaseTask = new Task();
		persistDataToDatabaseTask.setIdentifier("persistDataToDatabaseTask");

		Task notifyByMobileVibrationTask = new Task();
		notifyByMobileVibrationTask.setIdentifier("notifyByMobileVibrationTask");

		Task notifyBySoundAlertTask = new Task();
		notifyBySoundAlertTask.setIdentifier("notifyBySoundAlert");

		Task notifyByLightAlertTask = new Task();
		notifyByLightAlertTask.setIdentifier("notifyByLightAlert");

		Task centralCallTask = new Task();
		centralCallTask.setIdentifier("centralCallsP");

		Task sendInfoBySMSTask = new Task();
		sendInfoBySMSTask.setIdentifier("sendInfoBySMS");

		Task sendInfoByInternetTask = new Task();
		sendInfoByInternetTask.setIdentifier("sendInfoByInternet");

		Task considerLastKnownLocationTask = new Task();
		considerLastKnownLocationTask.setIdentifier("considerLastKnownLocation");

		Task identifyLocationByVoiceCallTask = new Task();
		identifyLocationByVoiceCallTask.setIdentifier("identifyLocationByVoiceCall");

		Task accessLocationFromTriangulationTask = new Task();
		accessLocationFromTriangulationTask.setIdentifier("accessLocationFromTriangulation");

		Task accessLocationFromGPSTask = new Task();
		accessLocationFromGPSTask.setIdentifier("accessLocationFromGPS");

		Task accessDataFromDatabaseTask = new Task();
		accessDataFromDatabaseTask.setIdentifier("accessDataFromDatabase");

		Task getInfoFromResponsibleTask = new Task();
		getInfoFromResponsibleTask.setIdentifier("getInfoFromResponsible");

		Task ambulanceDispatchDelegation = new Task();
		ambulanceDispatchDelegation.setIdentifier("ambulanceDispatchDelegation");

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

		medicalCareReachesGoal.addDependency(ambulanceIsDispatchedToLocationGoal);

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
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 180, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c10, Metric.SECONDS, 90, Comparison.LESS_THAN);
			QualityConstraint qc3 = new QualityConstraint(c9, Metric.SECONDS, 240, Comparison.LESS_THAN);
			respondToEmergencyGoal.getInterpretation().addQualityConstraint(qc1);
			respondToEmergencyGoal.getInterpretation().addQualityConstraint(qc2);
			respondToEmergencyGoal.getInterpretation().addQualityConstraint(qc3);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 30,
					Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c3, Metric.FALSE_NEGATIVE_PERCENTAGE, 10,
					Comparison.LESS_THAN);
			QualityConstraint qc3 = new QualityConstraint(c9, Metric.FALSE_NEGATIVE_PERCENTAGE, 5, Comparison.LESS_THAN);
			emergencyIsDetectedGoal.getInterpretation().addQualityConstraint(qc1);
			emergencyIsDetectedGoal.getInterpretation().addQualityConstraint(qc2);
			emergencyIsDetectedGoal.getInterpretation().addQualityConstraint(qc3);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 60, Comparison.LESS_THAN);
			centralReceivesInfoGoal.getInterpretation().addQualityConstraint(qc1);
		}

		{
			QualityConstraint qc4 = new QualityConstraint(null, Metric.DISTANCE_ERROR, 1000, Comparison.LESS_THAN);
			QualityConstraint qc6 = new QualityConstraint(c5, Metric.DISTANCE_ERROR, 20, Comparison.LESS_THAN);
			QualityConstraint qc5 = new QualityConstraint(c10, Metric.DISTANCE_ERROR, 200, Comparison.LESS_THAN);
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 120, Comparison.LESS_THAN);
			QualityConstraint qc3 = new QualityConstraint(c9, Metric.SECONDS, 240, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c10, Metric.SECONDS, 20, Comparison.LESS_THAN);
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
			QualityConstraint qc1 = new QualityConstraint(null, Metric.NOISE, 10, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c1, Metric.NOISE, 3, Comparison.LESS_THAN);
			isNotifiedAboutEmergencyGoal.getInterpretation().addQualityConstraint(qc1);
			isNotifiedAboutEmergencyGoal.getInterpretation().addQualityConstraint(qc2);
		}

		/* Provided Task QoS */
		notifyCentralBySMSTask.setProvidedQuality(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 10);

		notifyCentralByInternetTask.setProvidedQuality(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 5);

		acceptEmergencyTask.setProvidedQuality(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 30);

		confirmEmergencyByCallTask.setProvidedQuality(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 5);

		processDataFromSensorsTask.setProvidedQuality(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 15);

		collectDataFromSensorsTask.setProvidedQuality(null, Metric.SECONDS, 120);
		collectDataFromSensorsTask.setProvidedQuality(c3, Metric.SECONDS, 60);

		persistDataToDatabaseTask.setProvidedQuality(null, Metric.SECONDS, 5);

		identifySituationTask.setProvidedQuality(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 20);

		notifyByMobileVibrationTask.setProvidedQuality(null, Metric.NOISE, 2);
		notifyBySoundAlertTask.setProvidedQuality(null, Metric.NOISE, 9);
		notifyByLightAlertTask.setProvidedQuality(null, Metric.NOISE, 0);
		centralCallTask.setProvidedQuality(null, Metric.NOISE, 7);

		sendInfoBySMSTask.setProvidedQuality(null, Metric.SECONDS, 65);
		sendInfoBySMSTask.setProvidedQuality(c8, Metric.SECONDS, 45);

		sendInfoByInternetTask.setProvidedQuality(null, Metric.SECONDS, 40);

		considerLastKnownLocationTask.setProvidedQuality(null, Metric.DISTANCE_ERROR, 900);
		considerLastKnownLocationTask.setProvidedQuality(null, Metric.SECONDS, 15);

		identifyLocationByVoiceCallTask.setProvidedQuality(null, Metric.DISTANCE_ERROR, 100);
		identifyLocationByVoiceCallTask.setProvidedQuality(c11, Metric.DISTANCE_ERROR, 300);
		identifyLocationByVoiceCallTask.setProvidedQuality(null, Metric.SECONDS, 45);

		accessLocationFromTriangulationTask.setProvidedQuality(null, Metric.DISTANCE_ERROR, 40);
		accessLocationFromTriangulationTask.setProvidedQuality(c11, Metric.DISTANCE_ERROR, 400);
		accessLocationFromTriangulationTask.setProvidedQuality(null, Metric.SECONDS, 30);

		accessLocationFromGPSTask.setProvidedQuality(null, Metric.DISTANCE_ERROR, 20);
		accessLocationFromGPSTask.setProvidedQuality(c11, Metric.DISTANCE_ERROR, 30);
		accessLocationFromGPSTask.setProvidedQuality(null, Metric.SECONDS, 50);

		accessDataFromDatabaseTask.setProvidedQuality(null, Metric.SECONDS, 20);

		getInfoFromResponsibleTask.setProvidedQuality(null, Metric.SECONDS, 25);
		getInfoFromResponsibleTask.setProvidedQuality(c11, Metric.SECONDS, 50);

		ambulanceDispatchDelegation.setProvidedQuality(null, Metric.SECONDS, 30);
	}

	@Test
	public void findAchievableScenarios() {
		HashSet<Context> fullContext;
		long startTimeMs = System.currentTimeMillis();
		int count = 0;
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
														fullContext = createFullContext(t1, t2, t3, t4, t5, t6, t7, t8,
																t9, t10, t11, t12);
														count++;
														if (cgm.isAchievable(fullContext, null) != null) {
															System.out.println("Achievable");
															System.out.print("[");
															for (Task task : cgm.isAchievable(fullContext, null)
																	.getTasks()) {
																System.out.print(task.getIdentifier() + " ");
															}
															System.out.println("]");
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

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - startTimeMs) + "ms for " + count
				+ " context sets.");
	}

	private HashSet<Context> createFullContext(int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8, int t9,
			int t10, int t11, int t12) {
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
		System.out.println("]");
		return fullContext;
	}

}

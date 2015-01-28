package cgm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import metrics.DistanceErrorMargin;
import metrics.EnvironmentNoise;
import metrics.ExecutionTimeSec;
import metrics.FalseNegativePercentage;
import org.junit.Before;
import org.junit.Test;

public class TestEachContext {
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
			QualityConstraint qc1 = new QualityConstraint(null, (new ExecutionTimeSec()), 180, Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc2 = new QualityConstraint(c10, (new ExecutionTimeSec()), 90, Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc3 = new QualityConstraint(c9, (new ExecutionTimeSec()), 240, Comparison.LESS_OR_EQUAL_TO);
			respondToEmergencyGoal.getInterpretation().addQualityConstraint(qc1);
			respondToEmergencyGoal.getInterpretation().addQualityConstraint(qc2);
			respondToEmergencyGoal.getInterpretation().addQualityConstraint(qc3);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, (new FalseNegativePercentage()), 30,
					Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc2 = new QualityConstraint(c3, (new FalseNegativePercentage()), 10,
					Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc3 = new QualityConstraint(c9, (new FalseNegativePercentage()), 5,
					Comparison.LESS_OR_EQUAL_TO);
			emergencyIsDetectedGoal.getInterpretation().addQualityConstraint(qc1);
			emergencyIsDetectedGoal.getInterpretation().addQualityConstraint(qc2);
			emergencyIsDetectedGoal.getInterpretation().addQualityConstraint(qc3);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, (new ExecutionTimeSec()), 60, Comparison.LESS_OR_EQUAL_TO);
			centralReceivesInfoGoal.getInterpretation().addQualityConstraint(qc1);
		}

		{
			QualityConstraint qc4 = new QualityConstraint(null, (new DistanceErrorMargin()), 1000,
					Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc6 = new QualityConstraint(c5, (new DistanceErrorMargin()), 20, Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc5 = new QualityConstraint(c10, (new DistanceErrorMargin()), 200, Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc1 = new QualityConstraint(null, (new ExecutionTimeSec()), 120, Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc3 = new QualityConstraint(c9, (new ExecutionTimeSec()), 240, Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc2 = new QualityConstraint(c10, (new ExecutionTimeSec()), 20, Comparison.LESS_OR_EQUAL_TO);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc1);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc2);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc3);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc4);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc5);
			locationIsIdentifiedGoal.getInterpretation().addQualityConstraint(qc6);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, (new ExecutionTimeSec()), 900, Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc2 = new QualityConstraint(c10, (new ExecutionTimeSec()), 600, Comparison.LESS_OR_EQUAL_TO);
			infoIsPreparedGoal.getInterpretation().addQualityConstraint(qc1);
			infoIsPreparedGoal.getInterpretation().addQualityConstraint(qc2);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, (new EnvironmentNoise()), 10, Comparison.LESS_OR_EQUAL_TO);
			QualityConstraint qc2 = new QualityConstraint(c1, (new EnvironmentNoise()), 3, Comparison.LESS_OR_EQUAL_TO);
			isNotifiedAboutEmergencyGoal.getInterpretation().addQualityConstraint(qc1);
			isNotifiedAboutEmergencyGoal.getInterpretation().addQualityConstraint(qc2);
		}

		/* Provided Task QoS */
		notifyCentralBySMSTask.setProvidedQuality(null, (new FalseNegativePercentage()), 10);

		notifyCentralByInternetTask.setProvidedQuality(null, (new FalseNegativePercentage()), 5);

		acceptEmergencyTask.setProvidedQuality(null, (new FalseNegativePercentage()), 30);

		confirmEmergencyByCallTask.setProvidedQuality(null, (new FalseNegativePercentage()), 5);

		processDataFromSensorsTask.setProvidedQuality(null, (new FalseNegativePercentage()), 15);

		collectDataFromSensorsTask.setProvidedQuality(null, (new ExecutionTimeSec()), 120);
		collectDataFromSensorsTask.setProvidedQuality(c3, (new ExecutionTimeSec()), 60);

		persistDataToDatabaseTask.setProvidedQuality(null, (new ExecutionTimeSec()), 5);

		identifySituationTask.setProvidedQuality(null, (new FalseNegativePercentage()), 20);

		notifyByMobileVibrationTask.setProvidedQuality(null, (new EnvironmentNoise()), 2);
		notifyBySoundAlertTask.setProvidedQuality(null, (new EnvironmentNoise()), 9);
		notifyByLightAlertTask.setProvidedQuality(null, (new EnvironmentNoise()), 0);
		centralCallTask.setProvidedQuality(null, (new EnvironmentNoise()), 7);

		sendInfoBySMSTask.setProvidedQuality(null, (new ExecutionTimeSec()), 65);
		sendInfoBySMSTask.setProvidedQuality(c8, (new ExecutionTimeSec()), 45);

		sendInfoByInternetTask.setProvidedQuality(null, (new ExecutionTimeSec()), 40);

		considerLastKnownLocationTask.setProvidedQuality(null, (new DistanceErrorMargin()), 900);
		considerLastKnownLocationTask.setProvidedQuality(null, (new ExecutionTimeSec()), 15);

		identifyLocationByVoiceCallTask.setProvidedQuality(null, (new DistanceErrorMargin()), 100);
		identifyLocationByVoiceCallTask.setProvidedQuality(c11, (new DistanceErrorMargin()), 300);
		identifyLocationByVoiceCallTask.setProvidedQuality(null, (new ExecutionTimeSec()), 45);

		accessLocationFromTriangulationTask.setProvidedQuality(null, (new DistanceErrorMargin()), 40);
		accessLocationFromTriangulationTask.setProvidedQuality(c11, (new DistanceErrorMargin()), 400);
		accessLocationFromTriangulationTask.setProvidedQuality(null, (new ExecutionTimeSec()), 30);

		accessLocationFromGPSTask.setProvidedQuality(null, (new DistanceErrorMargin()), 20);
		accessLocationFromGPSTask.setProvidedQuality(c11, (new DistanceErrorMargin()), 30);
		accessLocationFromGPSTask.setProvidedQuality(null, (new ExecutionTimeSec()), 50);

		accessDataFromDatabaseTask.setProvidedQuality(null, (new ExecutionTimeSec()), 20);

		getInfoFromResponsibleTask.setProvidedQuality(null, (new ExecutionTimeSec()), 25);
		getInfoFromResponsibleTask.setProvidedQuality(c11, (new ExecutionTimeSec()), 50);

		ambulanceDispatchDelegation.setProvidedQuality(null, (new ExecutionTimeSec()), 30);
	}

	// @Test
	public void testC1() {
		System.out.println("=========== Test C1 ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("accessLocationFromGPS"))
				found = 1;
			if (task.getIdentifier().contentEquals("centralCallsP"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC2() {
		System.out.println("=========== Test C2 ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("acceptEmergency"))
				found = 1;
			if (task.getIdentifier().contentEquals("notifyBySoundAlert"))
				found = 1;

			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC3() {
		System.out.println("=========== Test C3 ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("acceptEmergency"))
				found = 1;

			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC4() {
		System.out.println("=========== Test C4 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);
		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyCentralBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("confirmEmergencyByCall"))
				found = 1;
			if (task.getIdentifier().contentEquals("notifyBySoundAlert"))
				found = 1;
			if (task.getIdentifier().contentEquals("sendInfoBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("identifyLocationByVoiceCall"))
				found = 1;
			if (task.getIdentifier().contentEquals("accessLocationFromTriangulation"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC5() {
		System.out.println("=========== Test C5 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyCentralBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("notifyBySoundAlert"))
				found = 1;
			if (task.getIdentifier().contentEquals("sendInfoBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("confirmEmergencyByCall"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC6() {
		System.out.println("=========== Test C6 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);
		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyCentralBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("sendInfoBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("confirmEmergencyByCall"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC7() {
		System.out.println("=========== Test C7 ================");
		HashSet<Context> fullContext = createFullContext(0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyCentralBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("notifyByMobileVibrationTask"))
				found = 1;
			if (task.getIdentifier().contentEquals("notifyBySoundAlert"))
				found = 1;
			if (task.getIdentifier().contentEquals("sendInfoBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("confirmEmergencyByCall"))
				found = 1;
			if (task.getIdentifier().contentEquals("identifyLocationByVoiceCall"))
				found = 1;
			if (task.getIdentifier().contentEquals("accessLocationFromTriangulation"))
				found = 1;
			if (task.getIdentifier().contentEquals("accessLocationFromGPS"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC8() {
		System.out.println("=========== Test C8 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyCentralBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("identifyLocationByVoiceCall"))
				found = 1;
			if (task.getIdentifier().contentEquals("accessLocationFromTriangulation"))
				found = 1;
			if (task.getIdentifier().contentEquals("notifyBySoundAlert"))
				found = 1;
			if (task.getIdentifier().contentEquals("sendInfoBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("confirmEmergencyByCall"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC9() {
		System.out.println("=========== Test C9 ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyByLightAlert"))
				found = 1;
			if (task.getIdentifier().contentEquals("acceptsEmergency"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC10() {
		System.out.println("=========== Test C10 ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyByLightAlert"))
				found = 1;
			if (task.getIdentifier().contentEquals("acceptEmergency"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC11() {
		System.out.println("=========== Test C11 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue("CGM is unachievable", tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyCentralBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("notifyByLightAlert"))
				found = 1;
			if (task.getIdentifier().contentEquals("sendInfoBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("confirmEmergencyByCall"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testC12() {
		System.out.println("=========== Test C12 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyCentralBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("sendInfoBySMS"))
				found = 1;
			if (task.getIdentifier().contentEquals("confirmEmergencyByCall"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testAll() {
		System.out.println("=========== Test All ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (Task task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("acceptEmergency"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	@Test
	public void testNone() {
		System.out.println("=========== Test None ================");
		HashSet<Context> fullContext = createFullContext(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks == null);
	}

	private HashSet<Context> createFullContext(int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8, int t9,
			int t10, int t11, int t12) {
		HashSet<Context> fullContext = new HashSet<Context>();

		System.out.print("Contexto: [");
		if (t1 == 1) {
			fullContext.add(c1);
			System.out.print("c1 ");
		}
		if (t2 == 1) {
			fullContext.add(c2);
			System.out.print("c2 ");
		}
		if (t3 == 1) {
			fullContext.add(c3);
			System.out.print("c3 ");
		}
		if (t4 == 1) {
			fullContext.add(c4);
			System.out.print("c4 ");
		}
		if (t5 == 1) {
			fullContext.add(c5);
			System.out.print("c5 ");
		}
		if (t6 == 1) {
			fullContext.add(c6);
			System.out.print("c6 ");
		}
		if (t7 == 1) {
			fullContext.add(c7);
			System.out.print("c7 ");
		}
		if (t8 == 1) {
			fullContext.add(c8);
			System.out.print("c8 ");
		}
		if (t9 == 1) {
			fullContext.add(c9);
			System.out.print("c9 ");
		}
		if (t10 == 1) {
			fullContext.add(c10);
			System.out.print("c10 ");
		}
		if (t11 == 1) {
			fullContext.add(c11);
			System.out.print("c11 ");
		}
		if (t12 == 1) {
			fullContext.add(c12);
			System.out.print("c12 ");
		}
		System.out.println("]");
		return fullContext;
	}
}

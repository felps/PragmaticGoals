package cgm;

import cgm.metrics.Metric;
import cgm.quality.FilterQualityConstraint;
import cgm.workflow.Plan;
import cgm.workflow.WorkflowTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;

import static org.junit.Assert.*;

public class TestEachContext {
	CGM cgm;
	Logger logger = LogManager.getLogger();

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

	//@Before
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
			FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.SECONDS, 180, Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc2 = new FilterQualityConstraint(c10, Metric.SECONDS, 90, Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc3 = new FilterQualityConstraint(c9, Metric.SECONDS, 240, Comparison.LESS_OR_EQUAL_TO);
			respondToEmergencyGoal.getInterpretation().addFilterQualityConstraint(qc1);
			respondToEmergencyGoal.getInterpretation().addFilterQualityConstraint(qc2);
			respondToEmergencyGoal.getInterpretation().addFilterQualityConstraint(qc3);
		}

		{
			FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 30,
					Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc2 = new FilterQualityConstraint(c3, Metric.FALSE_NEGATIVE_PERCENTAGE, 10,
					Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc3 = new FilterQualityConstraint(c9, Metric.FALSE_NEGATIVE_PERCENTAGE, 5,
					Comparison.LESS_OR_EQUAL_TO);
			emergencyIsDetectedGoal.getInterpretation().addFilterQualityConstraint(qc1);
			emergencyIsDetectedGoal.getInterpretation().addFilterQualityConstraint(qc2);
			emergencyIsDetectedGoal.getInterpretation().addFilterQualityConstraint(qc3);
		}

		{
			FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.SECONDS, 60, Comparison.LESS_OR_EQUAL_TO);
			centralReceivesInfoGoal.getInterpretation().addFilterQualityConstraint(qc1);
		}

		{
			FilterQualityConstraint qc4 = new FilterQualityConstraint(null, Metric.DISTANCE_ERROR, 1000,
					Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc6 = new FilterQualityConstraint(c5, Metric.DISTANCE_ERROR, 20, Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc5 = new FilterQualityConstraint(c10, Metric.DISTANCE_ERROR, 200, Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.SECONDS, 120, Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc3 = new FilterQualityConstraint(c9, Metric.SECONDS, 240, Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc2 = new FilterQualityConstraint(c10, Metric.SECONDS, 20, Comparison.LESS_OR_EQUAL_TO);
			locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc1);
			locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc2);
			locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc3);
			locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc4);
			locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc5);
			locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc6);
		}

		{
			FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc2 = new FilterQualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_OR_EQUAL_TO);
			infoIsPreparedGoal.getInterpretation().addFilterQualityConstraint(qc1);
			infoIsPreparedGoal.getInterpretation().addFilterQualityConstraint(qc2);
		}

		{
			FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.NOISE, 10, Comparison.LESS_OR_EQUAL_TO);
			FilterQualityConstraint qc2 = new FilterQualityConstraint(c1, Metric.NOISE, 3, Comparison.LESS_OR_EQUAL_TO);
			isNotifiedAboutEmergencyGoal.getInterpretation().addFilterQualityConstraint(qc1);
			isNotifiedAboutEmergencyGoal.getInterpretation().addFilterQualityConstraint(qc2);
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

	// //@Test
	public void testC1() {
		logger.debug("=========== Test C1 ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getId().contentEquals("accessLocationFromGPS"))
				found = 1;
			if (task.getId().contentEquals("centralCallsP"))
				found = 1;
			assertEquals("Task " + task.getId() + " not expected", 0, found);
		}
	}

	//@Test
	public void testC2() {
		logger.debug("=========== Test C2 ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("acceptEmergency"))
				found = 1;
			if (task.getIdentifier().contentEquals("notifyBySoundAlert"))
				found = 1;

			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	//@Test
	public void testC3() {
		logger.debug("=========== Test C3 ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("acceptEmergency"))
				found = 1;

			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	//@Test
	public void testC4() {
		logger.debug("=========== Test C4 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);
		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
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

	//@Test
	public void testC5() {
		logger.debug("=========== Test C5 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
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

	//@Test
	public void testC6() {
		logger.debug("=========== Test C6 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);
		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
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

	//@Test
	public void testC7() {
		logger.debug("=========== Test C7 ================");
		HashSet<Context> fullContext = createFullContext(0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
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

	//@Test
	public void testC8() {
		logger.debug("=========== Test C8 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
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

	//@Test
	public void testC9() {
		logger.debug("=========== Test C9 ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyByLightAlert"))
				found = 1;
			if (task.getIdentifier().contentEquals("acceptsEmergency"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	//@Test
	public void testC10() {
		logger.debug("=========== Test C10 ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("notifyByLightAlert"))
				found = 1;
			if (task.getIdentifier().contentEquals("acceptEmergency"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	//@Test
	public void testC11() {
		logger.debug("=========== Test C11 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue("CGM is unachievable", tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
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

	//@Test
	public void testC12() {
		logger.debug("=========== Test C12 ================");
		HashSet<Context> fullContext = createFullContext(1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
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

	//@Test
	public void testAll() {
		logger.debug("=========== Test All ================");
		HashSet<Context> fullContext = createFullContext(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertTrue(tasks != null);
		for (WorkflowTask task : cgm.isAchievable(fullContext, null).getTasks()) {
			int found = 0;
			if (task.getIdentifier().contentEquals("acceptEmergency"))
				found = 1;
			assertEquals("Task " + task.getIdentifier() + " not expected", 0, found);
		}
	}

	//@Test
	public void testNone() {
		logger.debug("=========== Test None ================");
		HashSet<Context> fullContext = createFullContext(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		Plan tasks = cgm.isAchievable(fullContext, null);

		assertFalse(tasks.isAchievable());
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
		logger.debug("]");
		return fullContext;
	}
}

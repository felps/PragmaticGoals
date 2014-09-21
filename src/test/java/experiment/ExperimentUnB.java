package experiment;

import org.junit.Before;

import com.sun.java.swing.plaf.windows.WindowsTreeUI.CollapsedIcon;

import cgm.CGM;
import cgm.Goal;
import cgm.Task;

public class ExperimentUnB {

	CGM cgm;
	
	@Before
	public void setUp() {
		/* Goals */
		Goal respondToEmergencyGoal = new Goal(Goal.AND);		
		Goal emergencyIsDetectedGoal = new Goal(Goal.OR);
		Goal callForHelpIsAcceptedGoal = new Goal(Goal.AND);
		Goal falseAlarmIsCheckedGoal = new Goal(Goal.OR);
		Goal pIsContacted = new Goal(Goal.AND);
		Goal receivesEmergencyButtonCallGoal = new Goal(Goal.OR);
		Goal situationsAreIdentifiedGoal = new Goal(Goal.AND);
		Goal vitalSignsAreMonitoredGoal = new Goal(Goal.AND);
		Goal isNotifiedAboutEmergencyGoal = new Goal(Goal.OR);
		Goal centralReceivesInfoGoal = new Goal(Goal.AND);
		Goal infoIsSentToEmergencyGoal = new Goal(Goal.OR);
		Goal infoIsPreparedGoal = new Goal(Goal.OR);
		Goal locationIsIdentifiedGoal = new Goal(Goal.OR);
		Goal setupAutomatedInfoGoal = new Goal(Goal.AND);
		Goal situationDataIsRecoveredGoal = new Goal(Goal.AND);
		Goal contactResponsibleGoal = new Goal(Goal.AND);
		Goal medicalCareReachesGoal = new Goal(Goal.AND);
		Goal ambulanceIsDispatchedGoal = new Goal(Goal.AND);
		
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
		Task accessInfoFromDatabaseTask = new Task();
		Task gatherInfoFromResponsibleTask = new Task();
		Task ambulanceDispatch = new Task();
		
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
		falseAlarmIsCheckedGoal.addDependency(confirmEmergencyByCallTask);
		
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

		/* Goal interpretations */
		
	}

}





































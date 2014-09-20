package experiment;

import org.junit.Before;

import cgm.CGM;
import cgm.Goal;
import cgm.Task;

public class ExperimentUnB {

	CGM cgm;
	
	@Before
	public void setUp() {
		Goal respondToEmergencyGoal = new Goal(Goal.AND);
		Goal emergencyIsDetectedGoal = new Goal(Goal.OR);
		Goal callForHelpIsAcceptedGoal = new Goal(Goal.AND);
		Goal falseAlarmIsCheckedGoal = new Goal(Goal.OR);
		Goal receivesEmergencyButtonCallGoal = new Goal(Goal.OR);
		Goal situationsAreIdentifiedGoal = new Goal(Goal.AND);
		Goal vitalSignsAreMonitoredGoal = new Goal(Goal.AND);
		Goal isNotifiedAboutEmergencyGoal = new Goal(Goal.OR);
		Goal centralReceivesInfoGoal = new Goal(Goal.AND);
		Goal infoIsSEntToEmergencyGoal = new Goal(Goal.OR);
		Goal locationIsIdentifiedGoal = new Goal(Goal.OR);
		Goal setupAutomatedInfoGoal = new Goal(Goal.AND);
		Goal situationDataIsRecoveredGoal = new Goal(Goal.AND);
		Goal contactResponsibleGoal = new Goal(Goal.AND);
		
		Task notifyCentralBySMSTask = new Task();
		Task notifyCentralByInternetTask = new Task();
		Task acceptEmergencyTask = new Task();
		Task confirmEmergencyByCallTask = new Task();
		Task processDataFromSensorsTask = new Task();
		Task identifySituationTask = new Task();
		Task gatherInfoFromSensorsTask = new Task();
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
	}

}

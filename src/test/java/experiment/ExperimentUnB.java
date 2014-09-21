package experiment;

import org.junit.Before;

import com.sun.java.swing.plaf.windows.WindowsTreeUI.CollapsedIcon;

import cgm.CGM;
import cgm.Comparison;
import cgm.Context;
import cgm.Goal;
import cgm.Metric;
import cgm.QualityConstraint;
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
		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_THAN);
			QualityConstraint qc3 = new QualityConstraint(c9, Metric.SECONDS, 1800, Comparison.LESS_THAN);
			respondToEmergencyGoal.addQualityConstraint(qc1);
			respondToEmergencyGoal.addQualityConstraint(qc2);
			respondToEmergencyGoal.addQualityConstraint(qc3);
		}
		
		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 80, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c3, Metric.FALSE_NEGATIVE_PERCENTAGE, 95, Comparison.LESS_THAN);
			emergencyIsDetectedGoal.addQualityConstraint(qc1);
			emergencyIsDetectedGoal.addQualityConstraint(qc2);
		}
		
		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 60, Comparison.LESS_THAN);
			centralReceivesInfoGoal.addQualityConstraint(qc1);
		}
		
		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_THAN);
			QualityConstraint qc3 = new QualityConstraint(c9, Metric.SECONDS, 1800, Comparison.LESS_THAN);
			QualityConstraint qc4 = new QualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_THAN);
			QualityConstraint qc5 = new QualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_THAN);
			QualityConstraint qc6 = new QualityConstraint(c9, Metric.SECONDS, 1800, Comparison.LESS_THAN);
			locationIsIdentifiedGoal.addQualityConstraint(qc1);
			locationIsIdentifiedGoal.addQualityConstraint(qc2);
			locationIsIdentifiedGoal.addQualityConstraint(qc3);
			locationIsIdentifiedGoal.addQualityConstraint(qc4);
			locationIsIdentifiedGoal.addQualityConstraint(qc5);
			locationIsIdentifiedGoal.addQualityConstraint(qc6);
		}

		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_THAN);
			infoIsPreparedGoal.addQualityConstraint(qc1);
			infoIsPreparedGoal.addQualityConstraint(qc2);
		}
		
		{
			QualityConstraint qc1 = new QualityConstraint(null, Metric.SECONDS, 900, Comparison.LESS_THAN);
			QualityConstraint qc2 = new QualityConstraint(c10, Metric.SECONDS, 600, Comparison.LESS_THAN);
			isNotifiedAboutEmergencyGoal.addQualityConstraint(qc1);
			isNotifiedAboutEmergencyGoal.addQualityConstraint(qc2);
		}
		
	}

}





































package runtime.Correctness;

import org.junit.Before;
import org.junit.Test;
import pragmatic.*;
import pragmatic.metrics.Metric;
import pragmatic.metrics.exceptions.MetricNotFoundException;
import pragmatic.quality.FilterQualityConstraint;
import pragmatic.runtime.annotations.*;
import pragmatic.workflow.Plan;
import pragmatic.workflow.WorkflowTask;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.Math.pow;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by fpont_000 on 28/07/2016.
 */
public class PragmaticRuntimeTest {

    private CGM mpersModel;

    /* Contexts */
    private Context c1 = new Context("c1");
    private Context c2 = new Context("c2");
    private Context c3 = new Context("c3");
    private Context c4 = new Context("c4");
    private Context c5 = new Context("c5");
    private Context c6 = new Context("c6");
    private Context c7 = new Context("c7");
    private Context c8 = new Context("c8");
    private Context c9 = new Context("c9");
    private Context c10 = new Context("c10");
    private Context c11 = new Context("c11");
    private Context c12 = new Context("c12");

    private Pragmatic g1_RespondToEmergencyGoal;
    private Pragmatic g2_emergencyIsDetectedGoal;
    private Pragmatic g3_isNotifiedAboutEmergencyGoal;
    private Pragmatic g4_centralReceivesInfoGoal;
    private Pragmatic g9_infoIsPreparedGoal;
    private Pragmatic g17_locationIsIdentifiedGoal;

    private Goal g5_medicalCareReachesGoal;
    private Goal g6_callForHelpIsAcceptedGoal;
    private Goal g7_situationsAreIdentifiedGoal;
    private Goal g8_infoIsSentToEmergencyGoal;
    private Goal g10_ambulanceIsDispatchedToLocationGoal;
    private Goal g11_receivesEmergencyButtonCallGoal;
    private Goal g12_falseAlarmIsCheckedGoal;
    private Goal g13_vitalSignsAreMonitoredGoal;
    private Goal g14_setupAutomatedInfoGoal;
    private Goal g15_contactResponsibleGoal;
    private Goal g16_pIsContacted;
    private Goal g18_situationDataIsRecoveredGoal;

    private int contextSet = 1;
    private Task ambulanceDispatchDelegation;
    private Task getInfoFromResponsibleTask;
    private Task accessDataFromDatabaseTask;
    private Task accessLocationFromGPSTask;
    private Task accessLocationFromTriangulationTask;
    private Task identifyLocationByVoiceCallTask;
    private Task considerLastKnownLocationTask;
    private Task sendInfoByInternetTask;
    private Task sendInfoBySMSTask;
    private Task centralCallTask;
    private Task notifyByLightAlertTask;
    private Task notifyBySoundAlertTask;
    private Task notifyByMobileVibrationTask;
    private Task persistDataToDatabaseTask;
    private Task collectDataFromSensorsTask;
    private Task identifySituationTask;
    private Task processDataFromSensorsTask;
    private Task confirmEmergencyByCallTask;
    private Task acceptEmergencyTask;
    private Task notifyCentralByInternetTask;
    private Task notifyCentralBySMSTask;


    @Before
    public void setUp() {
        RuntimeAnnotation runtimeAnnotation;
        CardinalityAnnotation cardinalAnnotation;

        mpersModel = new CGM();

		/* Goals */
        g1_RespondToEmergencyGoal = new Pragmatic(Goal.AND);
        runtimeAnnotation = new SequentialAnnotation();
        g1_RespondToEmergencyGoal.setRuntimeAnnotation(runtimeAnnotation);

        g2_emergencyIsDetectedGoal = new Pragmatic(Goal.OR);
        runtimeAnnotation = new AlternativeAnnotation();
        g2_emergencyIsDetectedGoal.setRuntimeAnnotation(runtimeAnnotation);

        g3_isNotifiedAboutEmergencyGoal = new Pragmatic(Goal.OR);
        runtimeAnnotation = new AlternativeAnnotation();
        g3_isNotifiedAboutEmergencyGoal.setRuntimeAnnotation(runtimeAnnotation);

        g4_centralReceivesInfoGoal = new Pragmatic(Goal.AND);
        runtimeAnnotation = new SequentialAnnotation();
        g4_centralReceivesInfoGoal.setRuntimeAnnotation(runtimeAnnotation);

        g5_medicalCareReachesGoal = new Goal(Goal.AND);
        runtimeAnnotation = new AlternativeAnnotation();
        g5_medicalCareReachesGoal.setRuntimeAnnotation(runtimeAnnotation);

        g6_callForHelpIsAcceptedGoal = new Goal(Goal.AND);
        runtimeAnnotation = new SequentialAnnotation();
        g6_callForHelpIsAcceptedGoal.setRuntimeAnnotation(runtimeAnnotation);

        g7_situationsAreIdentifiedGoal = new Goal(Goal.AND);
        runtimeAnnotation = new InterleavedAnnotation();
        g7_situationsAreIdentifiedGoal.setRuntimeAnnotation(runtimeAnnotation);

        g8_infoIsSentToEmergencyGoal = new Goal(Goal.OR);
        runtimeAnnotation = new TryAnnotation();
        g8_infoIsSentToEmergencyGoal.setRuntimeAnnotation(runtimeAnnotation);

        g9_infoIsPreparedGoal = new Pragmatic(Goal.OR);
        runtimeAnnotation = new TryAnnotation();
        g9_infoIsPreparedGoal.setRuntimeAnnotation(runtimeAnnotation);

        g10_ambulanceIsDispatchedToLocationGoal = new Goal(Goal.AND);
        runtimeAnnotation = new AlternativeAnnotation();
        g10_ambulanceIsDispatchedToLocationGoal.setRuntimeAnnotation(runtimeAnnotation);

        g11_receivesEmergencyButtonCallGoal = new Goal(Goal.OR);
        runtimeAnnotation = new AlternativeAnnotation();
        g11_receivesEmergencyButtonCallGoal.setRuntimeAnnotation(runtimeAnnotation);

        g12_falseAlarmIsCheckedGoal = new Goal(Goal.OR);
        runtimeAnnotation = new TryAnnotation();
        g12_falseAlarmIsCheckedGoal.setRuntimeAnnotation(runtimeAnnotation);

        g13_vitalSignsAreMonitoredGoal = new Goal(Goal.AND);
        runtimeAnnotation = new InterleavedAnnotation();
        g13_vitalSignsAreMonitoredGoal.setRuntimeAnnotation(runtimeAnnotation);

        g14_setupAutomatedInfoGoal = new Goal(Goal.AND);
        runtimeAnnotation = new InterleavedAnnotation();
        g14_setupAutomatedInfoGoal.setRuntimeAnnotation(runtimeAnnotation);

        g15_contactResponsibleGoal = new Goal(Goal.AND);
        cardinalAnnotation = new SequentialCardinalAnnotation();
        cardinalAnnotation.setIterations(3);
        g15_contactResponsibleGoal.setRuntimeAnnotation(cardinalAnnotation);


        g16_pIsContacted = new Goal(Goal.AND);
        cardinalAnnotation = new SequentialCardinalAnnotation();
        cardinalAnnotation.setIterations(2);
        g16_pIsContacted.setRuntimeAnnotation(cardinalAnnotation);

        g17_locationIsIdentifiedGoal = new Pragmatic(Goal.OR);
        runtimeAnnotation = new AlternativeAnnotation();
        g17_locationIsIdentifiedGoal.setRuntimeAnnotation(runtimeAnnotation);

        g18_situationDataIsRecoveredGoal = new Goal(Goal.AND);
        cardinalAnnotation = new InterleavedCardinalAnnotation();
        cardinalAnnotation.setIterations(2);
        g18_situationDataIsRecoveredGoal.setRuntimeAnnotation(cardinalAnnotation);

		/* Tasks */
        notifyCentralBySMSTask = new Task();
        notifyCentralBySMSTask.setIdentifier("notifyCentralBySMS");

        notifyCentralByInternetTask = new Task();
        notifyCentralByInternetTask.setIdentifier("notifyCentralByInternet");

        acceptEmergencyTask = new Task();
        acceptEmergencyTask.setIdentifier("acceptEmergency");

        confirmEmergencyByCallTask = new Task();
        confirmEmergencyByCallTask.setIdentifier("confirmEmergencyByCall");

        processDataFromSensorsTask = new Task();
        processDataFromSensorsTask.setIdentifier("processDataFromSensors");

        identifySituationTask = new Task();
        identifySituationTask.setIdentifier("identifySituationTask");

        collectDataFromSensorsTask = new Task();
        collectDataFromSensorsTask.setIdentifier("collectDataFromSensorsTask");

        persistDataToDatabaseTask = new Task();
        persistDataToDatabaseTask.setIdentifier("persistDataToDatabaseTask");

        notifyByMobileVibrationTask = new Task();
        notifyByMobileVibrationTask.setIdentifier("notifyByMobileVibrationTask");

        notifyBySoundAlertTask = new Task();
        notifyBySoundAlertTask.setIdentifier("notifyBySoundAlert");

        notifyByLightAlertTask = new Task();
        notifyByLightAlertTask.setIdentifier("notifyByLightAlert");

        centralCallTask = new Task();
        centralCallTask.setIdentifier("centralCallsP");

        sendInfoBySMSTask = new Task();
        sendInfoBySMSTask.setIdentifier("sendInfoBySMS");

        sendInfoByInternetTask = new Task();
        sendInfoByInternetTask.setIdentifier("sendInfoByInternet");

        considerLastKnownLocationTask = new Task();
        considerLastKnownLocationTask.setIdentifier("considerLastKnownLocation");

        identifyLocationByVoiceCallTask = new Task();
        identifyLocationByVoiceCallTask.setIdentifier("identifyLocationByVoiceCall");

        accessLocationFromTriangulationTask = new Task();
        accessLocationFromTriangulationTask.setIdentifier("accessLocationFromTriangulation");

        accessLocationFromGPSTask = new Task();
        accessLocationFromGPSTask.setIdentifier("accessLocationFromGPS");

        accessDataFromDatabaseTask = new Task();
        accessDataFromDatabaseTask.setIdentifier("accessDataFromDatabase");

        getInfoFromResponsibleTask = new Task();
        getInfoFromResponsibleTask.setIdentifier("getInfoFromResponsible");

        ambulanceDispatchDelegation = new Task();
        ambulanceDispatchDelegation.setIdentifier("ambulanceDispatchDelegation");

        mpersModel.setRoot(g1_RespondToEmergencyGoal);

		/* Refinements */

        g1_RespondToEmergencyGoal.addDependency(g2_emergencyIsDetectedGoal);
        g1_RespondToEmergencyGoal.addDependency(g3_isNotifiedAboutEmergencyGoal);
        g1_RespondToEmergencyGoal.addDependency(g4_centralReceivesInfoGoal);
        g1_RespondToEmergencyGoal.addDependency(g5_medicalCareReachesGoal);

        g2_emergencyIsDetectedGoal.addDependency(g6_callForHelpIsAcceptedGoal);
        g2_emergencyIsDetectedGoal.addDependency(g7_situationsAreIdentifiedGoal);

        g3_isNotifiedAboutEmergencyGoal.addDependency(notifyByMobileVibrationTask);
        g3_isNotifiedAboutEmergencyGoal.addDependency(notifyBySoundAlertTask);
        g3_isNotifiedAboutEmergencyGoal.addDependency(notifyByLightAlertTask);
        g3_isNotifiedAboutEmergencyGoal.addDependency(centralCallTask);

        g4_centralReceivesInfoGoal.addDependency(g8_infoIsSentToEmergencyGoal);
        g4_centralReceivesInfoGoal.addDependency(g9_infoIsPreparedGoal);

        g5_medicalCareReachesGoal.addDependency(g10_ambulanceIsDispatchedToLocationGoal);

        g6_callForHelpIsAcceptedGoal.addDependency(g11_receivesEmergencyButtonCallGoal);
        g6_callForHelpIsAcceptedGoal.addDependency(g12_falseAlarmIsCheckedGoal);

        g7_situationsAreIdentifiedGoal.addDependency(processDataFromSensorsTask);
        g7_situationsAreIdentifiedGoal.addDependency(g13_vitalSignsAreMonitoredGoal);
        g7_situationsAreIdentifiedGoal.addDependency(identifySituationTask);

        g8_infoIsSentToEmergencyGoal.addDependency(sendInfoByInternetTask);
        g8_infoIsSentToEmergencyGoal.addDependency(new Skip());
        g8_infoIsSentToEmergencyGoal.addDependency(sendInfoBySMSTask);

        g9_infoIsPreparedGoal.addDependency(g14_setupAutomatedInfoGoal);
        g9_infoIsPreparedGoal.addDependency(new Skip());
        g9_infoIsPreparedGoal.addDependency(g15_contactResponsibleGoal);

        g10_ambulanceIsDispatchedToLocationGoal.addDependency(ambulanceDispatchDelegation);

        g11_receivesEmergencyButtonCallGoal.addDependency(notifyCentralBySMSTask);
        g11_receivesEmergencyButtonCallGoal.addDependency(notifyCentralByInternetTask);

        g12_falseAlarmIsCheckedGoal.addDependency(g16_pIsContacted);
        g12_falseAlarmIsCheckedGoal.addDependency(new Skip());
        g12_falseAlarmIsCheckedGoal.addDependency(acceptEmergencyTask);

        g13_vitalSignsAreMonitoredGoal.addDependency(collectDataFromSensorsTask);
        g13_vitalSignsAreMonitoredGoal.addDependency(persistDataToDatabaseTask);

        g14_setupAutomatedInfoGoal.addDependency(g17_locationIsIdentifiedGoal);
        g14_setupAutomatedInfoGoal.addDependency(g18_situationDataIsRecoveredGoal);

        g15_contactResponsibleGoal.addDependency(getInfoFromResponsibleTask);

        g16_pIsContacted.addDependency(confirmEmergencyByCallTask);

        g17_locationIsIdentifiedGoal.addDependency(considerLastKnownLocationTask);
        g17_locationIsIdentifiedGoal.addDependency(identifyLocationByVoiceCallTask);
        g17_locationIsIdentifiedGoal.addDependency(accessLocationFromTriangulationTask);
        g17_locationIsIdentifiedGoal.addDependency(accessLocationFromGPSTask);

        g18_situationDataIsRecoveredGoal.addDependency(accessDataFromDatabaseTask);



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
            g1_RespondToEmergencyGoal.getInterpretation().addFilterQualityConstraint(qc1);
            g1_RespondToEmergencyGoal.getInterpretation().addFilterQualityConstraint(qc2);
            g1_RespondToEmergencyGoal.getInterpretation().addFilterQualityConstraint(qc3);
        }

        {
            FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.FALSE_NEGATIVE_PERCENTAGE, 30,
                    Comparison.LESS_OR_EQUAL_TO);
            FilterQualityConstraint qc2 = new FilterQualityConstraint(c3, Metric.FALSE_NEGATIVE_PERCENTAGE, 10,
                    Comparison.LESS_OR_EQUAL_TO);
            FilterQualityConstraint qc3 = new FilterQualityConstraint(c9, Metric.FALSE_NEGATIVE_PERCENTAGE, 5,
                    Comparison.LESS_OR_EQUAL_TO);
            g2_emergencyIsDetectedGoal.getInterpretation().addFilterQualityConstraint(qc1);
            g2_emergencyIsDetectedGoal.getInterpretation().addFilterQualityConstraint(qc2);
            g2_emergencyIsDetectedGoal.getInterpretation().addFilterQualityConstraint(qc3);
        }

        {
            FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.SECONDS, 60, Comparison.LESS_OR_EQUAL_TO);
            g4_centralReceivesInfoGoal.getInterpretation().addFilterQualityConstraint(qc1);
        }

        {
            FilterQualityConstraint qc4 = new FilterQualityConstraint(null, Metric.DISTANCE_ERROR, 1000,
                    Comparison.LESS_OR_EQUAL_TO);
            FilterQualityConstraint qc6 = new FilterQualityConstraint(c5, Metric.DISTANCE_ERROR, 20, Comparison.LESS_OR_EQUAL_TO);
            FilterQualityConstraint qc5 = new FilterQualityConstraint(c10, Metric.DISTANCE_ERROR, 200, Comparison.LESS_OR_EQUAL_TO);
            FilterQualityConstraint qc2 = new FilterQualityConstraint(c10, Metric.SECONDS, 20, Comparison.LESS_OR_EQUAL_TO);
            FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.SECONDS, 120, Comparison.LESS_OR_EQUAL_TO);
            FilterQualityConstraint qc3 = new FilterQualityConstraint(c9, Metric.SECONDS, 240, Comparison.LESS_OR_EQUAL_TO);
            g17_locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc1);
            g17_locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc2);
            g17_locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc3);
            g17_locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc4);
            g17_locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc5);
            g17_locationIsIdentifiedGoal.getInterpretation().addFilterQualityConstraint(qc6);
        }

        {
            FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.SECONDS, 45, Comparison.LESS_OR_EQUAL_TO);
            FilterQualityConstraint qc2 = new FilterQualityConstraint(c10, Metric.SECONDS, 30, Comparison.LESS_OR_EQUAL_TO);
            g9_infoIsPreparedGoal.getInterpretation().addFilterQualityConstraint(qc1);
            g9_infoIsPreparedGoal.getInterpretation().addFilterQualityConstraint(qc2);
        }

        {
            FilterQualityConstraint qc1 = new FilterQualityConstraint(null, Metric.NOISE, 10, Comparison.LESS_OR_EQUAL_TO);
            FilterQualityConstraint qc2 = new FilterQualityConstraint(c1, Metric.NOISE, 3, Comparison.LESS_OR_EQUAL_TO);
            g3_isNotifiedAboutEmergencyGoal.getInterpretation().addFilterQualityConstraint(qc1);
            g3_isNotifiedAboutEmergencyGoal.getInterpretation().addFilterQualityConstraint(qc2);
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
    public void testEachContextForBreachesInAnnotationSemantic() throws Exception {
        int i;

        for (i = 0; i < pow(2,12); i++) {
            Set<Context> contextSet = generateNextContextSet(12);
            Plan plan = mpersModel.isAchievable(contextSet, null);
            if (plan != null && plan.isAchievable()) {
                validateAnnotations(plan);
            }
        }

    }

    private void validateAnnotations(Plan plan) {
        //System.out.println("validating plan");
        Collection<WorkflowTask> tasks = plan.getTasks();

        HashSet<WorkflowTask> tasksUnderG1 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG2 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG3 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG4 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG5 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG6 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG7 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG8 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG9 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG10 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG11 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG12 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG13 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG14 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG15 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG16 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG17 = new HashSet<>();
        HashSet<WorkflowTask> tasksUnderG18 = new HashSet<>();

        WorkflowTask iterationTask;
        // Original task
        tasksUnderG18.add(accessDataFromDatabaseTask.getWorkflowTask());
        // Iteration Copy 1
        iterationTask = new WorkflowTask(accessDataFromDatabaseTask);
        iterationTask.setIterationCopy(1);
        tasksUnderG18.add(iterationTask);
        // Iteration Copy 2
        iterationTask = new WorkflowTask(accessDataFromDatabaseTask);
        iterationTask.setIterationCopy(2);
        tasksUnderG18.add(accessDataFromDatabaseTask.getWorkflowTask());

        tasksUnderG17.add(considerLastKnownLocationTask.getWorkflowTask());
        tasksUnderG17.add(identifyLocationByVoiceCallTask.getWorkflowTask());
        tasksUnderG17.add(accessLocationFromGPSTask.getWorkflowTask());
        tasksUnderG17.add(accessLocationFromTriangulationTask.getWorkflowTask());

        tasksUnderG16.add(confirmEmergencyByCallTask.getWorkflowTask());
        iterationTask = new WorkflowTask(confirmEmergencyByCallTask);
        iterationTask.setIterationCopy(1);
        tasksUnderG16.add(iterationTask);
        iterationTask = new WorkflowTask(confirmEmergencyByCallTask);
        iterationTask.setIterationCopy(2);
        tasksUnderG16.add(iterationTask);

        tasksUnderG15.add(getInfoFromResponsibleTask.getWorkflowTask());
        iterationTask = new WorkflowTask(getInfoFromResponsibleTask);
        iterationTask.setIterationCopy(1);
        tasksUnderG15.add(iterationTask);
        iterationTask = new WorkflowTask(getInfoFromResponsibleTask);
        iterationTask.setIterationCopy(2);
        tasksUnderG15.add(iterationTask);

        tasksUnderG14.addAll(tasksUnderG17);
        tasksUnderG14.addAll(tasksUnderG18);

        tasksUnderG13.add(collectDataFromSensorsTask.getWorkflowTask());
        tasksUnderG13.add(persistDataToDatabaseTask.getWorkflowTask());

        tasksUnderG12.addAll(tasksUnderG16);
        tasksUnderG12.add(acceptEmergencyTask.getWorkflowTask());

        tasksUnderG11.add(notifyCentralByInternetTask.getWorkflowTask());
        tasksUnderG11.add(notifyCentralBySMSTask.getWorkflowTask());

        tasksUnderG10.add(ambulanceDispatchDelegation.getWorkflowTask());

        tasksUnderG9.addAll(tasksUnderG14);
        tasksUnderG9.addAll(tasksUnderG15);

        tasksUnderG8.add(sendInfoByInternetTask.getWorkflowTask());
        tasksUnderG8.add(sendInfoBySMSTask.getWorkflowTask());

        tasksUnderG7.add(processDataFromSensorsTask.getWorkflowTask());
        tasksUnderG7.add(identifySituationTask.getWorkflowTask());
        tasksUnderG7.addAll(tasksUnderG13);

        tasksUnderG6.addAll(tasksUnderG11);
        tasksUnderG6.addAll(tasksUnderG12);

        tasksUnderG5.addAll(tasksUnderG10);

        tasksUnderG4.addAll(tasksUnderG8);
        tasksUnderG4.addAll(tasksUnderG9);

        tasksUnderG3.add(notifyByMobileVibrationTask.getWorkflowTask());
        tasksUnderG3.add(notifyBySoundAlertTask.getWorkflowTask());
        tasksUnderG3.add(notifyByLightAlertTask.getWorkflowTask());
        tasksUnderG3.add(centralCallTask.getWorkflowTask());

        tasksUnderG2.addAll(tasksUnderG6);
        tasksUnderG2.addAll(tasksUnderG7);

        tasksUnderG1.addAll(tasksUnderG2);
        tasksUnderG1.addAll(tasksUnderG3);
        tasksUnderG1.addAll(tasksUnderG3);
        tasksUnderG1.addAll(tasksUnderG4);


        // G1 sequential annotation
        assertTrue(containsAny(plan, tasksUnderG2));
        assertTrue(containsAny(plan, tasksUnderG3));
        assertTrue(containsAny(plan, tasksUnderG4));
        assertTrue(containsAny(plan, tasksUnderG5));

        // G2 Alternative Annotation
        assertTrue(
                (containsAny(plan, tasksUnderG7) && !containsAny(plan, tasksUnderG6)) ||
                        (!containsAny(plan, tasksUnderG7) && containsAny(plan, tasksUnderG6)));

        // G3 Alternative Annotation
        assertTrue(containsAny(plan, tasksUnderG3));
        if (plan.getTasks().contains(notifyByMobileVibrationTask.getWorkflowTask())) {
            assertFalse(plan.getTasks().contains(notifyBySoundAlertTask.getWorkflowTask()));
            assertFalse(plan.getTasks().contains(notifyByLightAlertTask.getWorkflowTask()));
            assertFalse(plan.getTasks().contains(centralCallTask.getWorkflowTask()));
        }
        if (plan.getTasks().contains(notifyBySoundAlertTask.getWorkflowTask())) {
            assertFalse(plan.getTasks().contains(notifyByMobileVibrationTask.getWorkflowTask()));
            assertFalse(plan.getTasks().contains(notifyByLightAlertTask.getWorkflowTask()));
            assertFalse(plan.getTasks().contains(centralCallTask.getWorkflowTask()));
        }
        if (plan.getTasks().contains(notifyByLightAlertTask.getWorkflowTask())) {
            assertFalse(plan.getTasks().contains(notifyBySoundAlertTask.getWorkflowTask()));
            assertFalse(plan.getTasks().contains(notifyByMobileVibrationTask.getWorkflowTask()));
            assertFalse(plan.getTasks().contains(centralCallTask.getWorkflowTask()));
        }
        if (plan.getTasks().contains(centralCallTask.getWorkflowTask())) {
            assertFalse(plan.getTasks().contains(notifyBySoundAlertTask.getWorkflowTask()));
            assertFalse(plan.getTasks().contains(notifyByLightAlertTask.getWorkflowTask()));
            assertFalse(plan.getTasks().contains(notifyByMobileVibrationTask.getWorkflowTask()));
        }

        // G4 Serial Annotation
        assertTrue(
                (containsAny(plan, tasksUnderG8) && containsAny(plan, tasksUnderG9)) ||
                        (!containsAny(plan, tasksUnderG4)));

        // G5
        assertTrue(plan.getTasks().contains(ambulanceDispatchDelegation.getWorkflowTask()));

        // G6 Sequential Annotation
        assertTrue(
                (containsAny(plan, tasksUnderG11) && containsAny(plan, tasksUnderG12))
                        || (!containsAny(plan, tasksUnderG6)));

        // G7 Sequential Annotation
        assertTrue(
                (
                        containsAny(plan, tasksUnderG13) &&
                                plan.getTasks().contains(processDataFromSensorsTask.getWorkflowTask()) &&
                                plan.getTasks().contains(identifySituationTask.getWorkflowTask())
                ) || ( // OR
                        !containsAny(plan, tasksUnderG7)
                ));

        // G8 Try annotation
        assertTrue(plan.getTasks().contains(sendInfoByInternetTask.getWorkflowTask()));

        // G9 Try Annotation
        assertTrue(containsAny(plan, tasksUnderG14));

        // G10 Has no Annotations
        assertTrue(plan.getTasks().contains(ambulanceDispatchDelegation.getWorkflowTask()));

        // G11 Alternative Annotation
        if (plan.getTasks().contains(notifyCentralByInternetTask.getWorkflowTask())) {
            assertFalse(plan.getTasks().contains(notifyCentralBySMSTask.getWorkflowTask()));
        }
        if (plan.getTasks().contains(notifyCentralBySMSTask.getWorkflowTask())) {
            assertFalse(plan.getTasks().contains(notifyCentralByInternetTask.getWorkflowTask()));
        }

        // G12 Try Annotation

        // G13 Interleaved Annotation
        assertTrue(
                (
                        plan.getTasks().contains(collectDataFromSensorsTask.getWorkflowTask()) &&
                                plan.getTasks().contains(persistDataToDatabaseTask.getWorkflowTask())
                ) || ( // OR
                        !containsAny(plan, tasksUnderG13)
                ));

        // G14 Interleaved Annotation

        if(containsAny(plan, tasksUnderG17)){
            assertTrue(containsAny(plan, tasksUnderG18));
        } else assertFalse (containsAny(plan, tasksUnderG14));

        // G15 Sequential Iterated

        // G16 Sequential Iterated


        // G17 alternative or
        if (containsAny(plan, tasksUnderG17)) {
            if (plan.getTasks().contains(considerLastKnownLocationTask.getWorkflowTask())) {
                assertFalse(plan.getTasks().contains(identifyLocationByVoiceCallTask.getWorkflowTask()));
                assertFalse(plan.getTasks().contains(accessLocationFromGPSTask.getWorkflowTask()));
                assertFalse(plan.getTasks().contains(accessLocationFromTriangulationTask.getWorkflowTask()));
            }
            if (plan.getTasks().contains(identifyLocationByVoiceCallTask.getWorkflowTask())) {
                assertFalse(plan.getTasks().contains(considerLastKnownLocationTask.getWorkflowTask()));
                assertFalse(plan.getTasks().contains(accessLocationFromGPSTask.getWorkflowTask()));
                assertFalse(plan.getTasks().contains(accessLocationFromTriangulationTask.getWorkflowTask()));
            }
            if (plan.getTasks().contains(accessLocationFromGPSTask.getWorkflowTask())) {
                assertFalse(plan.getTasks().contains(identifyLocationByVoiceCallTask.getWorkflowTask()));
                assertFalse(plan.getTasks().contains(considerLastKnownLocationTask.getWorkflowTask()));
                assertFalse(plan.getTasks().contains(accessLocationFromTriangulationTask.getWorkflowTask()));
            }
            if (plan.getTasks().contains(accessLocationFromTriangulationTask.getWorkflowTask())) {
                assertFalse(plan.getTasks().contains(identifyLocationByVoiceCallTask.getWorkflowTask()));
                assertFalse(plan.getTasks().contains(accessLocationFromGPSTask.getWorkflowTask()));
                assertFalse(plan.getTasks().contains(considerLastKnownLocationTask.getWorkflowTask()));
            }
        }

    }


    private boolean containsAny(Plan plan, Set<WorkflowTask> tasks) {
        for (WorkflowTask task : tasks){
            if(plan.getTasks().contains(task)){
                return true;
            }
        }
        return false;
    }

    private Set<Context> createFullContext(int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8, int t9,
                                           int t10, int t11, int t12) {
        HashSet<Context> fullContext = new HashSet<>();

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


    private Set<Context> generateNextContextSet(int contextAmount) {
        long limit;
        HashSet<Context> contexts = new HashSet<Context>();
        int currentSet = contextSet;
        limit = (long) pow(2, contextAmount);
        for (int i = 0; i < contextAmount; i++) {
            if (currentSet % 2 != 0) {
                contexts.add(new Context("c" + (contextAmount - i)));
                //System.out.println("c" + (contextAmount - i));
            }
            currentSet /= 2;
        }

        //contexts.add(null);
        contextSet++;
        return contexts;
    }

    private Plan evaluateContextSet(int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8, int t9,
                                   int t10, int t11, int t12) {
        System.out.println("=========== Experiment Context Set  ================");
        Set<Context> fullContext = createFullContext(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
        Plan plan = null;
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            plan = mpersModel.isAchievable(fullContext, null);
        }
        long elapsed = System.nanoTime() - startTime;
        System.out.println("Average elapsed time:" + elapsed / 10000 + " ns...");
        return plan;
    }

    @Test
    public void GlocGoal() throws MetricNotFoundException {
        System.out.println("=========== Experiment Context Set 1 ================");
        Set<Context> fullContext = createFullContext(1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0);
        Plan plan = null;
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            plan = g17_locationIsIdentifiedGoal.isAchievable(fullContext, null);
        }
        long elapsed = System.nanoTime() - startTime;
        System.out.println("Average elapsed time:" + elapsed / 10000 + " ns...");

        assertTrue(plan != null);
        assertFalse(plan.isAchievable());
    }

    @Test
    public void GlocGoalAchievable() throws MetricNotFoundException {
        System.out.println("=========== Experiment Context Set 1 ================");
        Set<Context> fullContext = createFullContext(1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0);
        Plan plan = null;
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            plan = g17_locationIsIdentifiedGoal.isAchievable(fullContext, null);
        }
        long elapsed = System.nanoTime() - startTime;
        System.out.println("Average elapsed time:" + elapsed / 10000 + " ns...");

        assertTrue(plan != null);
        assertTrue(plan.isAchievable());
    }
}

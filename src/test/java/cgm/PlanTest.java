package cgm;

import cgm.workflow.Plan;
import cgm.workflow.WorkflowTask;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Felipe on 07/07/2016.
 */
public class PlanTest {

    @Test
    public void shouldAddASingleTask() {
        WorkflowTask task = new WorkflowTask(new Task("id"));

        Plan plan = new Plan(task);

        // Test if it was added
        assertEquals("Task was not added", 1, plan.getTasks().size());
        // Test if it is an initial tasks
        assertTrue("Task was not set as initial", plan.getInitialTasks().contains(task));
        // Test if it is a final task
        assertTrue("Task was not set as final", plan.getFinalTasks().contains(task));
    }
//
//    @Test
//    public void shouldAddAnotherTaskSequentially() {
//        Task task1 = new Task();
//        task1.setIdentifier("initial");
//
//        Task task2 = new Task();
//        task2.setIdentifier("final");
//
//        Plan plan = new Plan(task1);
//        Plan plan2 = new Plan(task2);
//
//        plan.addSerial(plan2);
//
//        // Test if it was added
//        assertEquals("A Task was not added", 2, plan.getTasks().size());
//        // Test if it is an initial tasks
//        assertTrue("Task1 was set as initial", plan.getInitialTasks().contains(task1));
//        // Test if it is a final task
//        assertTrue("Task2 was not set as final", plan.getFinalTasks().contains(task2));
//        // Test if the chaining was correct
//
////        assertTrue("Task2 was not set as enabled by task1", task1.getEnabledTasksSet().contains(task2));
////        assertTrue("Task1 was not set as prerequisite for task2", task2.getRequiredTasksSet().contains(task1));
//    }
//
//    //
//    @Test
//    public void shouldAddATaskWithDependencies() {
//        Task task1, task2;
//
//        task1 = new Task("t1");
//        task2 = new Task("t2");
//
//        Plan initialPlan = new Plan(task1);
//
//        HashSet<WorkflowTask> dependencies = new HashSet<WorkflowTask>();
//        dependencies.add(task1.getWorkflowTask());
//        initialPlan.add(task2.getWorkflowTask(), dependencies);
//
//        // Test if it was added
//        assertEquals("A Task was not added", 2, initialPlan.getTasks().size());
//        // Test if it is an initial tasks
//        assertTrue("Task1 was not set as initial", initialPlan.getInitialTasks().contains(task1));
//        assertFalse("Task2 was set as initial", initialPlan.getInitialTasks().contains(task2));
//        // Test if the chaining was correct
//        assertTrue("Task2 was not set as enabled by task1", task1.getWorkflowTask().getEnabledTasksSet().contains(task2));
//        assertTrue("Task1 was not set as a prerequisite for task2", task2.getRequiredTasksSet().contains(task1));
//        // Test if it is a final task
//        assertFalse("Task1 was set as final", initialPlan.getFinalTasks().contains(task1));
//        assertTrue("Task2 was not set as final", initialPlan.getFinalTasks().contains(task2));
//
//    }
//
//    @Test
//    public void shouldAddAnotherTaskInterleaved() {
//        Task cgmTask1 = new Task();
//        Task cgmTask2 = new Task();
//        cgmTask1.setIdentifier("initial1");
//        cgmTask2.setIdentifier("initial2");
//
//        WorkflowTask task1 = new WorkflowTask(cgmTask1);
//        WorkflowTask task2 = new WorkflowTask(cgmTask2);
//
//        Plan plan2 = new Plan(task1);
//        Plan plan = new Plan(task2);
//
//        plan.addParallel(plan2);
//
//        // Test if it was added
//        assertEquals("A Task was not added", 2, plan.getTasks().size());
//        // Test if it is an initial tasks
//        assertTrue("Task1 was not set as initial", plan.getInitialTasks().contains(task1));
//        assertTrue("Task2 was not set as initial", plan.getInitialTasks().contains(task2));
//        // Test if it is a final task
//        assertTrue("Task1 was not set as final", plan.getFinalTasks().contains(task1));
//        assertTrue("Task2 was not set as final", plan.getFinalTasks().contains(task2));
//        // Test if the chaining was correct
//        assertTrue("Task2 is not enabled by task1", !task1.getEnabledTasksSet().contains(task2));
//        assertTrue("Task1 is not a prerequisite for task2", !task2.getRequiredTasksSet().contains(task1));
//        assertTrue("Task1 is not enabled by task2", !task2.getEnabledTasksSet().contains(task1));
//        assertTrue("Task2 is not a prerequisite for task1", !task1.getRequiredTasksSet().contains(task2));
//    }
////    @Test
////    public void shouldAddASerialPlan() {
////        WorkflowTask task1, task2, task3;
////
////        task1 = new WorkflowTask("t1");
////        task2 = new WorkflowTask("t2");
////        task3 = new WorkflowTask("t3");
////
////        Plan initialPlan = new Plan(task1);
////        HashSet<WorkflowTask> dependencies = new HashSet<WorkflowTask>();
////        dependencies.add(task1);
////        initialPlan.add(task2, dependencies);
////
////        // Test if it was added
////        assertEquals("A Task was not added", 2, initialPlan.getTasks().size());
////        // Test if it is an initial tasks
////        assertTrue("Task1 was not set as initial", initialPlan.getInitialTasks().contains(task1));
////        assertFalse("Task2 was set as initial", initialPlan.getInitialTasks().contains(task2));
////        // Test if the chaining was correct
////        assertTrue("Task2 was not set as enabled by task1", task1.getEnabledTasksSet().contains(task2));
////        assertTrue("Task1 was not set as a prerequisite for task2", task2.getRequiredTasksSet().contains(task1));
////        // Test if it is a final task
////        assertFalse("Task1 was set as final", initialPlan.getFinalTasks().contains(task1));
////        assertTrue("Task2 was not set as final", initialPlan.getFinalTasks().contains(task2));
////
////        Plan endPlan = new Plan(task3);
////        initialPlan.addSerial(endPlan);
////
////        // Test if it was added
////        assertEquals("A Task was not added", 3, initialPlan.getTasks().size());
////        // Test if it is an initial tasks
////        assertTrue("Task1 was not set as initial", initialPlan.getInitialTasks().contains(task1));
////        assertFalse("Task2 was set as initial", initialPlan.getInitialTasks().contains(task2));
////        assertFalse("Task3 was set as initial", initialPlan.getInitialTasks().contains(task3));
////        // Test if it is a final task
////        assertFalse("Task1 was set as final", initialPlan.getFinalTasks().contains(task1));
////        assertFalse("Task2 was set as final", initialPlan.getFinalTasks().contains(task2));
////        assertTrue("Task3 was not set as final", initialPlan.getFinalTasks().contains(task3));
////        // Test if the chaining was correct
////        assertTrue("Task2 was not set as enabled by task1", task1.getEnabledTasksSet().contains(task2));
////        assertTrue("Task1 was not set as a prerequisite for task2", task2.getRequiredTasksSet().contains(task1));
////        assertTrue("Task3 was not set as enabled by task2", task2.getEnabledTasksSet().contains(task3));
////        assertTrue("Task2 was not set as a prerequisite for task3", task3.getRequiredTasksSet().contains(task2));
////
////        assertEquals("More than one enabled dependency for task1", 1, task1.getEnabledTasksSet().size());
////        assertEquals("There are no required dependencies for task1", 0, task1.getRequiredTasksSet().size());
////        assertEquals("More than one enabled dependency for task2", 1, task2.getEnabledTasksSet().size());
////        assertEquals("More than one required dependency for task2", 1, task2.getRequiredTasksSet().size());
////        assertEquals("There are no enabled dependencies for task3", 0, task3.getEnabledTasksSet().size());
////        assertEquals("More than one required dependency for task3", 1, task3.getRequiredTasksSet().size());
////
////
////    }
}

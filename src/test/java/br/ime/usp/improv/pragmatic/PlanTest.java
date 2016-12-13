package br.ime.usp.improv.pragmatic;

import org.junit.Test;

import br.ime.usp.improv.pragmatic.Task;
import br.ime.usp.improv.pragmatic.workflow.WorkflowTask;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by Felipe on 07/07/2016.
 *
 * Tests for the Plan class
 */
public class PlanTest {

    @Test
    public void shouldAddASingleTask() {
        Task task = new Task("id");

        WorkflowPlan plan = new WorkflowPlan(task);

        // Test if it was added
        assertEquals("Task was not added", 1, plan.getTasks().size());
        // Test if it is an initial tasks
        assertTrue("Task was not set as initial", plan.getInitialTasks().contains(task.getWorkflowTask()));
        // Test if it is a final task
        assertTrue("Task was not set as final", plan.getFinalTasks().contains(task.getWorkflowTask()));
    }
//
@Test
public void shouldAddAnotherTaskSequentially() {
    Task task1 = new Task();
    task1.setIdentifier("initial");

    Task task2 = new Task();
    task2.setIdentifier("final");

    WorkflowPlan plan = new WorkflowPlan(task1);
    WorkflowPlan plan2 = new WorkflowPlan(task2);

    WorkflowTask workflowTask1 = task1.getWorkflowTask();
    WorkflowTask workflowTask2 = task2.getWorkflowTask();

    plan.addSerial(plan2);

    // Test if it was added
    assertEquals("A Task was not added", 2, plan.getTasks().size());
    // Test if it is an initial tasks
    assertTrue("Task1 was not set as initial", plan.getInitialTasks().contains(workflowTask1));
    // Test if it is a final task
    assertTrue("Task2 was not set as final", plan.getFinalTasks().contains(workflowTask2));
    // Test if the chaining was correct

    assertTrue("Task2 was not set as enabled by task1", workflowTask1.getEnabledTasksSet().contains(workflowTask2));
    assertTrue("Task1 was not set as prerequisite for task2", workflowTask2.getRequiredTasksSet().contains(workflowTask1));
}


    @Test
    public void shouldAddATaskWithDependencies() {
        Task task1, task2;

        task1 = new Task("t1");
        task2 = new Task("t2");

        WorkflowPlan initialPlan = new WorkflowPlan(task1);

        HashSet<WorkflowTask> dependencies = new HashSet<>();
        dependencies.add(new WorkflowTask(task1));
        initialPlan.add(new WorkflowTask(task2), dependencies);

        // Test if it was added
        assertEquals("A Task was not added", 2, initialPlan.getTasks().size());
        // Test if it is an initial tasks
        assertTrue("Task1 was not set as initial", initialPlan.getInitialTasks().contains(task1.getWorkflowTask()));
        assertFalse("Task2 was set as initial", initialPlan.getInitialTasks().contains(task2.getWorkflowTask()));
        // Test if the chaining was correct
        assertTrue("Task2 was not set as enabled by task1", task1.getWorkflowTask().getEnabledTasksSet().contains(task2.getWorkflowTask()));
        assertTrue("Task1 was not set as a prerequisite for task2", task2.getWorkflowTask().getRequiredTasksSet().contains(task1.getWorkflowTask()));
        // Test if it is a final task
        assertFalse("Task1 was set as final", initialPlan.getFinalTasks().contains(task1.getWorkflowTask()));
        assertTrue("Task2 was not set as final", initialPlan.getFinalTasks().contains(task2.getWorkflowTask()));

    }

    @Test
    public void shouldAddAnotherTaskInterleaved() {
        Task cgmTask1 = new Task();
        Task cgmTask2 = new Task();
        cgmTask1.setIdentifier("initial1");
        cgmTask2.setIdentifier("initial2");

        WorkflowTask task1 = cgmTask1.getWorkflowTask();
        WorkflowTask task2 = cgmTask2.getWorkflowTask();

        WorkflowPlan plan2 = new WorkflowPlan(cgmTask1);
        WorkflowPlan plan = new WorkflowPlan(cgmTask2);

        plan.addParallel(plan2);

        // Test if it was added
        assertEquals("A Task was not added", 2, plan.getTasks().size());
        // Test if it is an initial tasks
        assertTrue("Task1 was not set as initial", plan.getInitialTasks().contains(task1));
        assertTrue("Task2 was not set as initial", plan.getInitialTasks().contains(task2));
        // Test if it is a final task
        assertTrue("Task1 was not set as final", plan.getFinalTasks().contains(task1));
        assertTrue("Task2 was not set as final", plan.getFinalTasks().contains(task2));
        // Test if the chaining was correct
        assertTrue("Task2 is not enabled by task1", !task1.getEnabledTasksSet().contains(task2));
        assertTrue("Task1 is not a prerequisite for task2", !task2.getRequiredTasksSet().contains(task1));
        assertTrue("Task1 is not enabled by task2", !task2.getEnabledTasksSet().contains(task1));
        assertTrue("Task2 is not a prerequisite for task1", !task1.getRequiredTasksSet().contains(task2));
    }

    @Test
    public void shouldAddASerialPlan() {
        Task task1, task2, task3;

        task1 = new Task("t1");
        task2 = new Task("t2");
        task3 = new Task("t3");

        WorkflowPlan initialPlan = new WorkflowPlan(task1);
        HashSet<WorkflowTask> dependencies = new HashSet<>();
        dependencies.add(task1.getWorkflowTask());
        initialPlan.add(task2.getWorkflowTask(), dependencies);

        // Test if it was added
        assertEquals("A Task was not added", 2, initialPlan.getTasks().size());
        // Test if it is an initial tasks
        assertTrue("Task1 was not set as initial", initialPlan.getInitialTasks().contains(task1.getWorkflowTask()));
        assertFalse("Task2 was set as initial", initialPlan.getInitialTasks().contains(task2.getWorkflowTask()));
        // Test if the chaining was correct
        assertTrue("Task2 was not set as enabled by task1", task1.getWorkflowTask().getEnabledTasksSet().contains(task2.getWorkflowTask()));
        assertTrue("Task1 was not set as a prerequisite for task2", task2.getWorkflowTask().getRequiredTasksSet().contains(task1.getWorkflowTask()));
        // Test if it is a final task
        assertFalse("Task1 was set as final", initialPlan.getFinalTasks().contains(task1.getWorkflowTask()));
        assertTrue("Task2 was not set as final", initialPlan.getFinalTasks().contains(task2.getWorkflowTask()));

        WorkflowPlan endPlan = new WorkflowPlan(task3);
        initialPlan.addSerial(endPlan);

        // Test if it was added
        assertEquals("A Task was not added", 3, initialPlan.getTasks().size());
        // Test if it is an initial tasks
        assertTrue("Task1 was not set as initial", initialPlan.getInitialTasks().contains(task1.getWorkflowTask()));
        assertFalse("Task2 was set as initial", initialPlan.getInitialTasks().contains(task2.getWorkflowTask()));
        assertFalse("Task3 was set as initial", initialPlan.getInitialTasks().contains(task3.getWorkflowTask()));
        // Test if it is a final task
        assertFalse("Task1 was set as final", initialPlan.getFinalTasks().contains(task1.getWorkflowTask()));
        assertFalse("Task2 was set as final", initialPlan.getFinalTasks().contains(task2.getWorkflowTask()));
        assertTrue("Task3 was not set as final", initialPlan.getFinalTasks().contains(task3.getWorkflowTask()));
        // Test if the chaining was correct
        assertTrue("Task2 was not set as enabled by task1", task1.getWorkflowTask().getEnabledTasksSet().contains(task2.getWorkflowTask()));
        assertTrue("Task1 was not set as a prerequisite for task2", task2.getWorkflowTask().getRequiredTasksSet().contains(task1.getWorkflowTask()));
        assertTrue("Task3 was not set as enabled by task2", task2.getWorkflowTask().getEnabledTasksSet().contains(task3.getWorkflowTask()));
        assertTrue("Task2 was not set as a prerequisite for task3", task3.getWorkflowTask().getRequiredTasksSet().contains(task2.getWorkflowTask()));

        assertEquals("More than one enabled dependency for task1", 1, task1.getWorkflowTask().getEnabledTasksSet().size());
        assertEquals("There are no required dependencies for task1", 0, task1.getWorkflowTask().getRequiredTasksSet().size());
        assertEquals("More than one enabled dependency for task2", 1, task2.getWorkflowTask().getEnabledTasksSet().size());
        assertEquals("More than one required dependency for task2", 1, task2.getWorkflowTask().getRequiredTasksSet().size());
        assertEquals("There are no enabled dependencies for task3", 0, task3.getWorkflowTask().getEnabledTasksSet().size());
        assertEquals("More than one required dependency for task3", 1, task3.getWorkflowTask().getRequiredTasksSet().size());


    }

    @Test
    public void shouldAddAPlanToAnEmptyPlanCorrectly() throws Exception {
        Task task1 = new Task();
        task1.setIdentifier("final");

        WorkflowPlan plan = new WorkflowPlan();
        WorkflowPlan plan2 = new WorkflowPlan(task1);

        WorkflowTask workflowTask1 = task1.getWorkflowTask();

        plan.addSerial(plan2);

        // Test if it was added
        assertEquals("A Task was not added", 1, plan.getTasks().size());
        // Test if it is an initial tasks
        assertTrue("Task1 was not set as initial", plan.getInitialTasks().contains(workflowTask1));
        // Test if it is a final task
        assertTrue("Task1 was not set as final", plan.getFinalTasks().contains(workflowTask1));
    }

    @Test
    public void shouldAddAnEmptyPlanToAPlanCorrectly() throws Exception {
        Task task1 = new Task();
        task1.setIdentifier("final");

        WorkflowPlan plan = new WorkflowPlan();
        WorkflowPlan plan2 = new WorkflowPlan(task1);

        WorkflowTask workflowTask1 = task1.getWorkflowTask();

        plan2.addSerial(plan);

        // Test if it was added
        assertEquals("A Task was not added", 1, plan2.getTasks().size());
        // Test if it is an initial tasks
        assertTrue("Task1 was not set as initial", plan2.getInitialTasks().contains(workflowTask1));
        // Test if it is a final task
        assertTrue("Task1 was not set as final", plan2.getFinalTasks().contains(workflowTask1));
    }
}

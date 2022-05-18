package edu.ncsu.csc216.wolf_tasks.model.tasks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

/**
 * Test class for Task
 * 
 * @author yash
 *
 */
public class TaskTest {

	/** Task name to be used in testing */
	public static final String TASK_NAME = "Eat dinner";

	/** Task description to be used in testing */
	public static final String TASK_DESCRIPTION = "Remember to cook chicken.";

	/**
	 * Tests the Task class constructor
	 */
	@Test
	public void testTask() {
		Task t = new Task(TASK_NAME, TASK_DESCRIPTION, false, true);
		assertEquals(TASK_NAME, t.getTaskName());
		assertEquals(TASK_DESCRIPTION, t.getTaskDescription());
		assertFalse(t.isRecurring());
		assertTrue(t.isActive());

		// Tests task with null or empty task name
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Task(null, TASK_DESCRIPTION, false, true));
		assertEquals("Incomplete task information.", e.getMessage(), "Wrong error message");

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Task("", TASK_DESCRIPTION, false, true));
		assertEquals("Incomplete task information.", e1.getMessage(), "Wrong error message");

		// Tests task with null description
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Task(TASK_NAME, null, false, true));
		assertEquals("Incomplete task information.", e2.getMessage(), "Wrong error message");

		// Tests getting task list name with no tasks
		assertEquals("", t.getTaskListName());
	}

	/**
	 * Tests the Task.toString method
	 */
	@Test
	public void testToString() {
		// Test to string for task that is only active
		Task t = new Task(TASK_NAME, TASK_DESCRIPTION, false, true);
		assertEquals(TASK_NAME, t.getTaskName());
		assertEquals(TASK_DESCRIPTION, t.getTaskDescription());
		assertFalse(t.isRecurring());
		assertTrue(t.isActive());

		assertEquals("* " + TASK_NAME + ",active" + "\n" + TASK_DESCRIPTION, t.toString());

		// Test to string for task that is only recurring
		Task t2 = new Task(TASK_NAME, TASK_DESCRIPTION, true, false);
		assertEquals(TASK_NAME, t2.getTaskName());
		assertEquals(TASK_DESCRIPTION, t2.getTaskDescription());
		assertTrue(t2.isRecurring());
		assertFalse(t2.isActive());

		assertEquals("* " + TASK_NAME + ",recurring" + "\n" + TASK_DESCRIPTION, t2.toString());

		// Test to string for task that is not recurring or active
		Task t3 = new Task(TASK_NAME, TASK_DESCRIPTION, false, false);
		assertEquals(TASK_NAME, t3.getTaskName());
		assertEquals(TASK_DESCRIPTION, t3.getTaskDescription());
		assertFalse(t3.isRecurring());
		assertFalse(t3.isActive());

		assertEquals("* " + TASK_NAME + "\n" + TASK_DESCRIPTION, t3.toString());

		// Test to string for task that recurring and active
		Task t4 = new Task(TASK_NAME, TASK_DESCRIPTION, true, true);
		assertEquals(TASK_NAME, t4.getTaskName());
		assertEquals(TASK_DESCRIPTION, t4.getTaskDescription());
		assertTrue(t4.isRecurring());
		assertTrue(t4.isActive());

		assertEquals("* " + TASK_NAME + ",recurring" + ",active" + "\n" + TASK_DESCRIPTION, t4.toString());

	}

	/**
	 * Tests the Task.addTaskList() method
	 */
	@Test
	public void testAddTaskList() {
		Task t = new Task(TASK_NAME, TASK_DESCRIPTION, false, true);
		assertEquals(TASK_NAME, t.getTaskName());
		assertEquals(TASK_DESCRIPTION, t.getTaskDescription());
		assertFalse(t.isRecurring());
		assertTrue(t.isActive());

		TaskList tempTaskList = new TaskList("Chores", 0);
		TaskList tempTaskList2 = new TaskList("Kitchen", 0);
		TaskList nullTaskList = null;

		// Adds task lists
		tempTaskList.addTask(t);
		tempTaskList2.addTask(t);

		// Attempts to add null task list
		assertThrows(IllegalArgumentException.class, () -> t.addTaskList(nullTaskList));

		// Attempts to add duplicate task list
		tempTaskList2.addTask(t);

		// Tests getting task list name
		assertEquals("Chores", t.getTaskListName());

	}

	/**
	 * Tests the Task.completeTask() and Task.clone() method
	 */
	@Test
	public void testCompleteTaskandClone() {
		// Test complete task for task that is not active or recurring
		Task t = new Task(TASK_NAME, TASK_DESCRIPTION, false, false);
		assertEquals(TASK_NAME, t.getTaskName());
		assertEquals(TASK_DESCRIPTION, t.getTaskDescription());
		assertFalse(t.isRecurring());
		assertFalse(t.isActive());

		TaskList tempTaskList = new TaskList("Chores", 0);
		TaskList tempTaskList2 = new TaskList("Kitchen", 0);

		// Adds task lists
		tempTaskList.addTask(t);
		tempTaskList2.addTask(t);

		t.completeTask();
		// Checks that completed count incremented
		assertEquals(1, tempTaskList.getCompletedCount());
		assertEquals(1, tempTaskList2.getCompletedCount());

		assertEquals(0, tempTaskList.getTasksAsArray().length);

		// Test complete task for task that is recurring
		Task t2 = new Task(TASK_NAME, TASK_DESCRIPTION, true, false);
		assertEquals(TASK_NAME, t2.getTaskName());
		assertEquals(TASK_DESCRIPTION, t2.getTaskDescription());
		assertTrue(t2.isRecurring());
		assertFalse(t2.isActive());

		// Adds a task that is not recurring or active
		Task t3 = new Task("temp", TASK_DESCRIPTION, false, false);
		assertEquals("temp", t3.getTaskName());
		assertEquals(TASK_DESCRIPTION, t3.getTaskDescription());
		assertFalse(t3.isRecurring());
		assertFalse(t3.isActive());

		tempTaskList.addTask(t2);
		tempTaskList2.addTask(t2);
		tempTaskList.addTask(t3);
		tempTaskList2.addTask(t3);

		t2.completeTask();

		assertEquals(2, tempTaskList.getCompletedCount());
		assertEquals(2, tempTaskList2.getCompletedCount());
		assertEquals(t3.getTaskName(), tempTaskList.getTasksAsArray()[0][1]);
		assertEquals(t2.getTaskName(), tempTaskList.getTasksAsArray()[1][1]);

	}

}

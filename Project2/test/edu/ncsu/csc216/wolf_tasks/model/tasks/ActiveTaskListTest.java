package edu.ncsu.csc216.wolf_tasks.model.tasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

/**
 * Test class for ActiveTaskList
 * 
 * @author Yash Agarwal
 * @author Anika Bhadriraju
 *
 */
public class ActiveTaskListTest {

	/**
	 * Tests the ActiveTaskList class
	 */
	@Test
	public void testActiveTaskList() {
		// Construct and test ActiveTaskList
		ActiveTaskList actTaskList = new ActiveTaskList();
		assertEquals("Active Tasks", actTaskList.getTaskListName());
		assertEquals(0, actTaskList.getCompletedCount());
	}

	/**
	 * Tests ActiveTaskList.addTask()
	 */
	@Test
	public void testAddingTask() {
		// Add an active task
		Task temp1 = new Task("Make lunch", "Cook the rice", false, true);
		ActiveTaskList actTaskList = new ActiveTaskList();

		actTaskList.addTask(temp1);
		assertEquals(temp1, actTaskList.getTask(0));

		// Fail to add a non-active task
		Task temp2 = new Task("Make dinner", "Cook the chicken", false, false);
		Exception e = assertThrows(IllegalArgumentException.class, () -> actTaskList.addTask(temp2));
		assertEquals("Cannot add task to Active Tasks.", e.getMessage());

	}

	/**
	 * Tests ActiveTaskList.getTasksAsArray()
	 */
	@Test
	public void testGetTaskAsArray() {
		// Add an active task
		Task temp1 = new Task("Make lunch", "Cook the rice", false, true);
		ActiveTaskList actTaskList = new ActiveTaskList();

		actTaskList.addTask(temp1);
		String[][] taskListArray = actTaskList.getTasksAsArray();

		assertEquals(temp1.getTaskListName(), taskListArray[0][0]);
		assertEquals(temp1.getTaskName(), taskListArray[0][1]);
	}

	/**
	 * Tests ActiveTaskList.clearTasks()
	 */
	@Test
	public void testClearTasks() {
		// Add an active tasks
		Task temp1 = new Task("Make lunch", "Cook the rice", false, true);
		Task temp2 = new Task("Make Dinner", "Cook the meat", false, true);

		ActiveTaskList actTaskList = new ActiveTaskList();

		actTaskList.addTask(temp1);
		actTaskList.addTask(temp2);

		assertEquals(2, actTaskList.getTasks().size());

		actTaskList.clearTasks();
		assertEquals(0, actTaskList.getTasks().size());

	}

	/**
	 * Tests ActiveTaskList.setTaskListName()
	 */
	@Test
	public void testSetTaskListName() {
		ActiveTaskList actTaskList = new ActiveTaskList();
		// Tests setting to valid name
		actTaskList.setTaskListName("Active Tasks");
		assertEquals("Active Tasks", actTaskList.getTaskListName());

		assertThrows(IllegalArgumentException.class, () -> actTaskList.setTaskListName("random"));

	}
}

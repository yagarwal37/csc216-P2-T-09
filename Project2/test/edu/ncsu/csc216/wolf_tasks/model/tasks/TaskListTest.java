package edu.ncsu.csc216.wolf_tasks.model.tasks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

/**
 * Test class for TaskList
 * 
 * @author Anika Bhadriraju
 * @author Yash Agarwal
 *
 */
public class TaskListTest {

	/**
	 * Tests the TaskList constructor
	 */
	@Test
	public void testTaskList() {
		// Construction and test
		TaskList taskList = new TaskList("To Do List", 0);
		assertEquals("To Do List", taskList.getTaskListName());
		assertEquals(0, taskList.getCompletedCount());

		// Attempts to create a task list with a null and empty name
		Exception e = assertThrows(IllegalArgumentException.class, () -> new TaskList(null, 0));
		assertEquals("Invalid name.", e.getMessage(), "Wrong error message");

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new TaskList("", 0));
		assertEquals("Invalid name.", e2.getMessage(), "Wrong error message");

		// Attempts to create a task list with invalid completed count
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> new TaskList("To Do List", -1));
		assertEquals("Invalid completed count.", e3.getMessage(), "Wrong error message");

	}

	/**
	 * Tests the TaskList.add() and TaskList.remove() method
	 */
	@Test
	public void testAddandRemoveTask() {
		// Construction and test
		TaskList taskList = new TaskList("To Do List", 0);
		assertEquals("To Do List", taskList.getTaskListName());
		assertEquals(0, taskList.getCompletedCount());
		// Add a task to taskList
		Task temp = new Task("Make lunch", "Cook the rice", false, false);
		taskList.addTask(temp);
		assertEquals(temp, taskList.getTask(0));

		Task temp2 = new Task("Make dinner", "Cook the meat", false, false);
		taskList.addTask(temp2);
		assertEquals(temp2, taskList.getTask(1));

		assertEquals(temp, taskList.removeTask(0));
		assertEquals(temp2, taskList.removeTask(0));
	}

	/**
	 * Tests the TaskList.completeTask() method
	 */
	@Test
	public void testCompleteTask() {
		TaskList taskList = new TaskList("To Do List", 0);
		assertEquals("To Do List", taskList.getTaskListName());
		assertEquals(0, taskList.getCompletedCount());

		// Add a tasks to taskList
		Task temp = new Task("Make lunch", "Cook the rice", false, false);
		taskList.addTask(temp);
		assertEquals(temp, taskList.getTask(0));

		Task temp2 = new Task("Make dinner", "Cook the meat", false, false);
		taskList.addTask(temp2);
		assertEquals(temp2, taskList.getTask(1));

		// Complete a task
		temp.completeTask();
		assertEquals(1, taskList.getCompletedCount());
		assertEquals(temp2, taskList.getTask(0));
		temp2.completeTask();
		assertEquals(2, taskList.getCompletedCount());
		assertEquals(0, taskList.getTasks().size());

		// Complete a recurring task
		Task temp3 = new Task("Wash dishes", "Use new dish soap", true, false);
		taskList.addTask(temp3);
		taskList.addTask(temp2);
		taskList.addTask(temp);
		assertEquals(temp3, taskList.getTask(0));
		temp3.completeTask();

		// Checks that recurring task is still in task list
		assertEquals(3, taskList.getTasks().size());
		assertEquals("Wash dishes", taskList.getTask(2).getTaskName());
		assertEquals(temp2, taskList.getTask(0));
		assertEquals(temp, taskList.getTask(1));

		assertEquals(3, taskList.getCompletedCount());

	}

	/**
	 * Tests TaskList.getTasksAsArray() method
	 */
	@Test
	public void testGetTasksAsArray() {
		// Construction and test
		TaskList taskList = new TaskList("To Do List", 0);
		assertEquals("To Do List", taskList.getTaskListName());
		assertEquals(0, taskList.getCompletedCount());

		// Add a task to taskList
		Task temp = new Task("Make lunch", "Cook the rice", false, false);
		taskList.addTask(temp);
		assertEquals(temp, taskList.getTask(0));

		Task temp2 = new Task("Make dinner", "Cook the meet", false, false);
		taskList.addTask(temp2);
		assertEquals(temp2, taskList.getTask(1));

		// Compare a 2D array with getTasksAsArray
		String[][] tempArray = new String[2][2];
		tempArray[0][0] = "1";
		tempArray[0][1] = temp.getTaskName();
		tempArray[1][0] = "2";
		tempArray[1][1] = temp2.getTaskName();

		String[][] taskListArray = taskList.getTasksAsArray();

		assertEquals(tempArray[0][0], taskListArray[0][0]);
		assertEquals(tempArray[0][1], taskListArray[0][1]);
		assertEquals(tempArray[1][0], taskListArray[1][0]);
		assertEquals(tempArray[1][1], taskListArray[1][1]);

	}

	/**
	 * Tests TaskList.testCompareTo() method
	 */
	@Test
	public void testCompareTo() {
		// Construction of task lists
		TaskList current = new TaskList("Shopping Cart", 0);
		TaskList taskList = new TaskList("Apples", 0);
		TaskList taskList2 = new TaskList("Shopping Spree", 0);
		TaskList taskList3 = new TaskList("Shopping Cart", 0);

		// Tests a task
		int temp = current.compareTo(taskList);
		assertTrue(temp > 0);

		// Tests a task list that comes after current in the alphabet
		int temp2 = current.compareTo(taskList2);
		assertTrue(temp2 < 0);

		// Tests a task list that is the same as current
		int temp3 = current.compareTo(taskList3);
		assertEquals(0, temp3);

	}
}

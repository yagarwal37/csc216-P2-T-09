package edu.ncsu.csc216.wolf_tasks.model.notebook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;

/**
 * Test class for Notebook
 * 
 * @author yash
 *
 */
public class NotebookTest {

	/**
	 * Tests the Notebook class
	 */
	@Test
	public void testNotebook() {
		//Construct and test notebook
		Notebook notebook = new Notebook("Daily planner");
		assertEquals("Daily planner", notebook.getNotebookName());
		assertTrue(notebook.isChanged());
		
		
		
		//Add a TaskList to Notebook and see if it was added
		TaskList tempList = new TaskList("Dinner plans", 0);
		notebook.addTaskList(tempList);
		String[] namesArray = notebook.getTaskListsNames();
		
		assertEquals(namesArray[0], "Active Tasks");
		assertEquals(namesArray[1], "Dinner plans");
		
		
		
		//Edit currentTaskList's name to "Tester name" and see if it was successful
		notebook.editTaskList("Tester name");
		assertEquals("Tester name", notebook.getCurrentTaskList().getTaskListName());
		
		
		
		//Add a task to the currentTaskList and see if the output is correct
		Task temp = new Task("Test task", "See if this works", false, true);
		notebook.addTask(temp);
		
		String[][] outputNames = notebook.getCurrentTaskList().getTasksAsArray();
		assertEquals("1", outputNames[0][0]);
		assertEquals("Test task", outputNames[0][1]);
		
		
		
		//Edit the task, "Test task", to be recurring and check if other fields are the same
		notebook.editTask(0, temp.getTaskName(), temp.getTaskDescription(), true, temp.isActive());

		assertEquals("Test task", notebook.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("See if this works", notebook.getCurrentTaskList().getTask(0).getTaskDescription());
		assertTrue(notebook.getCurrentTaskList().getTask(0).isRecurring());
		assertTrue(notebook.getCurrentTaskList().getTask(0).isActive());
		
		
				
		//Attempt to set currentTaskList to a TaskList not in taskLists to make currentTaskList activeTaskList
		notebook.setCurrentTaskList("Failing TaskList");
		
		assertEquals("Active Tasks", notebook.getCurrentTaskList().getTaskListName());
		
		
		//Reset the currentTaskList to "Tester name"
		notebook.setCurrentTaskList("Tester name");
		
		assertEquals("Tester name", notebook.getCurrentTaskList().getTaskListName());
		
		
		//Remove the currentTaskList and default it to ActiveTaskList
		notebook.removeTaskList();
		
		assertEquals("Active Tasks", notebook.getCurrentTaskList().getTaskListName());


				
//		//Get the Tasklists' names
//		String[] outputArray = notebook.getTaskListsNames();
//		
//		//Create an array with the same names to test against outputArray
//		String[] tempArray = new String[2];
//		tempArray[0] = "Active Tasks";
//		tempArray[1] = "Dinner plans";
//		assertEquals(tempArray[0], outputArray[0]);
//		assertEquals(tempArray[1], outputArray[1]);
//
//		//Set the current TaskList to tempList and see if it checks out
//		notebook.setCurrentTaskList(tempList.getTaskListName());
//		assertEquals(tempList, notebook.getCurrentTaskList());
//		
//		//Add a task to the currentTaskList and test its addition
//		Task tempTask = new Task("Cook rice", "Set temperature to 165 degrees", false, false);
//		notebook.addTask(tempTask);
//		assertEquals(tempTask, notebook.getCurrentTaskList().getTask(0));
//		
//		//INSERT ADDITIONALLY TESTS BEFORE THIS POINT
//		//Remove tempList as the current taskList and make ActiveTaskList the default
//		notebook.removeTaskList();
//		outputArray = notebook.getTaskListsNames();
//		assertEquals(tempArray[0], outputArray[0]);

	}
}

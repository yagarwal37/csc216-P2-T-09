package edu.ncsu.csc216.wolf_tasks.model.tasks;

import edu.ncsu.csc216.wolf_tasks.model.util.ISwapList;

/**
 * List of Tasks that follow the Active parameter. No edits can be enacted on
 * this List as its a view for the user - not another TaskList in their
 * notebook. Extends the AbstractTaskList abstract class
 * 
 * @author Yash Agarwal
 * @author Anika Bhadriraju
 *
 */
public class ActiveTaskList extends AbstractTaskList {

	/** Constant name of the ActiveTaskList */
	public static final String ACTIVE_TASKS_NAME = "Active Tasks";

	/**
	 * Constructor for the ActiveTaskList Should have no completed tasks
	 */
	public ActiveTaskList() {
		super(ACTIVE_TASKS_NAME, 0);
	}

	/**
	 * Checks if a Task is active and adds it to the end of the list Overrides
	 * addTask from AbstractTaskList
	 * 
	 * @throws IllegalArgumentException Exception thrown if the Task is not active
	 */
	@Override
	public void addTask(Task t) {
		if (t.isActive()) {
			super.addTask(t);
		} else {
			throw new IllegalArgumentException("Cannot add task to Active Tasks.");
		}
	}

	/**
	 * Checks if tempName is the expected name and then sets the name Overrides the
	 * setTaskListName from AbstractTaskList
	 * 
	 * @param taskListName String new name of the TaskList
	 * @throws IllegalArgumentException Exception thrown if the name does not match
	 *                                  the expected name
	 */
	@Override
	public void setTaskListName(String taskListName) {
		if (taskListName.equals(ACTIVE_TASKS_NAME)) {
			super.setTaskListName(taskListName);
		} else {
			throw new IllegalArgumentException("The Active Tasks list may not be edited.");
		}
	}

	/**
	 * Returns a 2D String Array where the first column is the name of the TaskList
	 * that the Task belongs to and the name of the Task
	 * 
	 * @return tempArray String [][] Array that holds the contents of tasks
	 */
	@Override
	public String[][] getTasksAsArray() {
		String[][] tempArray = new String[getTasks().size()][2];
		for (int i = 0; i < getTasks().size(); i++) {
			tempArray[i][0] = getTasks().get(i).getTaskListName();
			tempArray[i][1] = getTasks().get(i).getTaskName();
		}

		return tempArray;
	}

	/**
	 * Clears the ActiveTaskList of all of the Tasks its holds - resets the list
	 */
	public void clearTasks() {
		ISwapList<Task> tempList = getTasks();

		for (int i = tempList.size() - 1; i >= 0; i--) {
			tempList.remove(i);
		}
	}
}
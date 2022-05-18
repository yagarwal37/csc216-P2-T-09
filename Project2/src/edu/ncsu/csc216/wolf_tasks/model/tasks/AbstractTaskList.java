package edu.ncsu.csc216.wolf_tasks.model.tasks;

import edu.ncsu.csc216.wolf_tasks.model.util.ISwapList;
import edu.ncsu.csc216.wolf_tasks.model.util.SwapList;

/**
 * Abstract class for TaskLists. Utilized by TaskList and ActiveTaskList. Holds
 * the underlying functionality for the two aforementioned classes
 * 
 * @author yash
 *
 */
public abstract class AbstractTaskList {
	/** Name of the TaskList */
	private String taskListName;
	/** Number of completed tasks */
	private int completedCount;
	/** List of Tasks with SwapList functionality */
	private ISwapList<Task> tasks;

	/**
	 * Constructor for AbstractTaskList object
	 * 
	 * @param taskListName   String name of the taskList
	 * @param completedCount int number of completed tasks
	 */
	public AbstractTaskList(String taskListName, int completedCount) {
		if (completedCount < 0) {
			throw new IllegalArgumentException("Invalid completed count.");
		}

		setTaskListName(taskListName);
		this.completedCount = completedCount;
		tasks = new SwapList<Task>();
	}

	/**
	 * Retrieves the name of the TaskList
	 * 
	 * @return taskListName String name of the TaskList
	 */
	public String getTaskListName() {
		return taskListName;
	}

	/**
	 * Sets the name of the TaskList
	 * 
	 * @param taskListName String name of the taskList
	 */
	public void setTaskListName(String taskListName) {
		if (taskListName == null || "".equals(taskListName)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.taskListName = taskListName;
	}

	/**
	 * Retrieves the Tasks inside of the ISwapList tasks
	 * 
	 * @return tasks ISwapList List of tasks
	 */
	public ISwapList<Task> getTasks() {
		return tasks;
	}

	/**
	 * Gets the number of completed Tasks
	 * 
	 * @return completedCount int number of completed Tasks
	 */
	public int getCompletedCount() {
		return completedCount;
	}

	/**
	 * Add a task to the SwapList tasks to the end of the list
	 * 
	 * @param t Task to be added to the taskList
	 */
	public void addTask(Task t) {
		tasks.add(t);
		t.addTaskList(this);
	}

	/**
	 * Removes the task at a given index
	 * 
	 * @param idx int location of the Task
	 * @return removedTask Task that was removed
	 */
	public Task removeTask(int idx) {
		return tasks.remove(idx);
	}

	/**
	 * Retrieves the Task at a given index
	 * 
	 * @param idx int location of the Task
	 * @return tempTask Task that is returned from the list
	 */
	public Task getTask(int idx) {
		return tasks.get(idx);
	}

	/**
	 * Locate and mark a Task as complete. Then remove it and increment the
	 * completedCount field.
	 * 
	 * @param t Task that is to be removed and marked as complete
	 */
	public void completeTask(Task t) {
		for (int i = 0; i < tasks.size(); i++) {
			if (t == tasks.get(i)) {
				tasks.remove(i);
				completedCount++;
			}
		}
	}

	/**
	 * Abstract method that returns the contents of the array as a 2D String array
	 * 
	 * @return tempArray String [][] Array that holds the contents of tasks
	 */
	public abstract String[][] getTasksAsArray();

}

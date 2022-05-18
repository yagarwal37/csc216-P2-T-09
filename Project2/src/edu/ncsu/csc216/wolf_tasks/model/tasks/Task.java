package edu.ncsu.csc216.wolf_tasks.model.tasks;

import edu.ncsu.csc216.wolf_tasks.model.util.ISwapList;
import edu.ncsu.csc216.wolf_tasks.model.util.SwapList;

/**
 * Constructs and controls the Task object with methods. Each task has a field
 * of a name, description, and whether the task is recurring and/or active. Each
 * task also has a field of type iSwapList called taskList and has functionality
 * to get and add a taskList of type AbstractTaskList. Recurring tasks are
 * cloned when marked as completed through the clone() method. Class implements
 * the Cloneable interface.
 * 
 * @author Anika Bhadriraju
 *
 */
public class Task implements Cloneable {

	/** Name of Task */
	private String taskName;

	/** Description of Task */
	private String taskDescription;

	/** True if the Task is recurring */
	private boolean recurring;

	/** True if the Task is active */
	private boolean active;

	/** ISwapList of AbstractTaskLists */
	private ISwapList<AbstractTaskList> taskLists;

	/**
	 * Constructs a Task from the provided task name, description, and whether the
	 * task is active and/or recurring. Also constructs the taskList field to an
	 * empty SwapList of AbstractTaskLists. The setting of the fields of the task is
	 * delegated to the public setter helper methods.
	 * 
	 * @param taskName    name of Task
	 * @param taskDetails details/description of Task
	 * @param recurring   true if the task is recurring
	 * @param active      true if the task is active
	 * 
	 */
	public Task(String taskName, String taskDetails, boolean recurring, boolean active) {

		setTaskName(taskName);
		setTaskDescription(taskDetails);
		setRecurring(recurring);
		setActive(active);
		taskLists = new SwapList<AbstractTaskList>();
	}

	/**
	 * Returns the name of the task.
	 * 
	 * @return task name
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Sets the task's name. The task's name cannot be null or empty.
	 * 
	 * @param taskName task name to set
	 * @throws IllegalArgumentException if task name is null or an empty string
	 */
	public void setTaskName(String taskName) {
		if (taskName == null || "".equals(taskName)) {
			throw new IllegalArgumentException("Incomplete task information.");
		}
		this.taskName = taskName;
	}

	/**
	 * Returns the description of the task.
	 * 
	 * @return task description
	 */
	public String getTaskDescription() {
		return taskDescription;
	}

	/**
	 * Sets the description of the task.
	 * 
	 * @param description task description to set
	 */
	public void setTaskDescription(String description) {
		if (description == null) {
			throw new IllegalArgumentException("Incomplete task information.");
		}
		this.taskDescription = description;
	}

	/**
	 * Returns whether the task is recurring or not.
	 * 
	 * @return true if the task is recurring
	 */
	public boolean isRecurring() {
		return recurring;
	}

	/**
	 * Sets the recurring boolean to true if the task is recurring
	 * 
	 * @param recurring whether the task is recurring to set
	 */
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	/**
	 * Returns whether the task is active or not.
	 * 
	 * @return true if the task is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active boolean to true if the task is active.
	 * 
	 * @param active whether the task is active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Returns the name of the task list that the task is in. If taskLists is null
	 * or empty, an empty string is returned. If there are multiple
	 * AbstractTaskLists in the taskLists field, the name of the first task list is
	 * returned.
	 * 
	 * @return name of task list
	 */
	public String getTaskListName() {
		if (taskLists == null || taskLists.size() == 0) {
			return "";
		} else {
			return taskLists.get(0).getTaskListName();
		}

	}

	/**
	 * Adds the AbstactTaskList is added to end of the taskLists field unless the
	 * AbstractTaskList is already registered with the Task. Functionality of the
	 * taskLists AbstractTaskList is delegated to the AbstractTaskList java class.
	 * 
	 * @param taskList AbstractTaskList to add to the end of the taskLists field
	 * @throws IllegalArgumentException if the taskList parameter is null
	 */
	public void addTaskList(AbstractTaskList taskList) {
		if (taskList == null) {
			throw new IllegalArgumentException("Incomplete task information.");
		}

		boolean isDuplicate = false;
		for (int i = 0; i < this.taskLists.size(); i++) {
			if (this.taskLists.get(i).equals(taskList)) {
				isDuplicate = true;
			}
		}

		if (!isDuplicate) {
			taskLists.add(taskList);
		}
	}

	/**
	 * Marks the Task as complete and notifies the corresponding taskLists through
	 * the completeTask() method in the TaskList class. If the Task is recurring,
	 * the task is cloned through the clone() method in Task and added to the
	 * AbstractTaskList that is registered.
	 * 
	 * @throws CloneNotSupportedException if there are no AbstractTaskLists
	 *                                    registered with the task
	 */
	public void completeTask() {
		try {
			if (recurring) {
				for (int i = 0; i < taskLists.size(); i++) {
					taskLists.get(i).completeTask(this);
				}
				Task temp = (Task) this.clone();

				for (int i = 0; i < taskLists.size(); i++) {
					taskLists.get(i).addTask(temp);
				}
			} else {
				for (int i = 0; i < taskLists.size(); i++) {
					taskLists.get(i).completeTask(this);
				}
			}
		} catch (CloneNotSupportedException e) {
			// Do nothing
		}

	}

	/**
	 * Creates a new task with copies of the fields. This new task is returned with
	 * a new SwapList in the taskLists fields to avoid references to the sane
	 * AbstractTaskList objects.
	 * 
	 * @return copy of the current task
	 * @throws CloneNotSupportedException if there are no AbstractTaskLists
	 *                                    registered with the task
	 */
	public Object clone() throws CloneNotSupportedException {
		if (taskLists.size() == 0 || taskLists == null) {
			throw new CloneNotSupportedException("Cannot clone.");
		}

		ISwapList<AbstractTaskList> tempTaskLists = new SwapList<AbstractTaskList>();
		for (int i = 0; i < this.taskLists.size(); i++) {
			tempTaskLists.add(this.taskLists.get(i));
		}

		Task temp = new Task(taskName, taskDescription, recurring, active);
		temp.taskLists = tempTaskLists;

		return temp;
	}

	/**
	 * Returns a string representation of all Task fields. Overridden to create
	 * specific output for writing Task information to a file.
	 * 
	 * @return String representation of Issue
	 */
	public String toString() {
		String rtn = "* " + getTaskName();

		if (isRecurring()) {
			rtn += ",recurring";
		}
		if (isActive()) {
			rtn += ",active";
		}

		rtn += "\n" + getTaskDescription();

		return rtn;
	}

}

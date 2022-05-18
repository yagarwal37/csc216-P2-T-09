package edu.ncsu.csc216.wolf_tasks.model.tasks;

/**
 * Creates and constructs a TaskList by extending AbstractTaskList and
 * implementing the Comparable interface. TaskList has functionality to compare
 * the names of tasks and retrieve the contents of a taskList as an array.
 * 
 * @author Anika Bhadriraju
 *
 */
public class TaskList extends AbstractTaskList implements Comparable<TaskList> {

	/**
	 * Constructs the an instance of the TaskList with the name of the task list and
	 * the number of completed tasks. Method is extended from AbstractTaskList
	 * class.
	 * 
	 * @param taskListName   name of the task list to set
	 * @param completedCount number of completed tasks within the list
	 * @throws IllegalArgumentException if taskListName is null or empty
	 * @throws IllegalArgumentException if the number of completed tasks is less
	 *                                  than zero
	 */
	public TaskList(String taskListName, int completedCount) {
		super(taskListName, completedCount);
	}

	/**
	 * Returns a 2D String array of the tasks in the task list that includes the
	 * priority of the Task and the name of the Task.
	 * 
	 * @return String[][] of tasks from the task list
	 */
	public String[][] getTasksAsArray() {
		String[][] temp = new String[this.getTasks().size()][2];
		for (int i = 0; i < this.getTasks().size(); i++) {
			temp[i][0] = String.valueOf(i + 1);
			temp[i][1] = this.getTasks().get(i).getTaskName();
		}
		return temp;
	}

	/**
	 * Overrides Comparable's compareTo() method to compare the task lists by name.
	 * Returns an integer less than 0, 0, or greater than 0 if this TaskList's name
	 * is less than, equal to, or greater than the current instance of the TaskList.
	 * 
	 * @param taskList to be compared to the current instance of taskList
	 * @return an integer less than 0, 0, or greater than 0 if this taskLists name
	 *         is less than, equal to, or greater than the current instance of the
	 *         task list
	 */
	@Override
	public int compareTo(TaskList taskList) {
		return this.getTaskListName().compareToIgnoreCase(taskList.getTaskListName());
	}

}

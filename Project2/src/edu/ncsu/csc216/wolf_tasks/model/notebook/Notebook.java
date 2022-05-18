package edu.ncsu.csc216.wolf_tasks.model.notebook;

import java.io.File;

import edu.ncsu.csc216.wolf_tasks.model.io.NotebookWriter;
import edu.ncsu.csc216.wolf_tasks.model.tasks.AbstractTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.ActiveTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;
import edu.ncsu.csc216.wolf_tasks.model.util.ISortedList;
import edu.ncsu.csc216.wolf_tasks.model.util.SortedList;

/**
 * Creates and controls an instance of notebook. Each notebook has a name, a
 * ISortedList of Task Lists, a current task list, and an active task list. The
 * class has the functionality to save a notebook to a file, add task lists to
 * the notebook, get task lists from the notebook, and set the current task
 * list. The specific functionality for the active task list is delegated to the
 * ActiveTaskList class.
 * 
 * @author Anika Bhadriraju
 *
 */
public class Notebook {

	/** String with notebook name */
	private String notebookName;

	/** Boolean stating whether the notebook has changed since its last save */
	private boolean isChanged;

	/** ISortedList of type task list */
	private ISortedList<TaskList> taskLists;

	/** The current task list in the notebook */
	private AbstractTaskList currentTaskList;

	/** The active task list in the notebook */
	private ActiveTaskList activeTaskList;

	/** Final string of the name of the active tasks list */
	public static final String ACTIVE_TASKS_NAME = "Active Tasks";

	/**
	 * Constructs a Notebook with the given name. The taskLists field is constructed
	 * in this constructor and the activeTaskList field is constructed and set to
	 * the currentTaskList. isChanged is initialized to true.
	 * 
	 * 
	 * @param notebookName name of notebook
	 * @throws IllegalArgumentException if the notebook name is empty, null, or
	 *                                  matches the name of the active task list
	 */
	public Notebook(String notebookName) {
		setNotebookName(notebookName);
		setChanged(true);
		taskLists = new SortedList<TaskList>();
		activeTaskList = new ActiveTaskList();
		currentTaskList = activeTaskList;

	}

	/**
	 * Saves the current instance of the notebook to a file. Works closely with
	 * writeNotebookFile() method in NotebookWriter class. When the notebook is
	 * saved, the isChanged boolean of the current Notebook is updated to false.
	 * 
	 * @param file file to save the Noteboook to
	 */
	public void saveNotebook(File file) {
		try {
			NotebookWriter.writeNotebookFile(file, this.getNotebookName(), this.taskLists);
		} catch (Exception e) {
			throw new IllegalArgumentException("File cannot be saved.");
		}
		setChanged(false);
	}

	/**
	 * Returns the name of the notebook.
	 * 
	 * @return name of the notebook
	 */
	public String getNotebookName() {
		return notebookName;
	}

	/**
	 * Sets the name of the Notebook.
	 * 
	 * @param name name of the notebook to set
	 * @throws IllegalArgumentException if the name of the Notebook is null or empty
	 */
	private void setNotebookName(String name) {
		if ("".equals(name) || name == null || ACTIVE_TASKS_NAME.equals(name)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.notebookName = name;
	}

	/**
	 * Returns whether the Notebook has been returned since the last save.
	 * 
	 * @return true if the Notebook has been saved since the last save.
	 */
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * Sets the boolean to whether the Notebook has been changed since the last
	 * save.
	 * 
	 * @param isChanged whether the Notebook has been changed
	 */
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	/**
	 * Adds the TaskList to the list of task lists and sets this new task list to
	 * the current task list. The isChanged boolean is initialized to true. Error
	 * checking of the added task list is done in the TaskList class.
	 * 
	 * @param taskList task list to add to list of Task Lists
	 * @throws IllegalArgumentException if the task lists name is the same as the
	 *                                  active task list or of an existing task list
	 */
	public void addTaskList(TaskList taskList) {
		if (ACTIVE_TASKS_NAME.equals(taskList.getTaskListName())) {
			throw new IllegalArgumentException("Invalid name.");
		} else {
			for (int i = 0; i < this.taskLists.size(); i++) {
				if (this.taskLists.get(i).getTaskListName().equals(taskList.getTaskListName())) {
					throw new IllegalArgumentException("Invalid name.");
				}
			}
		}
		this.taskLists.add(taskList);
		setCurrentTaskList(taskList.getTaskListName());
		// currentTaskList = taskList;
		setChanged(true);
		getActiveTaskList();
	}

	/**
	 * Returns an Array of type String of the task list names. The name of the
	 * active task list is listed first.
	 * 
	 * @return Array of task list names
	 */
	public String[] getTaskListsNames() {
		String[] names = new String[taskLists.size() + 1];
		names[0] = ACTIVE_TASKS_NAME;
		for (int i = 1; i <= taskLists.size(); i++) {
			names[i] = taskLists.get(i - 1).getTaskListName();
		}
		return names;
	}

	/**
	 * Private helper method that rebuilds the active task list when changes occur
	 * in the active tasks within other task lists. The active task list is sorted
	 * based on the order of the active tasks in the other task lists.
	 * 
	 */
	private void getActiveTaskList() {
		activeTaskList.clearTasks();
		for (int i = 0; i < taskLists.size(); i++) {
			TaskList temp = taskLists.get(i);
			for (int j = 0; j < temp.getTasks().size(); j++) {
				if (temp.getTasks().get(j).isActive()) {
					activeTaskList.addTask(temp.getTasks().get(j));
				}
			}
		}

	}

	/**
	 * Sets the current task list to the AbstractTaskList with the name provided in
	 * the parameter of the method. If the task list is not found, the current task
	 * list is set to the active task list.
	 * 
	 * @param taskListName name of task list to set to the current task list
	 */
	public void setCurrentTaskList(String taskListName) {
		TaskList temp = null;
		if (taskListName.equals(ACTIVE_TASKS_NAME)) {
			currentTaskList = activeTaskList;
		}
		for (int i = 0; i < taskLists.size(); i++) {
			if (taskListName.equals(taskLists.get(i).getTaskListName())) {
				temp = taskLists.get(i);
			}
		}

		if (temp == null) {
			currentTaskList = activeTaskList;
		} else {
			currentTaskList = temp;
		}
		getActiveTaskList();
	}

	/**
	 * Gets the current task list within the Notebook.
	 * 
	 * @return current task list of type AbstractTaskList
	 */
	public AbstractTaskList getCurrentTaskList() {
		return this.currentTaskList;
	}

	/**
	 * Edits the current task list in the notebook. Editing is done by removing the
	 * task list and adding it back to the task lists field after editing. isChanged
	 * is set to true.
	 * 
	 * @param taskListName task list to edit
	 * @throws IllegalArgumentException if the current task list is an active task
	 *        list and if the name of the task list is the same as the active task
	 *        list name or another task list in the notebook.
	 */
	public void editTaskList(String taskListName) {
		if (currentTaskList instanceof ActiveTaskList) {
			throw new IllegalArgumentException("The Active Tasks list may not be edited.");
		} else if ("Active Tasks".equals(taskListName)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		for (int i = 0; i < this.taskLists.size(); i++) {
			if (this.taskLists.get(i).getTaskListName().equals(taskListName)) {
				throw new IllegalArgumentException("Invalid name.");
			}
		}

		for (int i = 0; i < this.taskLists.size(); i++) {
			if (this.taskLists.get(i).getTaskListName().equals(getCurrentTaskList().getTaskListName())) {
				taskLists.remove(i);
			}
		}
		getCurrentTaskList().setTaskListName(taskListName);

		TaskList temp = (TaskList) getCurrentTaskList();
		taskLists.add(temp);
		setChanged(true);
	}

	/**
	 * Removes the current task list and sets the currentTaskList field to the
	 * active task list.
	 * 
	 * @throws IllegalArgumentException if the current task list is the active task
	 *                                  list
	 */
	public void removeTaskList() {
		if (getCurrentTaskList().getTaskListName().equals(ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("The Active Tasks list may not be deleted.");
		}

		for (int i = 0; i < this.taskLists.size(); i++) {
			if (this.taskLists.get(i).getTaskListName().equals(currentTaskList.getTaskListName())) {
				this.currentTaskList = activeTaskList;
				taskLists.remove(i);
			}
		}
		setChanged(true);
	}

	/**
	 * Adds the given task to the task list. If the task is active, the method adds
	 * the task to the active task list.
	 * 
	 * @param task task to add to the task list
	 */
	public void addTask(Task task) {
		if (currentTaskList instanceof TaskList) {
			currentTaskList.addTask(task);
			if (task.isActive()) {
				getActiveTaskList();
			}
		}
		setChanged(true);
	}

	/**
	 * Edits the task by updating the fields of tasks with the given parameters. If
	 * the task is active, the method updates the fields of the task in the active
	 * task list.
	 * 
	 * @param idx             index of priority of the task
	 * @param taskName        name of the task
	 * @param taskDescription description/details of the task
	 * @param recurring       true if the task is recurring
	 * @param active          true if the task is active
	 */
	public void editTask(int idx, String taskName, String taskDescription, boolean recurring, boolean active) {
		if (currentTaskList != activeTaskList) {
			currentTaskList.getTask(idx).setActive(active);
			currentTaskList.getTask(idx).setTaskName(taskName);
			currentTaskList.getTask(idx).setTaskDescription(taskDescription);
			currentTaskList.getTask(idx).setRecurring(recurring);
			getActiveTaskList();
			setChanged(true);
		}
	}
}

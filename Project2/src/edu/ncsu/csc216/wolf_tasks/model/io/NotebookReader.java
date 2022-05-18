package edu.ncsu.csc216.wolf_tasks.model.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_tasks.model.notebook.Notebook;
import edu.ncsu.csc216.wolf_tasks.model.tasks.AbstractTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;

/**
 * Reads Tasks from a file and creates Task Lists and a Notebook with the file
 * contents. Delegates the creation of the Task, Task List, and Notebook to the
 * respective constructors. Each input file can contain only a single Notebook.
 * 
 * @author Anika Bhadriraju
 * @author Yash Agarwal
 *
 */
public class NotebookReader {

	/**
	 * Reads a Notebook from a file and returns a notebook object with the contents
	 * from the file. Invalid task lists or tasks that cannot be constructed are
	 * ignored. Error checking within tasks and task lists is delegated to the Task,
	 * TaskList, and Notebook classes. TaskLists are ordered in sorted order to the
	 * Notebook's ISortedList field. Notebook cannot be created if the first line of
	 * the file does not begin with "! ".
	 * 
	 * 
	 * @param file File to read notebook from
	 * @return Notebook notebook created with file records
	 * @throws IllegalArgumentException if the file does not exist and if the first
	 *                                  line of the file does not begin with "! "
	 */
	public static Notebook readNotebookFile(File file) {
		Scanner fileReader;
		try {
			// Create a file scanner to read the file
			fileReader = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		// Creates a string that reads all contents of the file
		String fileContents = "";
		while (fileReader.hasNextLine()) {
			fileContents += fileReader.nextLine() + "\n";
		}
		fileReader.close();

		Scanner fileContentReader = new Scanner(fileContents);

		// Reads the notebook name
		String notebookName = fileContentReader.nextLine();
		if (!("! ".equals(notebookName.substring(0, 2)))) {
			fileContentReader.close();
			throw new IllegalArgumentException("Unable to load file.");
		}
		notebookName = notebookName.substring(2);

		// Creates a string with all notebook contents without the name of the notebook
		String line = "";
		while (fileContentReader.hasNextLine()) {
			line += fileContentReader.nextLine();
			if (fileContentReader.hasNextLine()) {
				line += "\n";
			}
		}

		fileContentReader.close(); // Close scanner

		// Creates new scanner to read individual task lists
		Scanner taskListReader = new Scanner(line);
		taskListReader.useDelimiter("\\r?\\n?[#]");
		String currentTaskList = "";
		// Creates a new notebook to return with the given notebook name
		Notebook tempNotebook = new Notebook(notebookName);

		try {
			while (taskListReader.hasNext()) {
				currentTaskList = taskListReader.next();
				TaskList tempTaskList = processTaskList(currentTaskList);
				if (tempTaskList != null) {
					tempNotebook.addTaskList(tempTaskList);
				}
			}
		} catch (Exception e) {
			// Do nothing
		}

		taskListReader.close(); // Close all scanners
		tempNotebook.setCurrentTaskList("Active Tasks");
		return tempNotebook;

	}

	/**
	 * Reads and processes a singular String from an input file and returns a newly
	 * constructed Task List object. Delegates the construction of the individual
	 * tasks to the processTask() method. Task Lists that cannot be constructed are
	 * ignored.
	 * 
	 * @param line String to be processed as a task list
	 * @return TaskList from the contents of the string
	 */
	private static TaskList processTaskList(String line) {
		Scanner taskReader = new Scanner(line);
		// Reads the information of the task list (task list name and completed amount)
		String taskListInformation = taskReader.nextLine();

		// Creates a temporary scanner that parses the task list information
		Scanner tempScanner = new Scanner(taskListInformation);
		tempScanner.useDelimiter(",");
		String taskListName = tempScanner.next();
		taskListName = taskListName.substring(1);

		int tempCompletedCount = tempScanner.nextInt();

		tempScanner.close();

		// Creates a task list with variables
		TaskList currentTaskList = null;
		try {
			currentTaskList = new TaskList(taskListName, tempCompletedCount);
		} catch (Exception e) {
			taskReader.close(); // Close scanner
			return null;
		}

		String line2 = "";
		while (taskReader.hasNextLine()) {
			line2 += taskReader.nextLine();
			if (taskReader.hasNextLine()) {
				line2 += "\n";
			}
		}
		taskReader.close();
		// Creates a new scanner to read specific tasks
		Scanner newTaskReader = new Scanner(line2);
		newTaskReader.useDelimiter("\\r?\\n?[*]");
		String currentTask = "";

		// Reads a specific task
		while (newTaskReader.hasNext()) {
			currentTask = newTaskReader.next();
			Task tempTask = processTask(currentTaskList, currentTask);
			if (tempTask != null) {
				currentTaskList.addTask(tempTask);
			}
		}
		newTaskReader.close(); // Close scanner

		return currentTaskList;
	}

	/**
	 * Reads and processes a singular String from an input file and returns a newly
	 * constructed Task object. The String is received from the processTaskList()
	 * method. Tasks that cannot be constructed are ignored
	 * 
	 * @param taskList taskList to register to the task
	 * @param line     String to be processed as a task
	 * @return Task from the contents of the string
	 */
	private static Task processTask(AbstractTaskList taskList, String line) {
		Scanner taskToken = new Scanner(line);
		// Reads first line of task to get task name, recurring, and active status
		String taskInformation = taskToken.nextLine();

		// Creates a new scanner to read first line of task
		Scanner readTaskInfo = new Scanner(taskInformation);
		readTaskInfo.useDelimiter(",");
		// Creates temp variables to set
		boolean tempIsRecurring = false;
		boolean tempIsActive = false;
		String temp1 = "";
		String temp2 = "";

		// Reads the title of the file
		String title = readTaskInfo.next();
		title = title.substring(1);

		// Parses the string values of whether recurring or active
		if (readTaskInfo.hasNext()) {
			temp1 = readTaskInfo.next();
		}
		if (readTaskInfo.hasNext()) {
			temp2 = readTaskInfo.next();
		}
		// Assigns those string values to temp booleans
		if ("recurring".equals(temp1) || "recurring".equals(temp2)) {
			tempIsRecurring = true;
		}
		if ("active".equals(temp1) || "active".equals(temp2)) {
			tempIsActive = true;
		}

		readTaskInfo.close();
		// Parses through for description of task
		String description = "";
		while (taskToken.hasNextLine()) {
			description += taskToken.nextLine();
			if (taskToken.hasNextLine()) {
				description += "\n";
			}
		}

		description = description.trim();
		Task temp;
		try {
			temp = new Task(title, description, tempIsRecurring, tempIsActive);
			taskToken.close();
			return temp;
		} catch (Exception e) {
			return null;
		}

	}

}

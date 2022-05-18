package edu.ncsu.csc216.wolf_tasks.model.io;

import java.io.File;
import java.io.PrintStream;

import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;
import edu.ncsu.csc216.wolf_tasks.model.util.ISortedList;

/**
 * Writes a Notebook with a List of Tasks to a file. The Task's toString()
 * method is used to properly format output. Any errors or exceptions are
 * handled and an IAE is thrown. Class allows to save records to a file
 * specified by the user.
 * 
 * @author Anika Bhadriraju
 * @author Yash Agarwal
 *
 */
public class NotebookWriter {

	/**
	 * Writes the given ISortedList of task lists to a file specified by the user.
	 * Uses Task's toString() method to create the properly formatted output for a
	 * task. Called in Notebook's saveNotebookToFile method.
	 * 
	 * @param file      file to write task lists to
	 * @param name      name of the notebook
	 * @param taskLists ISortedList of task lists to write to file
	 */
	public static void writeNotebookFile(File file, String name, ISortedList<TaskList> taskLists) {
		try {
			PrintStream fileWriter = new PrintStream(file);
			fileWriter.print("! " + name + "\n");
			for (int i = 0; i < taskLists.size(); i++) {
				fileWriter.print("# " + taskLists.get(i).getTaskListName() + ","
						+ String.valueOf(taskLists.get(i).getCompletedCount() + "\n"));
				for (int j = 0; j < taskLists.get(i).getTasks().size(); j++) {
					fileWriter.print(taskLists.get(i).getTasks().get(j).toString() + "\n");
				}
			}
			fileWriter.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}

}

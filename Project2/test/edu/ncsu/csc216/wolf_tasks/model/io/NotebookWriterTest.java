package edu.ncsu.csc216.wolf_tasks.model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import edu.ncsu.csc216.wolf_tasks.model.notebook.Notebook;
import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;

/**
 * Test class for NotebookWriter
 * 
 * @author Yash Agarwal
 * @author Anika Bhadriraju
 *
 */
public class NotebookWriterTest {

	/**
	 * Tests the NotebookWriter class with a valid notebook.
	 */
	@Test
	public void testNotebookWriterValid() {

		// Creates a valid temporary notebook
		Notebook tempNotebook = new Notebook("Anika's Notebook");
		TaskList tempTaskList = new TaskList("To Do List", 0);
		Task task = new Task("Clean room", "Dust and vacuum", false, false);
		tempTaskList.addTask(task);
		tempNotebook.addTaskList(tempTaskList);

		assertEquals(1, tempTaskList.getTasks().size());

		File name = new File("test-files/writer_test.txt");

		try {
			tempNotebook.saveNotebook(name);
		} catch (IllegalArgumentException e) {
			fail("Unable to save file.");
		}
		checkFiles("test-files/writer_expected.txt", "test-files/writer_test.txt");

	}

	/**
	 * Helper method to compare two files for the same contents. Used in
	 * NotebookWriter tests to ensure that files are written properly.
	 * 
	 * @param expected expected output
	 * @param actual   actual output
	 */
	private void checkFiles(String expected, String actual) {
		try (Scanner expScanner = new Scanner(new File(expected));
				Scanner actScanner = new Scanner(new File(actual));) {

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}

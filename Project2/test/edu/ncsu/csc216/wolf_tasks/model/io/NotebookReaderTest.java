package edu.ncsu.csc216.wolf_tasks.model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.wolf_tasks.model.notebook.Notebook;

/**
 * Test class for NotebookReader class in the WolfTasks project.
 * 
 * @author Yash Agarwal
 * @author Anika Bhadriraju
 *
 */
public class NotebookReaderTest {

	/** Valid issue records */
	private final String validTestFile = "test-files/notebook1.txt";

	/**
	 * Resets notebook1.txt for use in other tests.
	 * @throws IOException if file cannot be read
	 */
	@Before
	public void setUp() throws Exception {
		// Reset notebook1.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "notebook1_starter.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "notebook1.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests readNotebookFile() for a valid test file.
	 */
	@Test
	public void test() {
		try {
			File temp = new File(validTestFile);
			Notebook tempNotebook = NotebookReader.readNotebookFile(temp);

			assertEquals("School", tempNotebook.getNotebookName());
			assertEquals(4, tempNotebook.getTaskListsNames().length);

			// Checks that the names of the task lists are correct
			assertEquals("CSC 216", tempNotebook.getTaskListsNames()[1]);
			assertEquals("CSC 226", tempNotebook.getTaskListsNames()[2]);
			assertEquals("Habits", tempNotebook.getTaskListsNames()[3]);

		} catch (Exception e) {
			fail("Unexpected error reading " + validTestFile);
		}
	}
}

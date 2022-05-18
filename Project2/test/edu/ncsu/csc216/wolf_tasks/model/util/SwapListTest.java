package edu.ncsu.csc216.wolf_tasks.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

/**
 * Test class for SwapList
 * 
 * @author Yash Agarwal
 * @author Anika Bhadriraju
 *
 */
public class SwapListTest {

	/**
	 * Tests the SwapList class's add method
	 */
	@Test
	public void testAddSwapList() {
		// Create SwapList
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		// Tests adding null element
		assertThrows(NullPointerException.class, () -> list.add(null));

		// Add fields to list
		list.add("A");
		list.add("B");
		list.add("C");

		// Check if fields were added correctly
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("C", list.get(2));
		assertEquals(3, list.size());

		// Attempts to add once capacity is reached
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("G");
		list.add("H");
		list.add("I");
		list.add("J");
		assertEquals(10, list.size());
		
		list.add("K");

	}

	/**
	 * Tests the SwapList class's add and remove method
	 */
	@Test
	public void testRemoveSwapList() {
		// Create SwapList
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		// Add fields to list
		list.add("A");
		list.add("B");
		list.add("C");
		assertEquals(3, list.size());

		// Tests remove at out of bounds location
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));

		// Tests valid removal of elements
		assertEquals("B", list.remove(1));
		assertEquals("A", list.remove(0));
		assertEquals("C", list.remove(0));
		assertEquals(0, list.size());

		// Tests remove on empty list
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));

	}

	/**
	 * Test SwapList's moveToFront, and moveToBack
	 */
	@Test
	public void testFrontAndBackMovers() {
		// Create SwapList
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		// Add fields to list
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		assertEquals(5, list.size());
		// Move A to back and see if all indicies move accordingly
		list.moveToBack(0);
		assertEquals("B", list.get(0));
		assertEquals("C", list.get(1));
		assertEquals("D", list.get(2));
		assertEquals("E", list.get(3));
		assertEquals("A", list.get(4));

		// Move E to front and see if all indicies move accordingly
		list.moveToFront(3);
		assertEquals("E", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("C", list.get(2));
		assertEquals("D", list.get(3));
		assertEquals("A", list.get(4));
	}

	/**
	 * Test SwapList's moveUp method. Tests moving up at beginning, middle, and end
	 * of list.
	 */
	@Test
	public void testUpMovers() {
		// Create SwapList
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		// Add fields to list
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		assertEquals(5, list.size());

		// Try move up in middle of list
		list.moveUp(1);
		assertEquals("B", list.get(0));
		assertEquals("A", list.get(1));
		assertEquals("C", list.get(2));
		assertEquals("D", list.get(3));
		assertEquals("E", list.get(4));

		// Try move up at beginning of list
		list.moveUp(0);
		assertEquals("B", list.get(0));
		assertEquals("A", list.get(1));
		assertEquals("C", list.get(2));
		assertEquals("D", list.get(3));
		assertEquals("E", list.get(4));

		// Try move up at end of list
		list.moveUp(4);
		assertEquals("B", list.get(0));
		assertEquals("A", list.get(1));
		assertEquals("C", list.get(2));
		assertEquals("E", list.get(3));
		assertEquals("D", list.get(4));

	}

	/**
	 * Test SwapList's moveDown method. Tests moving down at beginning, middle, and
	 * end of list.
	 */
	@Test
	public void testDownMovers() {
		// Create SwapList
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		// Add fields to list
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		assertEquals(5, list.size());

		// Try move down in middle of list
		list.moveDown(1);
		assertEquals("A", list.get(0));
		assertEquals("C", list.get(1));
		assertEquals("B", list.get(2));
		assertEquals("D", list.get(3));
		assertEquals("E", list.get(4));

		// Try move up at beginning of list
		list.moveDown(0);
		assertEquals("C", list.get(0));
		assertEquals("A", list.get(1));
		assertEquals("B", list.get(2));
		assertEquals("D", list.get(3));
		assertEquals("E", list.get(4));

		// Try move down at end of list
		list.moveDown(4);
		assertEquals("C", list.get(0));
		assertEquals("A", list.get(1));
		assertEquals("B", list.get(2));
		assertEquals("D", list.get(3));
		assertEquals("E", list.get(4));

	}
}

package edu.ncsu.csc216.wolf_tasks.model.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

/**
 * Test class for SortedList
 * 
 * @author Yash Agarwal
 * @author Anika Bhadriraju
 *
 */
public class SortedListTest {

	/**
	 * Tests the SortedList class
	 */
	@Test
	public void testSortedListTest() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
	}

	/**
	 * Tests the SortedList add() method
	 */
	@Test
	public void addTest() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());

		list.add("apple");
		list.add("balloon");
		assertEquals(2, list.size());

		// Attempts to add duplicate element
		Exception e = assertThrows(IllegalArgumentException.class, () -> list.add("apple"));
		assertEquals("Cannot add duplicate element.", e.getMessage());

		// Attempts to add null element
		Exception e1 = assertThrows(NullPointerException.class, () -> list.add(null));
		assertEquals("Cannot add null element.", e1.getMessage());

		// Checks if elements are sorted
		list.add("amazing");
		list.add("bottle");
		list.add("car");
		list.add("bus");
		assertEquals(6, list.size());

		assertEquals("amazing", list.get(0));
		assertEquals("apple", list.get(1));
		assertEquals("balloon", list.get(2));
		assertEquals("bottle", list.get(3));
		assertEquals("bus", list.get(4));
		assertEquals("car", list.get(5));

	}

	/**
	 * Tests the SortedList remove() method
	 */
	@Test
	public void removeTest() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());

		list.add("apple");
		list.add("balloon");
		list.add("amazing");
		list.add("bottle");
		list.add("car");
		list.add("bus");

		// Attempts to remove at invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(6));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));

		// Attempts to remove at valid index
		assertEquals("apple", list.remove(1));
		assertEquals(5, list.size());
		assertEquals("amazing", list.remove(0));
		assertEquals(4, list.size());
		assertEquals("car", list.remove(list.size() - 1));
		assertEquals(3, list.size());

	}

	/**
	 * Tests the SortedList contains() method
	 */
	@Test
	public void containTest() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());

		list.add("apple");
		list.add("balloon");
		list.add("amazing");
		list.add("bottle");
		list.add("car");
		list.add("bus");

		// Checks if contains for elements in list
		assertTrue(list.contains("apple"));
		assertTrue(list.contains("amazing"));
		assertTrue(list.contains("car"));

		// Checks if contains for elements not in list
		assertFalse(list.contains("temp"));
	}

}

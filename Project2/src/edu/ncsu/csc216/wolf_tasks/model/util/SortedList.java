package edu.ncsu.csc216.wolf_tasks.model.util;

/**
 * Maintains a LinkedList-like list of ListNodes that are sorted in order of
 * index. Implements the ISortedList interface.
 * 
 * @author yash
 *
 * @param <E> Datatype to be carried through the SortedList when referenced.
 */
public class SortedList<E extends Comparable<E>> implements ISortedList<E> {

	/** The first item in the SortedList */
	private ListNode front;
	/** The number of elements inside of the SortedList */
	private int size;

	/**
	 * Constructor for the SortedList
	 */
	public SortedList() {
		front = null;
		size = 0;
	}

	/**
	 * Adds the element to the list in sorted order.
	 * 
	 * @param element element to add
	 * @throws NullPointerException     if element is null
	 * @throws IllegalArgumentException if element is a duplicate
	 */
	@Override
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}

		if (size() == 0) {
			front = new ListNode(element, null);
		} else {
			if (contains(element)) {
				throw new IllegalArgumentException("Cannot add duplicate element.");
			}

			ListNode current = front;
			int counter = -1;
			
			for (int i = 0; i < size(); i++) {
				if (current.data.compareTo(element) == 0) {
					throw new IllegalArgumentException("Cannot add duplicate element.");
				}

				if (current.data.compareTo(element) < 0) {
					current = current.next;
					counter++;
				} else if (current.data.compareTo(element) > 0) {
					break;
				}
			}
			current = front;

			if (counter == -1) {
				front = new ListNode(element, front);
			} else {
				for (int i = 0; i < counter; i++) {
					current = current.next;
				}
				ListNode temp = null;
				if (counter == size - 1) {
					temp = new ListNode(element, null);

				} else {
					temp = new ListNode(element, current.next);
				}
				current.next = temp;
			}

		}
		size++;

	}

	/**
	 * Returns the element from the given index. The element is removed from the
	 * list.
	 * 
	 * @param idx index to remove element from
	 * @return element at given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public E remove(int idx) {
		checkIndex(idx);

		E removedValue = null;

		if (idx == 0) { // Special Case: front of list
			removedValue = front.data;
			front = front.next;
		} else {
			ListNode current = front;
			for (int i = 0; i < idx - 1; i++) {
				current = current.next;
			}
			removedValue = current.next.data;
			current.next = current.next.next;
		}
		size--;

		return removedValue;
	}

	/**
	 * Check the index in the list. The index must be non-negative and less than the
	 * size of the list.
	 * 
	 * @param idx int index to be verified
	 * @throws IndexOutofBoundsException if the index is greater than size-1 or less
	 *                                   than 0
	 */
	private void checkIndex(int idx) {
		if (idx > size - 1 || idx < 0) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}

	}

	/**
	 * Returns true if the element is in the list.
	 * 
	 * @param element element to search for
	 * @return true if element is found
	 */
	@Override
	public boolean contains(E element) {
		ListNode current = front;
		for (int i = 0; i < size(); i++) {
			if (current.data.equals(element)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Returns the element at the given index.
	 * 
	 * @param idx index of the element to retrieve
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public E get(int idx) {
		checkIndex(idx);

		ListNode current = front;
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}

		return current.data;

	}

	/**
	 * Returns the number of elements in the list.
	 * 
	 * @return number of elements in the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * A single node inside of the SortedList class. Stores the information for the
	 * item at this index and the next ListNode
	 */
	private class ListNode {

		/**
		 * The data stored at the specific index in the SortedList inside this ListNode
		 */
		public E data;
		/**
		 * The next ListNode inside the SortedList. Null if it is the last element in
		 * the list.
		 */
		private ListNode next;

		/**
		 * The constructor of ListNode using parameters for both the information that
		 * will be stored at this location and the ListNode that comes next in the
		 * SortedList.
		 * 
		 * @param data The data that will be stored at this ListNode in the SortedList.
		 * @param next The next ListNode in the SortedList.
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

	}

}

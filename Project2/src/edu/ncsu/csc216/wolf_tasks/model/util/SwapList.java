package edu.ncsu.csc216.wolf_tasks.model.util;

/**
 * Uses an array of type E to manipulate the indicies in the array to create
 * ArrayList-like functionality. Can adjust the location of indicies by moving
 * them forward or back an index or to the front or back of the list.
 * 
 * @author Yash Agarwal
 * @author Anika Bhadriraju
 *
 * @param <E> Datatype to be carried through the SwapList when referenced.
 */
public class SwapList<E> implements ISwapList<E> {

	/** Constant int for the starting capacity of the SwapList */
	private static final int INITIAL_CAPACITY = 10;
	/** List of type E */
	private E[] list;
	/** int which tracks the size of the list */
	private int size;

	/**
	 * Constructor for the SwapList. Sets the size field of the SwapList to zero.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public SwapList() {
		list = (E[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}

	/**
	 * Adds the element to the end of the list. Null elements are not able to be
	 * added. If capacity is reached with addition of element, checkCapacity method
	 * is called to increase list capacity. Size field is incremented.
	 * 
	 * @param element element to add
	 * @throws NullPointerException     if element is null
	 * @throws IllegalArgumentException if element cannot be added
	 */
	@Override
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		checkCapacity(size + 1);
		try {
			list[size()] = element;
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid index.");
		}
		size++;
	}

	/**
	 * Checks if the addition of an element causes the list to exceed capacity. If
	 * addition of element causes capacity to exceed, doubles the capacity of the
	 * array.
	 * 
	 * @param sizeAfterAddition size of the list after the addition is made
	 * @SuppressWarnings ignores the unchecked type casting error
	 */
	@SuppressWarnings("unchecked")
	private void checkCapacity(int sizeAfterAddition) {
		if (sizeAfterAddition > list.length) {
			E[] temp = (E[]) new Object[size * 2];
			for (int i = 0; i < list.length; i++) {
				temp[i] = list[i];
			}
			list = temp;
		}
	}

	/**
	 * Returns the element from the given index. The element is removed from the
	 * list. Delegates the checking of the idx parameter to the checkIndex() method.
	 * 
	 * @param idx index to remove element from
	 * @return element at given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public E remove(int idx) {
		checkIndex(idx);
		
		E temp = list[idx];
		for (int i = idx; i < size; i++) {
			list[i] = list[i + 1];
		}
		list[size - 1] = null;
		size--;
		return temp;
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
		if (idx >= size  || idx < 0) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}

	/**
	 * Moves the element at the given index to index-1. If the element is already at
	 * the front of the list, the list is not changed. Delegates the checking of the
	 * idx parameter to the checkIndex() method.
	 * 
	 * @param idx index of element to move up
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public void moveUp(int idx) {
		checkIndex(idx);
		if (!(idx == 0)) {
			E temp = list[idx - 1];
			list[idx - 1] = list[idx];
			list[idx] = temp;
		}

	}

	/**
	 * Moves the element at the given index to index+1. If the element is already at
	 * the end of the list, the list is not changed. Delegates the checking of the
	 * idx parameter to the checkIndex() method.
	 * 
	 * @param idx index of element to move down
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public void moveDown(int idx) {
		checkIndex(idx);
		if (!(idx == size - 1)) {
			E temp = list[idx + 1];
			list[idx + 1] = list[idx];
			list[idx] = temp;
		}

	}

	/**
	 * Moves the element at the given index to index 0. If the element is already at
	 * the front of the list, the list is not changed. Delegates the checking of the
	 * idx parameter to the checkIndex() method.
	 * 
	 * @param idx index of element to move to the front
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public void moveToFront(int idx) {
		checkIndex(idx);
		if (!(idx == 0)) {
			E temp = list[idx];
			list[idx] = null;
			for (int i = idx; i > 0; i--) {
				list[i] = list[i - 1];
			}
			list[0] = temp;
		}
	}

	/**
	 * Moves the element at the given index to size-1. If the element is already at
	 * the end of the list, the list is not changed. Delegates the checking of the
	 * idx parameter to the checkIndex() method.
	 * 
	 * @param idx index of element to move to the back
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public void moveToBack(int idx) {
		checkIndex(idx);
		if (!(idx == size - 1)) {
			E temp = list[idx];
			list[idx] = null;
			for (int i = idx; i < size - 1; i++) {
				list[i] = list[i + 1];
			}
			list[size - 1] = temp;
		}

	}

	/**
	 * Returns the element at the given index. Delegates the checking of the idx
	 * parameter to the checkIndex() method.
	 * 
	 * @param idx index of the element to retrieve
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds for the list
	 */
	@Override
	public E get(int idx) {
		checkIndex(idx);
		return list[idx];
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

}

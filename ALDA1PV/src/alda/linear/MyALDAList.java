package alda.linear;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.*;

import java.util.Iterator;

public class MyALDAList<E> implements ALDAList<E> {

	private static class Node<E> {

		E element;
		Node<E> next;

		public Node(E element) {
			this.element = element;
		}

		public String toString() {
			return element.toString();
		}
	}

	private Node<E> head;
	private Node<E> tail;

	// Collections that implement the Iterable interface must provide a method named
	// iterator
	// that returns an object of type Iterator.
	@Override
	public Iterator<E> iterator() {

		return new SLLIterator();
	}

	// Reference https://www.cs.cmu.edu/~tcortina/15-121sp10/Unit04B.pdf
	private class SLLIterator implements Iterator<E> {
		private Node<E> nodePtr = head;
		private Node<E> prevPtr;
		private Node<E> prev2Ptr;
		private boolean okToRemove = false; // Man kan bara tabort om man går via next först

		@Override
		public boolean hasNext() {
			return nodePtr != null; // Returnerar true så länge nodePtr inte är null

		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			if (nodePtr == null)
				throw new NoSuchElementException();
			E result = nodePtr.element;
			// Flyttar fram referencerna ett steg framåt
			prev2Ptr = prevPtr;
			prevPtr = nodePtr;
			nodePtr = nodePtr.next;
			okToRemove = true;
			return result;
		}

		@Override
		public void remove() {
			if (!okToRemove)
				throw new IllegalStateException();
			if (prev2Ptr == null)
				head = nodePtr;
			else
				prev2Ptr.next = nodePtr; 
			prevPtr = prev2Ptr;
			okToRemove = false;
		}
	}

	@Override // KLAR
	public void add(E element) {
		if (head == null) {
			head = new Node<E>(element);
			tail = head;
		} else {
			tail.next = new Node<E>(element);
			tail = tail.next;
		}
	}

	@Override // KLAR
	public void add(int index, E element) {
		Node<E> nodeToAdd = new Node<E>(element);
		int counter = 0;

		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}

		else if (index == 0) {
			nodeToAdd.next = head;
			head = nodeToAdd;

		} else if (index == size()) { // FrÃ¥ga om denna pÃ¥ handledning?

			for (Node<E> temp = head; temp != null; temp = temp.next) {
				if (index == counter + 1) {
					nodeToAdd.next = temp.next;
					temp.next = nodeToAdd;
				}
				counter++;

			}
			tail = nodeToAdd;
		} else {
			for (Node<E> temp = head; temp != null; temp = temp.next) {
				if (index == counter + 1) {
					nodeToAdd.next = temp.next;
					temp.next = nodeToAdd;
				}
				counter++;

			}
		}
	}

	// "The remove method can be executed in one "next" reference change"
	// Removing the last item is trickier, because we have
	// to find the next-to-last item, change its next link to null, and then update
	// the link that
	// maintains the last node.

	// Case 1: Index out of bounds
	// Case 2: FÃ¶rsta elementet borttaget
	// Case 3: Sista elementet borttaget
	// Case 4: Element i mitten borttaget

	@Override
	public E remove(int index) {
		if (index < 0 || index >= this.size())
			throw new IndexOutOfBoundsException("Index=" + index);

		Node<E> temp1 = head;
		for (int i = 0; i < i - 2; i++) {
			temp1 = temp1.next;
		}
		Node<E> temp2 = temp1.next;
		temp1.next = temp2.next;

		return temp2.element;
	}

	@Override
	public boolean remove(E element) {

		for (Node<E> temp = head; temp != null; temp = temp.next) {
			if (temp.element == element || temp.element.equals(element)) {
				int idx = indexOf(element);
				remove(idx);
				return true;
			}
		}
		return false;

		// for(Node<E> temp = head; temp!=null; temp=temp.next) {
		// if(temp.element.equals(element) || temp.element == element) {
		//
		// }
		// }

	}

	@Override // KLAR
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		Node<E> nodeToGet = head;
		for (int i = 0; i < index; i++) {
			nodeToGet = nodeToGet.next;
		}
		return nodeToGet.element;
	}

	@Override // KLAR
	public boolean contains(E element) {

		// int index = indexOf(element); // Enklare variant som anvÃ¤nder indexOf
		// return index != -1;

		for (Node<E> temp = head; temp != null; temp = temp.next) {
			if (temp.element == element || temp.element.equals(element)) {
				return true;
			}
		}
		return false;
	}

	@Override // KLAR
	public int indexOf(E element) {
		int counter = 0;
		for (Node<E> temp = head; temp != null; temp = temp.next) {
			if (temp.element == element || temp.element.equals(element)) {
				return counter;
			}
			counter++;
		}
		return -1;
	}

	@Override // KLAR
	public void clear() {
		head = null;
		tail = null;
	}

	@Override // KLAR
	public int size() {

		int size = 0;

		if (head == null || head.equals(null)) {
			return 0;
		} else {
			for (Node<E> temp = head; temp != null; temp = temp.next) {
				size++;
			}
			return size;
		}
	}

	public String toString() {
		String s = "[";
		for (Node<E> temp = head; temp != null; temp = temp.next) {
			if (temp.equals(tail) || temp == tail) {
				return s += temp.element + "]";
			} else {
				s += temp.toString() + ", ";
			}
		}
		return s + "]";
	}

}

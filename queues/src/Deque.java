
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node<Item> first;
	private Node<Item> last;
	private int size;

	public Deque() {

	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		verifyNull(item);
		if (size == 0) {
			firstInsert(item);
			return;
		}

		Node<Item> oldFirst = first;
		first = new Node<>(item);
		first.next = oldFirst;
		oldFirst.prev = first;
		size++;
	}

	public void addLast(Item item) {
		verifyNull(item);
		if (size == 0) {
			firstInsert(item);
			return;
		}

		Node<Item> oldLast = last;
		last = new Node<>(item);
		last.prev = oldLast;
		oldLast.next = last;
		size++;
	}

	public Item removeFirst() {
		if (size == 0) {
			throw new NoSuchElementException("dequesAndRandomizedQueues.Deque is empty");
		}

		size--;
		Node<Item> oldFirst = first;
		first = oldFirst.next;
		if (first != null) {
			first.prev = null;
		}
		return oldFirst.item;
	}

	public Item removeLast() {
		if (size == 0) {
			throw new NoSuchElementException("dequesAndRandomizedQueues.Deque is empty");
		}

		size--;
		Node<Item> oldLast = last;
		last = oldLast.prev;
		if (last != null) {
			last.next = null;
		}
		return oldLast.item;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator<>(first);
	}

	private void firstInsert(Item item) {
		first = new Node<>(item);
		last = first;
		size++;
	}

	private void verifyNull(Item item) {
		if (item == null) {
			throw new NullPointerException("Item cannot be null");
		}
	}

	public static void main(String[] args) {

	}

	private static class DequeIterator<Item> implements Iterator<Item> {

		private Node<Item> node;

		DequeIterator(Node<Item> node) {
			if (node == null) {
				return;
			}

			this.node = new Node<>(node.item);
			this.node.next = node.next;
			this.node.prev = node.prev;
		}

		@Override
		public boolean hasNext() {
			return node != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			Item item = node.item;
			if (hasNext()) {
				node = node.next;
			}
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}
	}

	private static class Node<Item> {
		private Node<Item> next;
		private Node<Item> prev;
		private Item item;

		Node(Item item) {
			this.item = item;
		}
	}
}
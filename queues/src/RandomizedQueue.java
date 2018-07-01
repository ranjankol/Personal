import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int size;
	private Object[] items = new Object[10];

	public RandomizedQueue() {

	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException("item cannot be null");
		}

		if (size == items.length) {
			resize(items.length * 2);
		}

		items[size++] = item;
	}

	public Item dequeue() {
		validateSize();

		int index = StdRandom.uniform(size);
		Item item = (Item) items[index];
		items[index] = null;
		items[index] = items[size - 1];
		items[size - 1] = null;
		size--;

		if (size > 0 && size == items.length / 4) {
			resize(items.length / 4);
		}

		return item;
	}

	public Item sample() {
		validateSize();
		return (Item) items[StdRandom.uniform(size)];
	}

	@Override
	public Iterator<Item> iterator() {
		return new RandomizedIterator<>(this);
	}

	private void validateSize() {
		if (size == 0) {
			throw new NoSuchElementException("Queue is empty");
		}
	}

	private void resize(int capacity) {
		Object[] copy = new Object[capacity];
		for (int i = 0; i < size; i++) {
			copy[i] = items[i];
		}
		items = copy;
	}

	public static void main(String[] args) {

	}

	private static class RandomizedIterator<Item> implements Iterator<Item> {

		private int n = 0;
		private Object[] items;

		RandomizedIterator(RandomizedQueue<Item> queue) {
			items = new Object[queue.size];
			for (int i = queue.size - 1; i >= 0; i--) {
				int index = i == 0 ? 0 : StdRandom.uniform(i);
				Object m;
				Object k;

				if (items[i] != null) {
					m = items[i];
				} else {
					m = queue.items[i];
				}

				if (items[index] != null) {
					k = items[index];
				} else {
					k = queue.items[index];
				}

				items[i] = k;
				items[index] = m;
			}
		}

		@Override
		public boolean hasNext() {
			return n < items.length;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			return (Item) items[n++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
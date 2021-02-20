package hr.fer.oprpp1.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer> {
	private List<Integer> elements;
	private List<ListDataListener> observers;

	public PrimListModel() {
		elements = new ArrayList<>();
		observers = new ArrayList<>();
		elements.add(1);
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		observers.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		observers.remove(l);
	}

	public void next() {
		int nextPrime = elements.get(elements.size() - 1);
		boolean isPrime = false;

		while (!isPrime) {
			isPrime = true;
			nextPrime++;

			boolean first = true;
			for (int i : elements) {
				if (first) {
					first = false;
					continue;
				}

				if (nextPrime % i == 0) {
					isPrime = false;
				}
			}
		}

		int pos = elements.size();
		elements.add(nextPrime);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener l : observers) {
			l.intervalAdded(event);
		}
	}

}

package com.megapon.arcoupon.common.util.cs;

import java.util.LinkedList;

public class Queue extends LinkedList {
	private int waitingThreads = 0;

	public synchronized void insert(Object obj) {
		addLast(obj);
		notify();
	}

	public synchronized Object remove() {
		if (isEmpty()) {
			try {
				waitingThreads++;
				wait();
			} catch (InterruptedException ex) {
				Thread.interrupted();
			}

			waitingThreads--;
		}

		return removeFirst();
	}

	public boolean isEmpty() {
		return ((size() - waitingThreads) <= 0);
	}
}
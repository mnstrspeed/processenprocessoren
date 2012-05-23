package nl.tomsanders.processenprocessoren.emulator;

import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class IoBuffer extends Observable {
	private ConcurrentLinkedQueue<Character> inBuffer;
	private ConcurrentLinkedQueue<Character> outBuffer;

	public IoBuffer() {
		this.inBuffer = new ConcurrentLinkedQueue<Character>();
		this.outBuffer = new ConcurrentLinkedQueue<Character>();
	}

	public Character pollIn() {
		return this.inBuffer.poll();
	}

	public void addIn(char in) {
		this.inBuffer.add(in);
	}

	public Character pollOut() {
		return this.outBuffer.poll();
	}

	public void addOut(char in) {
		this.outBuffer.add(in);
		this.setChanged();
		this.notifyObservers();
	}
}

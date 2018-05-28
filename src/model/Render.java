package model;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import interfaces.Renderer;

public class Render extends Thread{
	private int semath = 0;
	private ConcurrentLinkedQueue<Renderer> once;
	private ArrayList<Renderer> mainPull;
	public Render() {
		once = new ConcurrentLinkedQueue<Renderer>();
		mainPull = new ArrayList<Renderer>();
		setDaemon(true);
	}
	public synchronized boolean isSleep() {
		return semath == 1;
	}
	public synchronized void goSleep() {
		semath = 1;
	}
	public synchronized void wakeup() { 
		semath = 0;
		notifyAll();
	}
	public synchronized void addMain(Renderer th) {
		mainPull.add(th);
	}
	public synchronized void delMain(Renderer th) {
		mainPull.remove(th);
	}
	public synchronized void addOnce(Renderer th) {
		once.add(th);
	}
	public void run() {
		try {
			while (true) {
				synchronized (this) {
					while (semath > 0)
						wait();
				}
				for (int i = 0; i < mainPull.size(); i++)
					mainPull.get(i).render();
				while(!once.isEmpty()) {
					once.poll().render();
				}
				sleep(100);
			}
		}catch (InterruptedException e) {
		}
	}
}

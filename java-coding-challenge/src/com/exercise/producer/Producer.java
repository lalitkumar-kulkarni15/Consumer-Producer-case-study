package com.exercise.producer;

import com.exercise.model.PriceUpdate;
import com.exercise.producer.Producer;
import com.exercise.regulator.LoadHandler;

public class Producer extends Thread {
	
	private LoadHandler loadHandler;
	
	public Producer(LoadHandler loadHandler) {
		this.loadHandler = loadHandler;
	}
	
	@Override
	public void run() {
		System.out.println("Run inside the producer is called.");
		try {
			generateUpdates();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void generateUpdates() throws InterruptedException{
		for (int i = 1; i < 10000000; i++) {
			System.out.println("Stock set start");
			Thread.sleep(5000);
			System.out.println("-----------------------");
			loadHandler.receive(new PriceUpdate("Apple", 97.85));
			loadHandler.receive(new PriceUpdate("Google", 160.71));
			loadHandler.receive(new PriceUpdate("Facebook", 91.66));
			loadHandler.receive(new PriceUpdate("Google", 160.73));
			loadHandler.receive(new PriceUpdate("Facebook", 91.71));
			loadHandler.receive(new PriceUpdate("Google", 160.76));
			loadHandler.receive(new PriceUpdate("Apple", 97.85));
			loadHandler.receive(new PriceUpdate("Google", 160.71));
			loadHandler.receive(new PriceUpdate("Facebook", 91.63));
			System.out.println("-----------------------");
			System.out.println("Stock set over");
		}
	}

}

package com.exercise.regulator;

import java.util.LinkedList;
import java.util.Queue;
import com.excercise.consumer.Consumer;
import com.exercise.model.PriceUpdate;

public class LoadHandler {
	
	private static final int MAX_PRICE_UPDATES = 100;
	
	private final Consumer consumer;
	
	public LoadHandler (Consumer consumer) {
		this.consumer = consumer;
		Scheduler scheduler = new Scheduler(consumer);
		scheduler.startConsumerFeedJobThread();
	}
	
	private static Queue<PriceUpdate> priceUpdateQueue = new LinkedList<>();
	
	public static Queue<PriceUpdate> getPriceUpdateQueue() {
		return priceUpdateQueue;
	}

	public static void setPriceUpdateQueue(Queue<PriceUpdate> priceUpdateQueue) {
		LoadHandler.priceUpdateQueue = priceUpdateQueue;
	}

	public void receive(PriceUpdate priceUpdate) {
		
		if(null!=priceUpdate) {
			
			if(priceUpdateQueue.size()<MAX_PRICE_UPDATES) {
				priceUpdateQueue.add(priceUpdate);
				System.out.println("Stock price added successfully.");
			} else {
				priceUpdateQueue.poll();
				System.out.println("Stock price polled successfully.");
				priceUpdateQueue.add(priceUpdate);
				System.out.println("Stock price added after poll successfully.");
			}
			
		}

	}
	
}

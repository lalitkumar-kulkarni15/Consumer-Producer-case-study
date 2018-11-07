package com.exercise.regulator;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import com.excercise.consumer.Consumer;
import com.exercise.model.PriceUpdate;

public class Scheduler {

	private Consumer consumer;

	private static Deque<PriceUpdate> stocksSentToConsumerList = new LinkedList<>();
	
	private static LocalDateTime lastSentDateTime;

	public static void setLastSentDateTime(LocalDateTime lastSentDateTime) {
		Scheduler.lastSentDateTime = lastSentDateTime;
	}

	public Scheduler(final Consumer consumer) {
		this.consumer = consumer;
	}
	
	public void startConsumerFeedJobThread() {

		final Runnable stockReguRunnable = getRunnableForstockRegu();
		final Thread stockRegulatorThread = new Thread(stockReguRunnable);
		stockRegulatorThread.start();

	}
	
	private Runnable getRunnableForstockRegu() {

		final Runnable runnable = () -> {

			try {
				sendRegulatedStcksToCnsmr();
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}

		};

		return runnable;
	}

	private void sendRegulatedStcksToCnsmr() throws InterruptedException {

		System.out.println("----Starting the scheduler for fetch in scheduler----");
		while (true) {

			askThreadToSleep();
			System.out.println("Got the stock price collection from main queue");
			Queue<PriceUpdate> priceUpdateQueue = LoadHandler.getPriceUpdateQueue();
			System.out.println("Price update queue size after fetching ::"+priceUpdateQueue.size());
			List<PriceUpdate> priceUpdateQueueCopy = new LinkedList<>(priceUpdateQueue);
			System.out.println("Copied the stock collection into new queue");

				System.out.println("Going to check for already sent stock prices");
			
				System.out.println("-----Printing stocks inside stocksSentToConsumerList------");
				stocksSentToConsumerList.forEach(System.out::println);
				System.out.println("-----------------------------------------------------------");
				System.out.println("-----Printing stocks inside priceUpdateQueueCopy------");
				priceUpdateQueueCopy.forEach(System.out::println);
				System.out.println("-----------------------------------------------------------");
				
				if(stocksSentToConsumerList.size()>0) {
				priceUpdateQueueCopy.removeIf((StockPredicate.isStockEqual(stocksSentToConsumerList).or(RemoveOlderStcksPredicate.isStockEqual(lastSentDateTime))));
				} else{
					priceUpdateQueueCopy.removeIf((StockPredicate.isStockEqual(stocksSentToConsumerList)));
				}
				System.out.println("-----Printing stocks inside priceUpdateQueueCopy after filtering------");
				priceUpdateQueueCopy.forEach(System.out::println);
				System.out.println("-----------------------------------------------------------");
				
				System.out.println("Got filtered stock list with size ::"+priceUpdateQueueCopy.size());
				this.consumer.send(priceUpdateQueueCopy);
				
				if(null!=priceUpdateQueueCopy && priceUpdateQueueCopy.size()>0) {
					savePrevConsumdStcks(priceUpdateQueueCopy);
				}

		}
	}
	
	private void askThreadToSleep() throws InterruptedException {
		System.out.println("----Scheduler sleeping for 1 sec----");
		Thread.sleep(1000);
		System.out.println("----Scheduler woke up after 1 sec----");
	}
	
	private void savePrevConsumdStcks(final List<PriceUpdate> priceUpdateListToSend) {

		System.out.println("Clearing the stock sent to consumer list before adding the price update list");
		stocksSentToConsumerList.clear();
		stocksSentToConsumerList.addAll(priceUpdateListToSend);
		setLastSentDateTime(stocksSentToConsumerList.peekLast().getLocalDateTime());
		System.out.println("Added the stock price sent list to the collection for next cycle comparison.");
		System.out.println("Last sent timestamp is :"+lastSentDateTime);
	}

}

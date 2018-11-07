package com.excercise.consumer;

import java.util.List;
import com.exercise.model.PriceUpdate;

/**
 * Please do not change the Consumer. 
 * 
 */
public class Consumer {

	public void send(List<PriceUpdate> priceUpdates) {
		System.out.println("List of price updates received at consumer class is size : "+priceUpdates.size());
		priceUpdates.forEach(System.out::println);
	}

}

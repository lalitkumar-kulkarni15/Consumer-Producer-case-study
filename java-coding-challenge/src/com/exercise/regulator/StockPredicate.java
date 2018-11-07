package com.exercise.regulator;

import java.util.HashSet;
import java.util.Queue;
import java.util.function.Predicate;
import com.exercise.model.PriceUpdate;

public class StockPredicate {
	
	public static Predicate<PriceUpdate> isStockEqual(Queue<PriceUpdate> stocksSentToConsumerList){
		
		return new HashSet<>(stocksSentToConsumerList)::contains;
		
	}

}

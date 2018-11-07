package com.exercise.regulator;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import com.exercise.model.PriceUpdate;

public class RemoveOlderStcksPredicate {
	
public static Predicate<PriceUpdate> isStockEqual(LocalDateTime localDateTime){
		System.out.println("Inside is stock equal localdateTime is ::"+localDateTime);
		return p->p.getLocalDateTime().isBefore(localDateTime);
		
	}


}

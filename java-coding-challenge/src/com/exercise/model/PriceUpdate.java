package com.exercise.model;

import java.time.LocalDateTime;

public class PriceUpdate {
	
	private final String companyName;
	
	private final double price;
	
	private LocalDateTime localDateTime;
	
	public PriceUpdate(String companyName, double price) {
		this.companyName = companyName;
		this.price = price;
		this.localDateTime = LocalDateTime.now();
	}
	
	public String getCompanyName() {
		return this.companyName;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	
	@Override
	public String toString() {
		return companyName + " - " + price +" - "+localDateTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(null==obj) {
			return false;
		}
		
		else if(null != obj && obj instanceof PriceUpdate) {
			
			final PriceUpdate priceUpdate = (PriceUpdate) obj;
			
				 if(null!=priceUpdate 
						
					&& priceUpdate.getCompanyName().equalsIgnoreCase(this.getCompanyName()) 
					&& priceUpdate.getPrice()==(this.getPrice()) 
					&& (priceUpdate.getLocalDateTime().equals(this.getLocalDateTime()))) {
				
					System.out.println("Equals returning true");
					
				return true;
			}
			
		} 
		
		System.out.println("Equals returning false");
		return false;
		
	}
	
	@Override
    public int hashCode() {
        
		int hash =    this.companyName.hashCode() 
					* Double.valueOf(this.price).hashCode() 
					* this.localDateTime.hashCode();
        return hash;
    }
}

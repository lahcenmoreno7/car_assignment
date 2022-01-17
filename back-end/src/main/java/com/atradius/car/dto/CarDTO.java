package com.atradius.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Home
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
	

	private String model;

	private String name;
	
	private String make;

	private String version;

	private int yearRelease;

	private double price;
	
	private double fuel;

	private double annualCost;

}

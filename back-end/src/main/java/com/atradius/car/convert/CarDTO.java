package com.atradius.car.convert;

import com.atradius.car.model.Car;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Home
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CarDTO {

	private long id;
	
	private String model;

	private String name;

	private String make;

	private String version;

	private int yearRelease;

	private double price;

	private double fuel;

	private double annualCost;

	public CarDTO(Car car) {

		this.model = car.getModel();
		this.name = car.getName();
		this.make = car.getMake();
		this.version = car.getVersion();
		this.yearRelease = car.getYearRelease();
		this.price = car.getPrice();
		this.fuel = car.getFuel();
		this.annualCost = car.getAnnualCost();
		
	}

}

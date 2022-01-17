package com.atradius.car.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Home
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

	private double price;

	private int distanceMonth;
}

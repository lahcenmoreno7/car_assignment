package com.atradius.car.service;

import org.springframework.data.domain.Pageable;

import com.atradius.car.convert.CarDTO;
import com.atradius.car.model.Car;
import com.atradius.car.utils.CarResponse;

/**
 * 
 * @author Home
 *
 */
public interface CarService {
	
	Car createCar(Car Car);

	CarResponse getAllCars(Pageable pageable);
	
	CarResponse getCarsBySerachKeyword(String keyword, Pageable pageRequest);

	CarResponse getLowestAnnualCost(String distanceMonth,String price) throws Exception;
	
	
}

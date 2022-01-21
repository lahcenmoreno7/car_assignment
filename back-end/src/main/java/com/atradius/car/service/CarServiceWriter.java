package com.atradius.car.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atradius.car.model.Car;
import com.atradius.car.repository.CarRepository;

/**
 * 
 * @author Home
 *
 */
@Component
public class CarServiceWriter implements ItemWriter<Car> {
	
	private static final Logger log = LoggerFactory.getLogger(CarServiceWriter.class);
	
	@Autowired
	CarRepository contriesRepository;

	/**
	 * Save cars item into database
	 */
	@Override
	public void write(List<? extends Car> carItems) throws Exception {
		
		log.info("save all items cars from cvs file to H2 Database");
		
		carItems.forEach((item) -> contriesRepository.save(item));
		
	}

}

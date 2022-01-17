package com.atradius.car.configuration;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.atradius.car.model.Car;
import com.atradius.car.reader.CarReader;
import com.atradius.car.service.CarServiceWriter;

/**
 *  Batch job and batch step configurations
 * @author Home
 *
 */
@Configuration
@EnableBatchProcessing
public class CarBatchConfig {
	
	private static final Logger log = LoggerFactory.getLogger(CarBatchConfig.class);

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	
	@Autowired
	CarReader carsReader;
	
	@Autowired
	CarServiceWriter carServiceWriter;
	
	/**
	 * insert batch car job
	 * @return  job
	 */
	@Bean
	public Job createCasJob() {
		
		log.info("Start car insert job.");
		
		return jobBuilderFactory.get("CarsInsertJob")
				.incrementer(new RunIdIncrementer())
				.flow(carsCreateStep()).end().build();
	}

	/**
	 * Create car Step
	 * @return step builder factory for cars
	 */
	@Bean
	public Step carsCreateStep() {
		
		log.info("Car create step");
		
		return stepBuilderFactory.get("CarInsertStep")
				.<Car, Car> chunk(3)
				.reader(carReaderBatch())
				.writer(carServiceWriter)
				.build();
	}

	/**
	 * Method converts to a java bean object using the ItemReader interface
	 * @return car itemReader
	 */
	private ItemReader<? extends Car> carReaderBatch() {
		
		log.info("Cars reader batch from ressources");
		
		 Resource[] resources = null;
		    ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();  
		    
		    
		    try {
		        resources = patternResolver.getResources("classpath:file/cars.csv");
		        
		    } catch (IOException e) {
		        log.error("Error during reading CSV file " , e.getMessage() );
		    }

		    MultiResourceItemReader<Car> reader = new MultiResourceItemReader<>();
		    reader.setResources(resources);
		    reader.setDelegate(carsReader);
		    
		    return reader;
		
		
	}
}

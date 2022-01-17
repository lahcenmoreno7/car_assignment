package com.atradius.car.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

import com.atradius.car.constant.CarConstant;
import com.atradius.car.model.Car;


/**
 * 
 * @author Home
 *
 */
@Component
public class CarReader extends FlatFileItemReader<Car> implements ItemReader<Car>{
	
	private static final Logger log = LoggerFactory.getLogger(CarReader.class);
	
	public CarReader() {
		setLinesToSkip(CarConstant.LIGNE_TO_SKIP); 
		setLineMapper(carMapper());
	}

	
	/**
	 * Car mapper
	 * @return DefaultLineMapper for Cars
	 */
	private LineMapper<Car> carMapper() {
		
		log.info("Car wrapper filed");

		DefaultLineMapper<Car> defaultLineMapper = new DefaultLineMapper<Car>();
		
		DelimitedLineTokenizer delimitedLineTokenizer =new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames( CarConstant.CAR_HEADER );
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		
		BeanWrapperFieldSetMapper<Car> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<Car>();
		beanWrapperFieldSetMapper.setTargetType(Car.class);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		
		
		return defaultLineMapper;
	}

}

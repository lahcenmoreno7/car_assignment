package com.atradius.car.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.atradius.car.convert.CarDTO;
import com.atradius.car.model.Car;
import com.atradius.car.repository.CarRepository;
import com.atradius.car.service.CarService;
import com.atradius.car.utils.CarLowestResponse;
import com.atradius.car.utils.CarResponse;
import com.atradius.car.utils.ModelMapperUtil;

/**
 * 
 * @author Home
 *
 */
@Service
public class CarServiceImpl implements CarService {

	private static final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);

	CarRepository carRepository;
	

	public CarServiceImpl(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Override
	public Car createCar(Car car) {

		return carRepository.save(car);
	}

	/**
	 * Get All Cars
	 */
	@Override
	public CarResponse getAllCars(Pageable pageable) {

		CarResponse response = new CarResponse();

		log.info("Finding all cars");

		Page<Car> cars = carRepository.findAll(pageable);

		List<Car> listOfCars = cars.getContent();

		List<Car> content = listOfCars.stream().map(car -> car).collect(Collectors.toList());

		List<CarDTO> carDtoList = ModelMapperUtil.mapAll(content, CarDTO.class);

		response.setCars(carDtoList);
		response.setPageNo(cars.getNumber());
		response.setPageSize(cars.getSize());
		response.setTotalElements(cars.getTotalElements());
		response.setTotalPages(cars.getTotalPages());

		return response;
	}
	

	/**
	 * Get cars by keyword
	 */
	public CarResponse getCarsBySerachKeyword(String keyword, Pageable pageable) {

		CarResponse response = new CarResponse();

		log.info("Finding car by keyword ");

		Page<Car> cars = carRepository.searchkeyword(keyword, pageable);

		List<Car> listOfCars = cars.getContent();

		List<Car> content = listOfCars.stream().map(car -> car).collect(Collectors.toList());

		List<CarDTO> carDtoList = ModelMapperUtil.mapAll(content, CarDTO.class);
		
		response.setCars(carDtoList);
		response.setPageNo(cars.getNumber());
		response.setPageSize(cars.getSize());
		response.setTotalElements(cars.getTotalElements());
		response.setTotalPages(cars.getTotalPages());

		return response;
	}

	/**
	 * Get lowest car annual cost
	 * 
	 * @throws Exception
	 */
	@Override
	public CarResponse getLowestAnnualCost(String distanceMonth,String price) throws Exception {

		CarResponse response = new CarResponse();
		List<Car> listOfCars = new ArrayList<Car>();

		log.info("Lowest car total annual cost");

		List<CarLowestResponse> listCarsRecommended = carRepository.getLowestAnnualCost(distanceMonth,price);

		CarLowestResponse carRecommended = listCarsRecommended.stream().findFirst()
				.orElseThrow(() -> new Exception());
		
		listOfCars.add(convertDTOToCar(carRecommended));
		
		List<CarDTO> carDtoList = ModelMapperUtil.mapAll(listOfCars, CarDTO.class);
		
		response.setCars(carDtoList);
		
		return response;
	}

	private Car convertDTOToCar(CarLowestResponse carRecommended) {
		
		Car carToConvert = new Car();
		carToConvert.setName(carRecommended.getName());
		carToConvert.setMake(carRecommended.getMake());
		carToConvert.setModel(carRecommended.getModel());
		carToConvert.setPrice(carRecommended.getPrice());
		carToConvert.setVersion(carRecommended.getVersion());
		carToConvert.setFuel(carRecommended.getFuel());
		carToConvert.setYearRelease(carRecommended.getYear_release());
		carToConvert.setAnnualCost(carRecommended.getAnnualCost());
		
		return carToConvert;
	}



}

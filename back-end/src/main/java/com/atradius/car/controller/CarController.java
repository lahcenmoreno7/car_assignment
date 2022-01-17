package com.atradius.car.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atradius.car.constant.CarConstant;
import com.atradius.car.convert.CarDTO;
import com.atradius.car.exception.CarApplicationException;
import com.atradius.car.model.Car;
import com.atradius.car.service.CarService;
import com.atradius.car.utils.CarResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * 
 * @author Home
 *
 */
@RestController
@RequestMapping("/cars")
public class CarController {

	private static final Logger log = LoggerFactory.getLogger(CarController.class);

	CarService carService;

	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	/**
	 * Method to get all cars
	 * @return
	 */
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CarResponse> getall() {

		Sort sort = CarConstant.DEFAULT_SORT_DIRECTION.equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(CarConstant.DEFAULT_SORT_BY).ascending()
				: Sort.by(CarConstant.DEFAULT_SORT_BY).descending();

		Pageable pageable = PageRequest.of(CarConstant.DEFAULT_PAGE_NUMBER, CarConstant.DEFAULT_PAGE_SIZE, sort);

		return new ResponseEntity<CarResponse>(carService.getAllCars(pageable), HttpStatus.OK);
	}

	/**
	 * Method to search all cars or to search by keyword
	 */
	@Operation(summary = "Search cars by keyword", description = "Code search by %keyword% format", tags = { "Cars" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get cars by keyword", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Car.class)))),
			@ApiResponse(responseCode = "400", description = "Invalid keyword") })
	@GetMapping(value = "/search/{filter}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CarResponse> getCarsBySerachKeyword(@PathVariable("filter") String filter) {

		log.info("Searching car by keyword ");

		CarResponse response = new CarResponse();

		Sort sort = CarConstant.DEFAULT_SORT_DIRECTION.equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(CarConstant.DEFAULT_SORT_BY).ascending()
				: Sort.by(CarConstant.DEFAULT_SORT_BY).descending();

		Pageable pageable = PageRequest.of(CarConstant.DEFAULT_PAGE_NUMBER, CarConstant.DEFAULT_PAGE_SIZE, sort);

		try {

			if (filter == null || StringUtils.isEmpty(filter)) {

				response = carService.getAllCars(pageable);

				if (response.getPageSize() == 0) {
					throw new CarApplicationException("List empty, no car found");
				}

			} else {

				response = carService.getCarsBySerachKeyword(filter, pageable);
				if (response.getPageSize() == 0) {
					throw new CarApplicationException(Long.valueOf(filter));
				}

			}

			return new ResponseEntity<CarResponse>(response, HttpStatus.OK);

		} catch (CarApplicationException ex) {
			throw new CarApplicationException(HttpStatus.BAD_REQUEST);
		}

	}

	@Operation(summary = "lowest total annual cost", description = "lowest total annual cost", tags = { "Cars" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "recommend car with the lowest total annual cost over a period four (4) years.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Car.class)))),
			@ApiResponse(responseCode = "400", description = "No lowest car found") })
	@GetMapping(value = "/lowest/{distanceMonth}/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CarResponse> getLowestAnnualCost(@PathVariable("distanceMonth") String distanceMonth,
			@PathVariable("price") String price) throws Exception {

		log.info("calculate the lowest total annual cost over a period four (4) years");

		CarResponse response = carService.getLowestAnnualCost(distanceMonth,price);

		return new ResponseEntity<CarResponse>(response, HttpStatus.OK);

	}

	/**
	 * Method to add new car
	 */
	@Operation(summary = "Add new car", description = "Add new car", tags = { "Cars" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Add new car", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Car.class)))),
			@ApiResponse(responseCode = "400", description = "Error saving car") })
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> saveCar(@RequestBody Car car) {

		log.info("Saving a new car");

		return new ResponseEntity<Car>(carService.createCar(car), HttpStatus.CREATED);

	}

}

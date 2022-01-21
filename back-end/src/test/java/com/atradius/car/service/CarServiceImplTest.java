package com.atradius.car.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.atradius.car.constant.CarConstant;
import com.atradius.car.convert.CarDTO;
import com.atradius.car.model.Car;
import com.atradius.car.repository.CarRepository;
import com.atradius.car.serviceImpl.CarServiceImpl;
import com.atradius.car.utils.CarLowestResponse;
import com.atradius.car.utils.CarResponse;

/**
 * 
 * @author Home
 *
 */
@SpringBootTest
public class CarServiceImplTest {

	@Autowired
	private CarRepository carRepository;

	private CarService carService;

	@BeforeEach
	public void setUp() {
		carRepository = mock(CarRepository.class);
		carService = new CarServiceImpl(carRepository);
	}

	@DisplayName("Test Spring get list all cars - Service")
	@Test
	void getAllCars() {

		Sort sort = CarConstant.DEFAULT_SORT_DIRECTION.equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(CarConstant.DEFAULT_SORT_BY).ascending()
				: Sort.by(CarConstant.DEFAULT_SORT_BY).descending();

		Pageable pageable = PageRequest.of(CarConstant.DEFAULT_PAGE_NUMBER, CarConstant.DEFAULT_PAGE_SIZE, sort);

		given(carRepository.findAll(pageable)).willReturn(getListOfCars());

		CarResponse carExpected = carService.getAllCars(pageable);

		assertThat(carExpected.getCars().size(), is(3));

	}

	@DisplayName("Test Spring get list cars by year - Service")
	@Test
	void getCarBySearchKeyword_Year() {

		int yearSearch = 2020;

		Sort sort = CarConstant.DEFAULT_SORT_DIRECTION.equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(CarConstant.DEFAULT_SORT_BY).ascending()
				: Sort.by(CarConstant.DEFAULT_SORT_BY).descending();

		Pageable pageable = PageRequest.of(CarConstant.DEFAULT_PAGE_NUMBER, CarConstant.DEFAULT_PAGE_SIZE, sort);

		given(carRepository.searchkeyword(String.valueOf(yearSearch), pageable)).willReturn(getListOfCarsByYear());

		CarResponse carExpected = carService.getCarsBySerachKeyword(String.valueOf(yearSearch), pageable);

		assertThat(carExpected.getCars().size(), is(1));

	}

	@DisplayName("Test Spring get list cars by make - Service")
	@Test
	void whenGetCarBySearchKeyword_Make() {

		String makeSearch = "Citroen";

		Sort sort = CarConstant.DEFAULT_SORT_DIRECTION.equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(CarConstant.DEFAULT_SORT_BY).ascending()
				: Sort.by(CarConstant.DEFAULT_SORT_BY).descending();

		Pageable pageable = PageRequest.of(CarConstant.DEFAULT_PAGE_NUMBER, CarConstant.DEFAULT_PAGE_SIZE, sort);

		given(carRepository.searchkeyword(makeSearch, pageable)).willReturn(getListOfCarsByMake());

		CarResponse carExpected = carService.getCarsBySerachKeyword(makeSearch, pageable);

		assertThat(carExpected.getCars().size(), is(2));

	}
	
	
	
	
	@DisplayName("Test Spring add new car - Service")
	@Test
    public void whenSaveCar_shouldReturnCar() {
		Car car = Car.builder().id(1).make("Honda").model("Fit Hibrid").name("Fit").price(17000).fuel(0.23)
				.annualCost(530.0).yearRelease(2021).build();

        when(carRepository.save(ArgumentMatchers.any(Car.class))).thenReturn(car);

        Car created = carService.createCar(car);

        assertThat(created.getName()).isSameAs(car.getName());
        verify(carRepository).save(car);
    }

	private List<CarLowestResponse> getRecomendedCar(){
		
		List<CarLowestResponse> listRecomended = new ArrayList<CarLowestResponse>();
		
		 return null;
		
	}
	
	private Page<Car> getListOfCars() {

		List<Car> cars = new ArrayList<Car>();

		Car car1 = Car.builder().id(1).make("Honda").model("Fit Hibrid").name("Fit").price(17000).fuel(0.23)
				.annualCost(530.0).yearRelease(2021).build();

		Car car2 = Car.builder().id(1).make("Citroen").model("C3 blue motion").name("C3").price(17000).fuel(0.23)
				.annualCost(530.0).yearRelease(2021).build();

		Car car3 = Car.builder().id(1).make("Citroen").model("C2 Base").name("C2").price(17000).fuel(0.23)
				.annualCost(530.0).yearRelease(2020).build();

		cars.add(car1);
		cars.add(car2);
		cars.add(car3);

		Page<Car> carsPage = new PageImpl<>(cars);

		return carsPage;

	}

	private Page<Car> getListOfCarsByYear() {

		List<Car> cars = new ArrayList<Car>();

		Car car1 = Car.builder().id(1).make("Honda").model("Fit Hibrid").name("Fit").price(17000).fuel(0.23)
				.annualCost(530.0).yearRelease(2021).build();

		cars.add(car1);

		Page<Car> carsPage = new PageImpl<>(cars);

		return carsPage;

	}
	
	private Page<Car> getListOfCarsByMake() {

		List<Car> cars = new ArrayList<Car>();

		Car car2 = Car.builder().id(1).make("Citroen").model("C3 blue motion").name("C3").price(17000).fuel(0.23)
				.annualCost(530.0).yearRelease(2021).build();

		Car car3 = Car.builder().id(1).make("Citroen").model("C2 Base").name("C2").price(17000).fuel(0.23)
				.annualCost(530.0).yearRelease(2020).build();

		cars.add(car2);
		cars.add(car3);

		Page<Car> carsPage = new PageImpl<>(cars);

		return carsPage;

	}
}

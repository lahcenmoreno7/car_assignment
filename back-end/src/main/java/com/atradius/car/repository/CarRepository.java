package com.atradius.car.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.atradius.car.constant.CarConstant;
import com.atradius.car.model.Car;
import com.atradius.car.utils.CarLowestResponse;

/**
 * 
 * @author Home
 *
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
	
	@Query(CarConstant.SEARCH_BY_KEYWORD_QUERY)
	public Page<Car> searchkeyword(String keyword, Pageable pageRequest);
	
	@Query(value = CarConstant.LOWST_ANNUAL_COST_QUERY, nativeQuery = true)
	public List<CarLowestResponse> getLowestAnnualCost(String distanceMonth, String price);
}

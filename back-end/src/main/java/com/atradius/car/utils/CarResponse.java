package com.atradius.car.utils;

import java.util.List;

import com.atradius.car.convert.CarDTO;

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
public class CarResponse {

	private List<CarDTO> cars;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalPages;

}

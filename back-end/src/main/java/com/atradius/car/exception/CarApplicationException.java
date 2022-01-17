package com.atradius.car.exception;

import org.springframework.http.HttpStatus;

public class CarApplicationException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public String param;
	public String  message;
	public long carId;
	
	
	public CarApplicationException(long carId){

        super(String.format("Car is not found with ID '%s'",carId));
    }

	public CarApplicationException(String message) {
		super(message);
		
	}

	public CarApplicationException(Throwable cause) {
		super(cause);
		
	}

	public CarApplicationException(HttpStatus status) {
		super(status.toString());
	}

}

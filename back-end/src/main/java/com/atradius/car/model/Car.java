package com.atradius.car.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author Home
 *
 */
@Entity
@Table(name = "Car",indexes = {@Index(name = "NAME", columnList = "name"),@Index(name = "yearRelease", columnList = "year_release")  })
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Car implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "model")
	private String model;

	@Column(name = "name")
	private String name;
	
	@Column(name = "make")
	private String make;

	@Column(name = "version")
	private String version;

	@Column(name = "year_release")
	private int yearRelease;


	@Column(name = "price")
	private double price;
	
	@Column(name = "fuel")
	private double fuel;

	@Column(name = "annual_cost")
	private double annualCost;
	
	

}

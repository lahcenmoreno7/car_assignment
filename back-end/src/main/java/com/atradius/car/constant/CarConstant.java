package com.atradius.car.constant;

/**
 * Constants class
 */
public class CarConstant {

	public static final int LIGNE_TO_SKIP = 1;

	public static final int DEFAULT_PAGE_NUMBER = 0;

	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final String DEFAULT_SORT_BY = "id";

	public static final String DEFAULT_SORT_DIRECTION = "asc";

	public static final String[] CAR_HEADER = { "id", "model", "name", "make", "version", "year_release", "price",
			"fuel", "annual_cost" };

	public static final String SEARCH_BY_KEYWORD_QUERY = "SELECT c FROM Car c WHERE CONCAT(c.make, ' ', c.name, ' ', c.model, ' ', "
			+ "c.yearRelease) LIKE %?1%";

	public static final String LOWST_ANNUAL_COST_QUERY = "SELECT c.id, c.make, c.name, c.model, c.version, c.year_release,  c.price , c.fuel , (?1 * 12 / c.fuel  * ?2 + c.annual_cost ) AS annualCost FROM "
			+ "Car r INNER JOIN Car c ON r.id  = c.id  ORDER BY annualCost DESC";
	
	
}

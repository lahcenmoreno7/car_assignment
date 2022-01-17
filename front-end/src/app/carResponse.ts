import { Car } from "./car";

export interface carResponse {
    cars : Car[];
	pageNo : number;
	pageSize : number;
	totalElements : number;
	totalPages : number;
  }
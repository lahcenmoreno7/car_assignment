import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Car } from './car';
import { environment } from 'src/environments/environment';
import { carResponse } from './carResponse';
import { SearchFilter } from './search-filter';

@Injectable({providedIn: 'root'})
export class CarService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient){}

   public getCars(filter: string): Observable<carResponse> {
    const headers = new HttpHeaders().set('Accept', 'application/json');
    const params = {
      'filter': filter,
    };

    return this.http.get<carResponse>(`${this.apiServerUrl}/cars/search/${filter}`, {params, headers});
  }

  public getRecomendedCar(distance: string,price : string): Observable<carResponse> {
    const headers = new HttpHeaders().set('Accept', 'application/json');
    const params = {
      'distance': distance,
      'price': price
    };

    return this.http.get<carResponse>(`${this.apiServerUrl}/cars/lowest/${distance}/${price}`, {params, headers});
  }

  public getAllCars(): Observable<carResponse> {
        return this.http.get<carResponse>(`${this.apiServerUrl}/cars/all`);
  }

  public addCar(car: Car): Observable<Car> {
    return this.http.post<Car>(`${this.apiServerUrl}/cars/add`, car);
  }


}

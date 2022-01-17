import { Component, OnInit, ViewChild } from '@angular/core';
import { Car } from './car';
import { CarService } from './car.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { carResponse } from './carResponse';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public carResponse: carResponse;
  public cars: Car[];
  private distance: string;
  private price:string;
  @ViewChild('recomended') recomendedButton;
  
  constructor(private carService: CarService){}

  ngOnInit() {
        this.getAllCars();
  }

  public getAllCars(): void {
    this.carService.getAllCars().subscribe(
      (response: carResponse) => {
        this.carResponse = response;
        console.log(this.carResponse);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
  

  public searchCars(filter: string): void {
    if (filter === "") {
      this.getAllCars();
   }else{
    this.carService.getCars(filter).subscribe(
      (response: carResponse) => {
      this.carResponse = response;
      console.log(this.carResponse);
    },
    (error: HttpErrorResponse) => {
      alert(error.message);
    }
  );
   }
  }

  public getRecomendedCar(recomendedCarForm: NgForm): void {

    this.distance = recomendedCarForm.controls.distance.value;
    this.price = recomendedCarForm.controls.fuel_price.value;
  
    this.carService.getRecomendedCar(this.distance,this.price ).subscribe(
        (response: carResponse) => {
        this.carResponse = response;
           console.log(this.carResponse);
           
           recomendedCarForm.reset;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }

  public onAddCar(addForm: NgForm): void {
    document.getElementById('add-car-form').click();
    this.carService.addCar(addForm.value).subscribe(
      (response: Car) => {
        console.log(response);
        this.searchCars("");
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  public onOpenModal(car: Car, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addCarModal');
    }else if (mode = 'recommended'){
      button.setAttribute('data-target', '#carRecomendedModal');
    }
   container.appendChild(button);
    button.click();
  }



}

import { Component, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SuperHttpClient } from '../super-http-client';

@Component({
  selector: 'app-fetch-secret',
  templateUrl: './fetch-secret.component.html'
})
export class FetchSecretComponent {
  public forecasts: WeatherForecast[] = [];

  constructor(http: SuperHttpClient, @Inject('BASE_URL') baseUrl: string) {
    http.get<WeatherForecast[]>(baseUrl + 'api/weatherforecast/secret', result => this.forecasts = result, _ => { });
//http.get<WeatherForecast[]>(baseUrl + 'api/weatherforecast/secret')
    //  .subscribe(
    //    result => {
    //      this.forecasts = result;
    //    },
    //    error => console.error(error));
  }
}

interface WeatherForecast {
  date: string;
  temperatureC: number;
  temperatureF: number;
  summary: string;
}

import { HttpClient } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { AuthenticationResponse } from "./models/auth/authentication-response";
import { RefreshRequest } from "./models/auth/refresh-request";

@Injectable()
export class SuperHttpClient {
  private readonly http: HttpClient;
  private readonly baseUrl: string;
  private readonly router: Router;

  constructor(http: HttpClient, @Inject('BASE_URL') baseUrl: string, router: Router) {
    this.http = http;
    this.baseUrl = baseUrl;
    this.router = router;
  }

  async get<T>(route: string, onSuccess: (result: T) => void, onError: (error: any) => void) {
    this.http.get<T>(route)
      .subscribe(
        result => onSuccess(result),
        error => {
          if (error.status === 401) {
            const accessToken = localStorage.getItem('access-token');
            const refreshToken = localStorage.getItem('refresh-token');

            if (accessToken !== null && refreshToken !== null) {
              this.http.post<AuthenticationResponse>(this.baseUrl + 'api/auth/refresh', new RefreshRequest(accessToken, refreshToken))
                .subscribe(
                  response => {
                    localStorage.setItem('access-token', response.accessToken);
                    localStorage.setItem('refresh-token', response.refreshToken);

                    this.http.get<T>(route)
                      .subscribe(
                        result => onSuccess(result),
                        error => {
                          if (error.status === 401) {
                            this.router.navigate(['/', 'login']);
                          } else {
                            onError(error);
                          }
                        }
                      )
                  },
                  error => {
                    onError(error);
                  }
                );
            } else {
              this.router.navigate(['/', 'login']);
            }
          } else {
            onError(error);
          }
        });
  }
}

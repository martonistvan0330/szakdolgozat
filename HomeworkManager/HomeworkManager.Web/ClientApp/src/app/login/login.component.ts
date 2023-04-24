import { HttpClient } from '@angular/common/http';
import { Component, Inject, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationRequest } from '../models/auth/authentication-request';
import { AuthenticationResponse } from '../models/auth/authentication-response';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  authRequest: AuthenticationRequest = new AuthenticationRequest();
  rememberMe = false;

  loading = false;

  http: HttpClient;
  baseUrl: string;
  router: Router;

  constructor(http: HttpClient, @Inject('BASE_URL') baseUrl: string, router: Router) {
    this.http = http;
    this.baseUrl = baseUrl;
    this.router = router;
  }

  ngOnInit() {
  }

  onSubmit() {
    this.loading = true;
    this.http.post<AuthenticationResponse>(this.baseUrl + 'api/auth/bearertoken', this.authRequest)
      .subscribe(
        response => {
          this.loading = false;
          localStorage.setItem('logged-in', 'true');
          localStorage.setItem('access-token', response.accessToken);
          localStorage.setItem('refresh-token', response.refreshToken);
          this.router.navigate(['/', 'fetch-secret'])
            .then(() => {
              location.reload();
            });
        },
        error => {
          this.loading = false;
        }
      );
  }
}

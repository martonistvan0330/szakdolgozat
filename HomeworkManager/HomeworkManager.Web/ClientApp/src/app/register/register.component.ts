import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { NewUser } from '../models/auth/new-user';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user: NewUser = new NewUser();

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
    this.http.post(this.baseUrl + 'api/auth/register', this.user)
      .subscribe(
        response => {
          this.loading = false;
          this.router.navigate(['/']);
        },
        error => {
          this.loading = false;
        }
      );
  }
}

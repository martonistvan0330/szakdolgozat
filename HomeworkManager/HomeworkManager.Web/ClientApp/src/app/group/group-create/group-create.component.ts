import { Component, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { NewGroup } from '../../models/new-group';
import { SuperHttpClient } from '../../super-http-client';

@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent {
  group: NewGroup = new NewGroup();
  file?: File;

  loading = false;

  http: SuperHttpClient;
  baseUrl: string;
  router: Router;

  constructor(http: SuperHttpClient, @Inject('BASE_URL') baseUrl: string, router: Router) {
    this.http = http;
    this.baseUrl = baseUrl;
    this.router = router;
  }

  onSubmit() {
    this.loading = true;
    this.http.post<number>(this.baseUrl + 'api/group', this.group,
      result => this.router.navigate(['/', 'groups']),
      error => { }
    )
  }
}

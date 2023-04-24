import { Component, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { GroupHeader } from '../../models/group-header';
import { SuperHttpClient } from '../../super-http-client';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent {
  groups: GroupHeader[] = [];

  loading = true;

  http: SuperHttpClient;
  baseUrl: string;
  router: Router;

  constructor(http: SuperHttpClient, @Inject('BASE_URL') baseUrl: string, router: Router) {
    this.http = http;
    this.baseUrl = baseUrl;
    this.router = router;
  }

  ngOnInit() {
    this.http.get<GroupHeader[]>(
      this.baseUrl + 'api/group',
      result => {
        this.groups = result;
        this.loading = false;
      },
      error => {

      }
    )
  }
}

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FetchSecretComponent } from './fetch-secret.component';

describe('FetchSecretComponent', () => {
  let component: FetchSecretComponent;
  let fixture: ComponentFixture<FetchSecretComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FetchSecretComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FetchSecretComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeadChefDashboardComponent } from './head-chef-dashboard.component';

describe('HeadChefDashboardComponent', () => {
  let component: HeadChefDashboardComponent;
  let fixture: ComponentFixture<HeadChefDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeadChefDashboardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeadChefDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

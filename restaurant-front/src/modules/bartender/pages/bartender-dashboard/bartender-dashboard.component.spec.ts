import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BartenderDashboardComponent } from './bartender-dashboard.component';

describe('BartenderDashboardComponent', () => {
  let component: BartenderDashboardComponent;
  let fixture: ComponentFixture<BartenderDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BartenderDashboardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BartenderDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

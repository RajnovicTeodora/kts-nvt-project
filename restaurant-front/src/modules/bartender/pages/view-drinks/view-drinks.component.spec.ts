import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDrinksComponent } from './view-drinks.component';

describe('ViewDrinksComponent', () => {
  let component: ViewDrinksComponent;
  let fixture: ComponentFixture<ViewDrinksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewDrinksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewDrinksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

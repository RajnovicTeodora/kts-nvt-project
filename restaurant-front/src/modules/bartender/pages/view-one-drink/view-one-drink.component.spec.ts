import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewOneDrinkComponent } from './view-one-drink.component';

describe('ViewOneDrinkComponent', () => {
  let component: ViewOneDrinkComponent;
  let fixture: ComponentFixture<ViewOneDrinkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewOneDrinkComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewOneDrinkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

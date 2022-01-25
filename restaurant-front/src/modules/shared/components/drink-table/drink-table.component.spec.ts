import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrinkTableComponent } from './drink-table.component';

describe('DrinkCardComponent', () => {
  let component: DrinkTableComponent;
  let fixture: ComponentFixture<DrinkTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrinkTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DrinkTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDrinkManagerComponent } from './add-drink-manager.component';

describe('AddDrinkManagerComponent', () => {
  let component: AddDrinkManagerComponent;
  let fixture: ComponentFixture<AddDrinkManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddDrinkManagerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDrinkManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

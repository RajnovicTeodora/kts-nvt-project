import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OneDrinkCardComponent } from './one-drink-card.component';

describe('OneDrinkCardComponent', () => {
  let component: OneDrinkCardComponent;
  let fixture: ComponentFixture<OneDrinkCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OneDrinkCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OneDrinkCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

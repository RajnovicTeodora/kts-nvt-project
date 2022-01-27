import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListNewOrdersComponent } from './list-new-orders.component';

describe('ListNewOrdersComponent', () => {
  let component: ListNewOrdersComponent;
  let fixture: ComponentFixture<ListNewOrdersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListNewOrdersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListNewOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAcceptedOrdersComponent } from './list-accepted-orders.component';

describe('ListAcceptedOrdersComponent', () => {
  let component: ListAcceptedOrdersComponent;
  let fixture: ComponentFixture<ListAcceptedOrdersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListAcceptedOrdersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListAcceptedOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

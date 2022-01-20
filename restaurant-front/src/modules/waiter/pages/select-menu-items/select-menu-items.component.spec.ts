import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectMenuItemsComponent } from './select-menu-items.component';

describe('SelectMenuItemsComponent', () => {
  let component: SelectMenuItemsComponent;
  let fixture: ComponentFixture<SelectMenuItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectMenuItemsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectMenuItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

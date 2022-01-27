import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomizeOrderedItemComponent } from './customize-ordered-item.component';

describe('CustomizeOrderedItemComponent', () => {
  let component: CustomizeOrderedItemComponent;
  let fixture: ComponentFixture<CustomizeOrderedItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomizeOrderedItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomizeOrderedItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

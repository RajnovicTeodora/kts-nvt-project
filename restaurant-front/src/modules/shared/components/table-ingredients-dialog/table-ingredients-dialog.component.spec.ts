import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableIngredientsDialogComponent } from './table-ingredients-dialog.component';

describe('TableIngredientsDialogComponent', () => {
  let component: TableIngredientsDialogComponent;
  let fixture: ComponentFixture<TableIngredientsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableIngredientsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableIngredientsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

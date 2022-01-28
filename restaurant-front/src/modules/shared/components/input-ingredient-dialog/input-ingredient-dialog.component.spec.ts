import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputIngredientDialogComponent } from './input-ingredient-dialog.component';

describe('InputIngredientDialogComponent', () => {
  let component: InputIngredientDialogComponent;
  let fixture: ComponentFixture<InputIngredientDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputIngredientDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InputIngredientDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

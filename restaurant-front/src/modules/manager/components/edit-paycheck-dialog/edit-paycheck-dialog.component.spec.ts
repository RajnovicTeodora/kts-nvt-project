import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPaycheckDialogComponent } from './edit-paycheck-dialog.component';

describe('EditPaycheckDialogComponent', () => {
  let component: EditPaycheckDialogComponent;
  let fixture: ComponentFixture<EditPaycheckDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditPaycheckDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPaycheckDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AfterLogoutComponent } from './after-logout.component';

describe('AfterLogoutComponent', () => {
  let component: AfterLogoutComponent;
  let fixture: ComponentFixture<AfterLogoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AfterLogoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AfterLogoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

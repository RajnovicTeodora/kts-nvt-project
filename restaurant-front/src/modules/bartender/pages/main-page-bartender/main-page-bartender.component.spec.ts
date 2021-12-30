import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainPageBartenderComponent } from './main-page-bartender.component';

describe('MainPageBartenderComponent', () => {
  let component: MainPageBartenderComponent;
  let fixture: ComponentFixture<MainPageBartenderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainPageBartenderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainPageBartenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

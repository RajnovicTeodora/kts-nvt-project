import { TestBed } from '@angular/core/testing';

import { MenuItemWithIngredientsService } from './menu-item-with-ingredients.service';

describe('MenuItemWithIngredientsService', () => {
  let service: MenuItemWithIngredientsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MenuItemWithIngredientsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

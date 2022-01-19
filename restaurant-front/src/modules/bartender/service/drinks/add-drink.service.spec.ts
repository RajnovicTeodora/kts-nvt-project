import { TestBed } from '@angular/core/testing';

import { AddDrinkService } from './add-drink.service';

describe('AddDrinkService', () => {
  let service: AddDrinkService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddDrinkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

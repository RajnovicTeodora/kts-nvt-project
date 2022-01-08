import { TestBed } from '@angular/core/testing';

import { DrinkCardService } from './drink-card.service';

describe('DrinkCardService', () => {
  let service: DrinkCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DrinkCardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

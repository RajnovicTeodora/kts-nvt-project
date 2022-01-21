import { TestBed } from '@angular/core/testing';

import { PaycheckService } from './paycheck.service';

describe('PaycheckService', () => {
  let service: PaycheckService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaycheckService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

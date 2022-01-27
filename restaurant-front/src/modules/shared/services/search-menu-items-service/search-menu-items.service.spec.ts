import { TestBed } from '@angular/core/testing';

import { SearchMenuItemsService } from './search-menu-items.service';

describe('SearchMenuItemsService', () => {
  let service: SearchMenuItemsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SearchMenuItemsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

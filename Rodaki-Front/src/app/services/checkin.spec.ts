import { TestBed } from '@angular/core/testing';

import { CheckinService } from './checkin';

describe('CheckinService', () => {
  let service: CheckinService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheckinService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { PassengerProfileService } from './passenger-profile';

describe('PassengerProfileService', () => {
  let service: PassengerProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PassengerProfileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

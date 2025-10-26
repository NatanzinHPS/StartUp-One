import { TestBed } from '@angular/core/testing';

import { PassengerList } from './passenger-list';

describe('PassengerList', () => {
  let service: PassengerList;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PassengerList);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

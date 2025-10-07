import { TestBed } from '@angular/core/testing';

import { Motorista } from './motorista';

describe('Motorista', () => {
  let service: Motorista;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Motorista);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

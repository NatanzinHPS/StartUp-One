import { TestBed } from '@angular/core/testing';

import { Passageiro } from './passageiro';

describe('Passageiro', () => {
  let service: Passageiro;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Passageiro);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

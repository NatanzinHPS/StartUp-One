import { TestBed } from '@angular/core/testing';

import { PaymentProof } from './payment-proof';

describe('PaymentProof', () => {
  let service: PaymentProof;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaymentProof);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

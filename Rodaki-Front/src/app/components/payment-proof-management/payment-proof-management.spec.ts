import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentProofManagement } from './payment-proof-management';

describe('PaymentProofManagement', () => {
  let component: PaymentProofManagement;
  let fixture: ComponentFixture<PaymentProofManagement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentProofManagement]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentProofManagement);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

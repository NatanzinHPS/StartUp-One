import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadPaymentProof } from './upload-payment-proof';

describe('UploadPaymentProof', () => {
  let component: UploadPaymentProof;
  let fixture: ComponentFixture<UploadPaymentProof>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UploadPaymentProof]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadPaymentProof);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

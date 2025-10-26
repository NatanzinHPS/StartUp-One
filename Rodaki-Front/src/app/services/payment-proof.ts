import { Injectable } from '@angular/core';

export interface PaymentProof {
  id?: number;
  passengerId: number;
  referenceMonth: string | null;
  amount: number | null;
  fileName?: string;
  fileType?: string;
  fileDataUrl?: string;
  uploadedAt?: string;
}

@Injectable({ providedIn: 'root' })
export class PaymentProofService {
  private store: PaymentProof[] = [];
  private idSeq = 1;

  async uploadProof(passengerId: number, proof: PaymentProof): Promise<PaymentProof> {
    const record = { ...proof, id: this.idSeq++, passengerId, uploadedAt: new Date().toISOString() };
    this.store.push(record);
    console.log('Mock upload saved', record);
    return structuredClone(record);
  }

  async getProofs(passengerId: number): Promise<PaymentProof[]> {
    return this.store.filter((p) => p.passengerId === passengerId).map((r) => structuredClone(r));
  }
}

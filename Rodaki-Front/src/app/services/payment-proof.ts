import { Injectable } from '@angular/core';

export type ProofDetailStatus = 'AGUARDANDO_APROVAÇÃO' | 'PENDENTE' | 'APROVADO' | 'REJEITADO';
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

export interface ProofRow {
  id: number;
  name: string;
  month: string; // e.g. 'Janeiro 2024'
  amount: number; // e.g. 180.0
  uploadDate: string; // ISO string
  statusDetail: ProofDetailStatus;
  proofFileUrl?: string; // mock url to image/pdf
  reviewer?: string;
  reviewComment?: string;
}

@Injectable({ providedIn: 'root' })
export class PaymentProofService {
  private store: PaymentProof[] = [];
  private idSeq = 1;

  private proofs: ProofRow[] = [
    // Alinhado aos mocks de PassengerList.passengersForManagement
    { id: 1,  name: 'Maria Silva',        month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-15T08:10:00', statusDetail: 'APROVADO',  proofFileUrl: 'assets/PaymentProof.png', reviewer: 'Paulo' },
    { id: 2,  name: 'João Santos',        month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-15T09:05:00', statusDetail: 'AGUARDANDO_APROVAÇÃO', proofFileUrl: 'assets/PaymentProof.png' },
    { id: 3,  name: 'Ana Costa',          month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-14T16:45:00', statusDetail: 'REJEITADO', proofFileUrl: 'assets/PaymentProof.png', reviewer: 'Paulo', reviewComment: 'Imagem ilegível' },
    { id: 4,  name: 'Pedro Lima',         month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-13T11:20:00', statusDetail: 'AGUARDANDO_APROVAÇÃO',  proofFileUrl: 'assets/PaymentProof.png' },
    { id: 5,  name: 'Carla Oliveira',     month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-13T14:05:00', statusDetail: 'PENDENTE' },
    { id: 6,  name: 'Rafael Souza',       month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-12T08:00:00', statusDetail: 'REJEITADO', proofFileUrl: 'assets/PaymentProof.png', reviewer: 'Paulo', reviewComment: 'Comprovante vencido' },
    { id: 7,  name: 'Beatriz Ferreira',   month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-12T10:30:00', statusDetail: 'PENDENTE' },
    { id: 8,  name: 'Lucas Almeida',      month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-11T15:40:00', statusDetail: 'APROVADO',  proofFileUrl: 'assets/PaymentProof.png', reviewer: 'Paulo' },
    { id: 9,  name: 'Fernanda Gomes',     month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-11T17:25:00', statusDetail: 'APROVADO',  proofFileUrl: 'assets/PaymentProof.png', reviewer: 'Paulo' },
    { id: 10, name: 'Bruno Ribeiro',      month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-10T09:55:00', statusDetail: 'REJEITADO', proofFileUrl: 'assets/PaymentProof.png', reviewer: 'Paulo', reviewComment: 'Dados inconsistentes' },
    { id: 11, name: 'Sofia Pereira',      month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-10T13:15:00', statusDetail: 'PENDENTE' },
    { id: 12, name: 'Marcos Alves',       month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-09T08:45:00', statusDetail: 'AGUARDANDO_APROVAÇÃO',  proofFileUrl: 'assets/PaymentProof.png' },
    { id: 13, name: 'Helena Rocha',       month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-09T11:05:00', statusDetail: 'APROVADO',  proofFileUrl: 'assets/PaymentProof.png', reviewer: 'Paulo' },
    { id: 14, name: 'Tiago Fernandes',    month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-08T16:10:00', statusDetail: 'PENDENTE' },
    { id: 15, name: 'Paula Mendes',       month: 'Novembro 2025', amount: 180, uploadDate: '2025-11-08T18:35:00', statusDetail: 'REJEITADO', proofFileUrl: 'assets/PaymentProof.png', reviewer: 'Paulo', reviewComment: 'Comprovante não corresponde ao mês' }
  ];

  async getAll(): Promise<ProofRow[]> {
    return new Promise(resolve => setTimeout(() => resolve(this.proofs.slice()), 300));
  }

  async updateStatus(id: number, status: ProofDetailStatus, reviewer?: string, comment?: string): Promise<ProofRow | undefined> {
    const idx = this.proofs.findIndex(p => p.id === id);
    if (idx === -1) return undefined;
    this.proofs[idx] = { ...this.proofs[idx], statusDetail: status, reviewer: reviewer ?? 'Motorista', reviewComment: comment ?? this.proofs[idx].reviewComment };
    return new Promise(resolve => setTimeout(() => resolve(this.proofs[idx]), 250));
  }

  async counts() {
    const all = await this.getAll();
    const aguardando = all.filter(p => p.statusDetail === 'AGUARDANDO_APROVAÇÃO').length;
    const aprovados = all.filter(p => p.statusDetail === 'APROVADO').length;
    const rejeitados = all.filter(p => p.statusDetail === 'REJEITADO').length;
    const pendentes = all.filter(p => p.statusDetail === 'PENDENTE').length;
    return { aguardando, aprovados, rejeitados, pendentes };
  }

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

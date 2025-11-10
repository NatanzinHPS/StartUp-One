import { Injectable } from '@angular/core';

export type ProofStatus = 'APROVADO' | 'PENDENTE' | 'REJEITADO';
export type AccountStatus = 'ATIVO' | 'INATIVO';

export interface PassengerRow {
  id: number;
  name: string;
  phone: string;
  route: string;
  stop?: string;
  status: AccountStatus;
  proofStatus: ProofStatus;
  avatarUrl?: string;
}
export interface PassengerStatus {
  name: string;
  route: string;
  time: string;
  useStatus: 'PRESENTE' | 'AUSENTE' | 'PENDENTE';
  tripType?: 'IDA' | 'VOLTA' | 'AMBAS';
}

@Injectable({ providedIn: 'root' })
export class PassengerList {

  private passengers: PassengerStatus[] = [
    { name: 'Maria Silva', route: 'Rota Centro', time: '07:30', useStatus: 'PRESENTE', tripType: 'IDA' },
    { name: 'João Santos', route: 'Rota Norte', time: '08:00', useStatus: 'AUSENTE' },
    { name: 'Ana Costa', route: 'Rota Leste', time: '07:45', useStatus: 'PENDENTE' },
    { name: 'Pedro Lima', route: 'Rota Sul', time: '08:15', useStatus: 'PRESENTE', tripType: 'AMBAS' },
    { name: 'Carla Oliveira', route: 'Rota B', time: '07:30', useStatus: 'PRESENTE', tripType: 'VOLTA' },
    { name: 'Rafael Souza', route: 'Rota A', time: '08:00', useStatus: 'AUSENTE' },
    { name: 'Beatriz Ferreira', route: 'Rota C', time: '07:45', useStatus: 'PENDENTE' },
    { name: 'Lucas Almeida', route: 'Rota B', time: '08:15', useStatus: 'PRESENTE', tripType: 'AMBAS' },
    { name: 'Fernanda Gomes', route: 'Rota D', time: '07:20', useStatus: 'PRESENTE', tripType: 'AMBAS' },
    { name: 'Bruno Ribeiro', route: 'Rota E', time: '08:30', useStatus: 'AUSENTE' },
    { name: 'Sofia Pereira', route: 'Rota Centro', time: '07:50', useStatus: 'PENDENTE' },
    { name: 'Marcos Alves', route: 'Rota Norte', time: '08:05', useStatus: 'PRESENTE', tripType: 'AMBAS' },
    { name: 'Helena Rocha', route: 'Rota Leste', time: '07:40', useStatus: 'PRESENTE', tripType: 'IDA' },
    { name: 'Tiago Fernandes', route: 'Rota Sul', time: '08:10', useStatus: 'AUSENTE' },
    { name: 'Paula Mendes', route: 'Rota B', time: '07:35', useStatus: 'PENDENTE' },
  ];

  private passengersForManagement: PassengerRow[] = [
    { id: 1, name: 'Maria Silva', phone: '(11) 99999-0001', route: 'Rota Centro', status: 'ATIVO', proofStatus: 'APROVADO', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 2, name: 'João Santos', phone: '(11) 99999-0002', route: 'Rota Norte', status: 'ATIVO', proofStatus: 'PENDENTE', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 3, name: 'Ana Costa', phone: '(11) 99999-0003', route: 'Rota Leste', status: 'INATIVO', proofStatus: 'REJEITADO', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 4, name: 'Pedro Lima', phone: '(11) 99999-0004', route: 'Rota Sul', status: 'ATIVO', proofStatus: 'APROVADO', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 5, name: 'Carla Oliveira', phone: '(11) 99999-0005', route: 'Rota B', status: 'ATIVO', proofStatus: 'PENDENTE', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 6, name: 'Rafael Souza', phone: '(11) 99999-0006', route: 'Rota A', status: 'INATIVO', proofStatus: 'REJEITADO', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 7, name: 'Beatriz Ferreira', phone: '(11) 99999-0007', route: 'Rota C', status: 'ATIVO', proofStatus: 'PENDENTE', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 8, name: 'Lucas Almeida', phone: '(11) 99999-0008', route: 'Rota B', status: 'ATIVO', proofStatus: 'APROVADO', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 9, name: 'Fernanda Gomes', phone: '(11) 99999-0009', route: 'Rota D', status: 'ATIVO', proofStatus: 'APROVADO', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 10, name: 'Bruno Ribeiro', phone: '(11) 99999-0010', route: 'Rota E', status: 'INATIVO', proofStatus: 'REJEITADO', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 11, name: 'Sofia Pereira', phone: '(11) 99999-0011', route: 'Rota Centro', status: 'ATIVO', proofStatus: 'PENDENTE', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 12, name: 'Marcos Alves', phone: '(11) 99999-0012', route: 'Rota Norte', status: 'ATIVO', proofStatus: 'APROVADO', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 13, name: 'Helena Rocha', phone: '(11) 99999-0013', route: 'Rota Leste', status: 'ATIVO', proofStatus: 'APROVADO', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 14, name: 'Tiago Fernandes', phone: '(11) 99999-0014', route: 'Rota Sul', status: 'INATIVO', proofStatus: 'PENDENTE', avatarUrl: 'assets/avatar-placeholder.png' },
    { id: 15, name: 'Paula Mendes', phone: '(11) 99999-0015', route: 'Rota B', status: 'ATIVO', proofStatus: 'REJEITADO', avatarUrl: 'assets/avatar-placeholder.png' }
  ];

  async getTodayList(): Promise<PassengerStatus[]> {
    return new Promise(resolve => setTimeout(() => resolve(this.passengers), 1000)); // simulate delay
  }

  async getAll(): Promise<PassengerRow[]> {
    // simulate delay
    return new Promise(resolve => setTimeout(() => resolve(this.passengersForManagement.slice()), 300));
  }

  // placeholder for edit/save
  async update(p: Partial<PassengerRow> & { id: number }): Promise<PassengerRow> {
    const idx = this.passengersForManagement.findIndex(x => x.id === p.id);
    if (idx >= 0) {
      this.passengersForManagement[idx] = { ...this.passengersForManagement[idx], ...p };
    }
    return new Promise(r => setTimeout(() => r(this.passengersForManagement[idx]), 200));
  }
}
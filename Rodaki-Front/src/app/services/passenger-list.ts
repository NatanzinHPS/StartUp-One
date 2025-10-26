import { Injectable } from '@angular/core';

export interface PassengerStatus {
  name: string;
  route: string;
  time: string;
  status: 'PRESENTE' | 'AUSENTE' | 'PENDENTE';
  tripType?: 'IDA' | 'VOLTA' | 'AMBAS';
}

@Injectable({ providedIn: 'root' })
export class PassengerList {

  private passengers: PassengerStatus[] = [
    { name: 'Ana Silva', route: 'Rota A', time: '07:30', status: 'PRESENTE', tripType: 'IDA' },
    { name: 'João Santos', route: 'Rota B', time: '08:00', status: 'AUSENTE' },
    { name: 'Maria Costa', route: 'Rota A', time: '07:45', status: 'PENDENTE' },
    { name: 'Pedro Lima', route: 'Rota C', time: '08:15', status: 'PRESENTE', tripType: 'AMBAS' },
    { name: 'Carla Oliveira', route: 'Rota B', time: '07:30', status: 'PRESENTE', tripType: 'VOLTA' },
    { name: 'Rafael Souza', route: 'Rota A', time: '08:00', status: 'AUSENTE' },
    { name: 'Beatriz Ferreira', route: 'Rota C', time: '07:45', status: 'PENDENTE' },
    { name: 'Lucas Almeida', route: 'Rota B', time: '08:15', status: 'PRESENTE', tripType: 'AMBAS' },
  ];

  async getTodayList(): Promise<PassengerStatus[]> {
    return new Promise(resolve => setTimeout(() => resolve(this.passengers), 1000)); // simulate delay
  }
}
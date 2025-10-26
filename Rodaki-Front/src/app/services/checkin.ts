import { Injectable } from '@angular/core';

/**
 * Tipos possíveis de presença no check-in diário
 */
export type TodayCheckin = 'IDA' | 'VOLTA' | 'AMBAS' | 'AUSENTE' | null;

@Injectable({
  providedIn: 'root'
})
export class CheckinService {
  private todayStatus: TodayCheckin = 'AMBAS'; // valor inicial mockado

  /**
   * Obtém o status atual do check-in do passageiro
   */
  async getTodayStatus(): Promise<TodayCheckin> {
    // simula chamada HTTP
    return new Promise(resolve => setTimeout(() => resolve(this.todayStatus), 300));
  }

  /**
   * Atualiza o status do check-in do passageiro
   */
  async setTodayStatus(status: TodayCheckin): Promise<void> {
    // simula chamada ao backend com leve atraso
    return new Promise(resolve => {
      setTimeout(() => {
        this.todayStatus = status;
        resolve();
      }, 400);
    });
  }
}
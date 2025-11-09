import { Injectable } from '@angular/core';

export interface AttendancePoint { date: string; percent: number }
export interface PaymentStatus { approved: number; pending: number; rejected: number }
export interface RevenueByRoute { route: string; value: number }
export interface TopAbsent { name: string; count: number }

@Injectable({ providedIn: 'root' })
export class ReportsService {

  async getAttendanceSeries(days = 15): Promise<AttendancePoint[]> {
    const list: AttendancePoint[] = [];
    for (let i = 0; i < days; i++) {
      const d = new Date();
      d.setDate(d.getDate() - (days - 1 - i));
      const pct = Math.round(70 + (Math.sin(i / 5) * 15) + (Math.random() * 8 - 4));
      list.push({ date: d.toISOString().slice(0, 10), percent: Math.max(40, Math.min(100, pct)) });
    }
    return new Promise((r) => setTimeout(() => r(list), 400));
  }

  async getPaymentStatus(): Promise<PaymentStatus> {
    return new Promise((r) => setTimeout(() => r({ approved: 120, pending: 28, rejected: 9 }), 300));
  }

  async getRevenueByRoute(): Promise<RevenueByRoute[]> {
    return new Promise((r) => setTimeout(() => r([
      { route: 'Centro - Zona Sul', value: 4200 },
      { route: 'Aeroporto - Centro', value: 3300 },
      { route: 'Campus C', value: 2900 }
    ]), 300));
  }

  async getTopAbsent(): Promise<TopAbsent[]> {
    return new Promise((r) => setTimeout(() => r([
      { name: 'Marina S.', count: 8 },
      { name: 'João P.', count: 6 },
      { name: 'Ana L.', count: 5 },
      { name: 'Carlos M.', count: 4 },
      { name: 'Lucas F.', count: 3 }
    ]), 300));
  }
}
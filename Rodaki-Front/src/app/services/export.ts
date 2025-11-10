import { Injectable } from '@angular/core';

export interface ExportRecord {
  id: number;
  fileName: string;
  createdAt: string;
  blobUrl: string;
}

@Injectable({ providedIn: 'root' })
export class ExportService {
  private history: ExportRecord[] = [];
  private seq = 1;

  async exportCsv(type: string, from?: string, to?: string): Promise<ExportRecord> {
    const rows = [
      ['Rodaki CSV Export'],
      ['Tipo', type],
      ['Período', `${from || '-'} -> ${to || '-'}`],
      [],
      ['col1', 'col2', 'col3']
    ];
    // add some mock data rows
    for (let i = 0; i < 8; i++) rows.push([`r${i}`, `${Math.round(Math.random() * 1000)}`, `${Math.round(Math.random() * 100)}`]);

    const csv = rows.map(r => r.map(c => `"${String(c).replace(/"/g, '""')}"`).join(',')).join('\n');
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const rec: ExportRecord = { id: this.seq++, fileName: `rodaki_export_${Date.now()}.csv`, createdAt: new Date().toISOString(), blobUrl: url };
    this.history.unshift(rec);
    // simulate delay
    return new Promise(r => setTimeout(() => r(rec), 300));
  }

  async getHistory(): Promise<ExportRecord[]> {
    return new Promise(r => setTimeout(() => r(this.history.slice()), 150));
  }
}
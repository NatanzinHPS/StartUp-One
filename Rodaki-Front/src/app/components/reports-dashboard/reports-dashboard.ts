import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ExportRecord, ExportService } from '../../services/export';
import { ReportsService } from '../../services/reports';
import { BottomNav } from '../bottom-nav/bottom-nav';

// ApexCharts imports
import { ChartComponent, NgApexchartsModule } from 'ng-apexcharts';
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexTitleSubtitle,
  ApexNonAxisChartSeries,
  ApexResponsive,
  ApexStroke,
  ApexFill,
  ApexPlotOptions
} from 'ng-apexcharts';
import { TopBar } from '../top-bar/top-bar';

export type LineChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  stroke: ApexStroke;
  title: ApexTitleSubtitle;
  fill: ApexFill;
  responsive: ApexResponsive[];
};

@Component({
  selector: 'app-reports-dashboard',
  imports: [CommonModule, FormsModule, NgApexchartsModule, TopBar, BottomNav],
  templateUrl: './reports-dashboard.html',
  styleUrl: './reports-dashboard.scss',
})

export class ReportsDashboard implements OnInit {
  
  attendanceSeries: any[] = [];
  paymentStatus: any = { approved: 0, pending: 0, rejected: 0 };
  revenue: any[] = [];
  topAbsent: any[] = [];

  // chart options
  lineChartOptions: Partial<LineChartOptions> | any;
  donutSeries: number[] = [];
  donutLabels: string[] = [];
  revenueOptions: any;
  absentOptions: any;

  // export
  fromDate: string | null = null;
  toDate: string | null = null;
  dataType = 'Presenca';
  exporting = false;
  history: ExportRecord[] = [];

  constructor(private reports: ReportsService, private exporter: ExportService, private router: Router) { }

  async ngOnInit() {
    await this.loadAll();
    this.history = await this.exporter.getHistory();
  }

  async loadAll() {
    const attendance = await this.reports.getAttendanceSeries(15);
    this.attendanceSeries = attendance;
    this.lineChartOptions = this.buildLineOptions(attendance.map(a => a.date), attendance.map(a => a.percent));

    this.paymentStatus = await this.reports.getPaymentStatus();
    this.donutSeries = [this.paymentStatus.approved, this.paymentStatus.pending, this.paymentStatus.rejected];
    this.donutLabels = ['Aprovado', 'Pendente', 'Rejeitado'];

    this.revenue = await this.reports.getRevenueByRoute();
    this.revenueOptions = this.buildBarOptions(this.revenue.map(r => r.route), this.revenue.map(r => r.value));

    this.topAbsent = await this.reports.getTopAbsent();
    this.absentOptions = this.buildBarOptions(this.topAbsent.map(p => p.name), this.topAbsent.map(p => p.count), true);
  }

  buildLineOptions(labels: string[], data: number[]) {
    return {
      chart: { type: 'line', height: 240, toolbar: { show: false }, animations: { enabled: true } },
      series: [{ name: 'Presença (%)', data }],
      stroke: { curve: 'smooth', width: 3 },
      xaxis: { categories: labels.map(l => l.slice(5)), labels: { rotate: -45 } },
      colors: ['#0056B3'],
      grid: { borderColor: '#eef2f6' }
    };
  }

  buildBarOptions(labels: string[], data: number[], minimal = false) {
    return {
      chart: { type: 'bar', height: minimal ? 180 : 220, toolbar: { show: false } },
      series: [{ name: 'Value', data }],
      plotOptions: { bar: { borderRadius: 6 } },
      xaxis: { categories: labels, labels: { rotate: -20 } },
      colors: ['#1976d2']
    };
  }

  async doExport() {
    if (this.exporting) return;
    this.exporting = true;
    const rec = await this.exporter.exportCsv(this.dataType, this.fromDate || undefined, this.toDate || undefined);
    // automatically trigger download
    const a = document.createElement('a');
    a.href = rec.blobUrl;
    a.download = rec.fileName;
    a.click();
    this.history = await this.exporter.getHistory();
    this.exporting = false;
  }

  downloadRecord(rec: ExportRecord) {
    const a = document.createElement('a');
    a.href = rec.blobUrl;
    a.download = rec.fileName;
    a.click();
  }
}


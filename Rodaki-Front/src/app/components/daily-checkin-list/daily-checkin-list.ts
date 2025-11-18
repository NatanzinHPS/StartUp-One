import { Component, OnInit } from '@angular/core';
import { PassengerList, PassengerStatus } from '../../services/passenger-list';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BottomNav } from '../bottom-nav/bottom-nav';
import { TopBar } from '../top-bar/top-bar';

@Component({
  selector: 'app-daily-checkin-list',
  imports: [CommonModule, FormsModule, TopBar, BottomNav],
  templateUrl: './daily-checkin-list.html',
  styleUrls: ['./daily-checkin-list.scss'],
})
export class DailyCheckinList implements OnInit {

  allPassengers: PassengerStatus[] = [];
  filteredPassengers: PassengerStatus[] = [];
  searchTerm = '';

  total = 0;
  presentes = 0;
  ausentes = 0;
  pendentes = 0;

  animatedStats = { total: 0, presentes: 0, ausentes: 0, pendentes: 0 };

  loading = false;

  filters = [
    { key: 'TODOS', label: 'Todos', color: 'primary' },
    { key: 'PRESENTE', label: 'Confirmados', color: 'success' },
    { key: 'AUSENTE', label: 'Ausentes', color: 'danger' },
    { key: 'PENDENTE', label: 'Pendentes', color: 'warning' }
  ];

  activeFilter = 'PRESENTE'; // default per usability tests

  constructor(private service: PassengerList) { }

  ngOnInit() {
    this.loadPassengers();
  }

  async loadPassengers() {
    this.loading = true;
    this.allPassengers = await this.service.getTodayList();
    this.updateStats();
    this.applyFilter();
    this.loading = false;
  }

  animateValue(property: keyof typeof this.animatedStats, start: number, end: number, duration: number = 600) {
    const startTime = performance.now();
    const animate = (time: number) => {
      const progress = Math.min((time - startTime) / duration, 1);
      this.animatedStats[property] = Math.floor(progress * (end - start) + start);
      if (progress < 1) requestAnimationFrame(animate);
    };
    requestAnimationFrame(animate);
  }

  updateStats() {
    const prev = { ...this.animatedStats };
    this.total = this.allPassengers.length;
    this.presentes = this.allPassengers.filter(p => p.useStatus === 'PRESENTE').length;
    this.ausentes = this.allPassengers.filter(p => p.useStatus === 'AUSENTE').length;
    this.pendentes = this.allPassengers.filter(p => p.useStatus === 'PENDENTE').length;

    this.animateValue('total', prev.total, this.total);
    this.animateValue('presentes', prev.presentes, this.presentes);
    this.animateValue('ausentes', prev.ausentes, this.ausentes);
    this.animateValue('pendentes', prev.pendentes, this.pendentes);
  }

  applyFilter() {
    const term = this.searchTerm.toLowerCase();
    const filtered = this.allPassengers.filter(p => {
      const matchTerm = p.name.toLowerCase().includes(term);
      if (this.activeFilter === 'TODOS') return matchTerm;
      return matchTerm && p.useStatus === this.activeFilter;
    });
    this.filteredPassengers = filtered;
  }

  setFilter(filter: string) {
    this.activeFilter = filter;
    this.applyFilter();
  }

  getTripIcon(tripType?: 'IDA' | 'VOLTA' | 'AMBAS'): string {
    switch (tripType) {
      case 'IDA': return 'bi-arrow-right-circle trip-going';
      case 'VOLTA': return 'bi-arrow-left-circle trip-back';
      case 'AMBAS': return 'bi-arrow-left-right trip-both';
      default: return '';
    }
  }

  getTripLabel(tripType?: 'IDA' | 'VOLTA' | 'AMBAS'): string {
    return tripType ? tripType.charAt(0) + tripType.slice(1).toUpperCase() : '';
  }
}
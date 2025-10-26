import { Component, OnInit } from '@angular/core';
import { PassengerList, PassengerStatus } from '../../services/passenger-list';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-daily-chekin-list',
  imports: [CommonModule, FormsModule],
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

  filters = [
    { key: 'TODOS', label: 'Todos', color: 'primary' },
    { key: 'PRESENTE', label: 'Presentes', color: 'success' },
    { key: 'AUSENTE', label: 'Ausentes', color: 'danger' },
    { key: 'PENDENTE', label: 'Pendentes', color: 'warning' }
  ];

  activeFilter = 'PRESENTE'; // default as per usability tests

  constructor(private service: PassengerList) { }

  ngOnInit() {
    this.loadPassengers();
  }

  async loadPassengers() {
    this.allPassengers = await this.service.getTodayList();
    this.updateStats();
    this.applyFilter();
  }

  updateStats() {
    this.total = this.allPassengers.length;
    this.presentes = this.allPassengers.filter(p => p.status === 'PRESENTE').length;
    this.ausentes = this.allPassengers.filter(p => p.status === 'AUSENTE').length;
    this.pendentes = this.allPassengers.filter(p => p.status === 'PENDENTE').length;
  }

  applyFilter() {
    const term = this.searchTerm.toLowerCase();
    const filtered = this.allPassengers.filter(p => {
      const matchTerm = p.name.toLowerCase().includes(term);
      if (this.activeFilter === 'TODOS') return matchTerm;
      return matchTerm && p.status === this.activeFilter;
    });
    this.filteredPassengers = filtered;
  }

  setFilter(filter: string) {
    this.activeFilter = filter;
    this.applyFilter();
  }
}
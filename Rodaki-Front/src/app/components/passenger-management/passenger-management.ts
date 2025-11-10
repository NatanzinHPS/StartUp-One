import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { TopBar } from '../top-bar/top-bar';
import { BottomNav } from '../bottom-nav/bottom-nav';
import { PassengerList, PassengerRow } from '../../services/passenger-list';

@Component({
  selector: 'app-passenger-management',
  imports: [CommonModule, FormsModule, TopBar, BottomNav],
  templateUrl: './passenger-management.html',
  styleUrl: './passenger-management.scss',
})
export class PassengerManagement implements OnInit {

  passengers: PassengerRow[] = [];
  filtered: PassengerRow[] = [];

  searchTerm = '';
  selectedRoute = 'Todas as Rotas';
  selectedStatus = 'Todos Status';

  routes: string[] = ['Todas as Rotas'];
  statuses: string[] = ['Todos Status', 'ATIVO', 'INATIVO'];

  showToast = false;
  toastText = '';

  constructor(private svc: PassengerList, private router: Router) { }

  async ngOnInit() {
    this.passengers = await this.svc.getAll();
    this.routes = Array.from(new Set(['Todas as Rotas', ...this.passengers.map(p => p.route)]));
    this.applyFilter();
  }

  applyFilter() {
    const term = this.searchTerm.trim().toLowerCase();
    this.filtered = this.passengers.filter(p => {
      const matchTerm = !term || p.name.toLowerCase().includes(term) || p.phone.includes(term) || p.route.toLowerCase().includes(term);
      const matchRoute = this.selectedRoute === 'Todas as Rotas' || p.route === this.selectedRoute;
      const matchStatus = this.selectedStatus === 'Todos Status' || p.status === this.selectedStatus;
      return matchTerm && matchRoute && matchStatus;
    });
  }

  copyLink() {
    const link = 'https://rodaki.app/register?token=mocked-12345';
    // try navigator clipboard
    if (navigator.clipboard && navigator.clipboard.writeText) {
      navigator.clipboard.writeText(link).then(() => this.showCopiedToast());
    } else {
      // fallback
      const textarea = document.createElement('textarea');
      textarea.value = link;
      textarea.style.position = 'fixed';
      document.body.appendChild(textarea);
      textarea.focus(); textarea.select();
      try { document.execCommand('copy'); this.showCopiedToast(); } catch { }
      textarea.remove();
    }
  }

  showCopiedToast() {
    this.toastText = 'Link copiado com sucesso!';
    this.showToast = true;
    setTimeout(() => this.showToast = false, 2000);
  }

  goToEdit(id: number) {
    // TODO
  }
}
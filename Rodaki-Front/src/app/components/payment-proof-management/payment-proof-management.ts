import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PaymentProofService, ProofRow } from '../../services/payment-proof';
import { TopBar } from '../top-bar/top-bar';
import { BottomNav } from '../bottom-nav/bottom-nav';

@Component({
  selector: 'app-payment-proof-management',
  standalone: true,
  imports: [CommonModule, FormsModule, TopBar, BottomNav],
  templateUrl: './payment-proof-management.html',
  styleUrls: ['./payment-proof-management.scss']
})

export class PaymentProofManagement implements OnInit {

  proofs: ProofRow[] = [];
  filtered: ProofRow[] = [];

  // filters & search
  filters: { key: 'AGUARDANDO_APROVAÇÃO' | 'PENDENTE' | 'APROVADO' | 'REJEITADO'; label: string }[] = [
    { key: 'AGUARDANDO_APROVAÇÃO', label: 'Aguardando' },
    { key: 'PENDENTE', label: 'Pendente' },
    { key: 'APROVADO', label: 'Aprovado' },
    { key: 'REJEITADO', label: 'Rejeitado' }
  ];
  activeFilter: 'AGUARDANDO_APROVAÇÃO' | 'PENDENTE' | 'APROVADO' | 'REJEITADO' = 'AGUARDANDO_APROVAÇÃO';
  searchTerm = '';

  // modal
  modalOpen = false;
  currentProof?: ProofRow;
  rejectComment = '';
  showRejectArea = false;
  processing = false;

  // stats animated
  animatedStats = { aguardando: 0, aprovados: 0, rejeitados: 0, pendentes: 0 };

  // toast
  toastText = '';
  showToast = false;
  toastClass = 'success';

  constructor(private svc: PaymentProofService) { }

  async ngOnInit() {
    await this.loadAll();
  }

  async loadAll() {
    this.proofs = await this.svc.getAll();
    this.applyFilter();
    const counts = await this.svc.counts();
    this.animateValue('aguardando', 0, counts.aguardando);
    this.animateValue('aprovados', 0, counts.aprovados);
    this.animateValue('rejeitados', 0, counts.rejeitados);
    this.animateValue('pendentes', 0, counts.pendentes);
  }

  applyFilter() {
    const term = this.searchTerm.trim().toLowerCase();
    this.filtered = this.proofs.filter(p => {
      const matchFilter = p.statusDetail === this.activeFilter;
      const matchTerm = !term || p.name.toLowerCase().includes(term);
      return matchFilter && matchTerm;
    });
  }

  setFilter(f: 'AGUARDANDO_APROVAÇÃO' | 'PENDENTE' | 'APROVADO' | 'REJEITADO') {
    this.activeFilter = f;
    this.applyFilter();
  }

  openModal(p: ProofRow) {
    this.currentProof = p;
    this.rejectComment = '';
    this.showRejectArea = false;
    this.modalOpen = true;
  }

  closeModal() {
    this.modalOpen = false;
    this.showRejectArea = false;
    setTimeout(() => this.currentProof = undefined, 200);
  }

  async approve(proof: ProofRow) {
    if (this.processing) return;
    this.processing = true;
    const updated = await this.svc.updateStatus(proof.id, 'APROVADO', 'Paulo');
    if (updated) {
      this.toast('Comprovante aprovado com sucesso', 'success');
      await this.loadAll();
      if (this.currentProof && this.currentProof.id === proof.id) this.currentProof = updated;
    }
    this.processing = false;
  }

  async reject(proof: ProofRow) {
    if (this.processing) return;
    if (!this.showRejectArea) {
      this.showRejectArea = true;
      return;
    }
    if (this.rejectComment.trim().length === 0) return; // botão ficará disabled
    this.processing = true;
    const updated = await this.svc.updateStatus(proof.id, 'REJEITADO', 'Paulo', this.rejectComment.trim());
    if (updated) {
      this.toast('Comprovante rejeitado com sucesso', 'error');
      await this.loadAll();
      if (this.currentProof && this.currentProof.id === proof.id) this.currentProof = updated;
      this.rejectComment = '';
      this.closeModal();
    }
    this.processing = false;
  }

  cancelReject() {
    this.showRejectArea = false;
    this.rejectComment = '';
  }

  toast(text: string, type: 'success' | 'error' = 'success') {
    this.toastText = text;
    this.toastClass = type === 'success' ? 'success' : 'error';
    this.showToast = true;
    setTimeout(() => this.showToast = false, 3000);
  }

  animateValue(key: keyof typeof this.animatedStats, start: number, end: number, duration = 800) {
    const startTime = performance.now();
    const animate = (time: number) => {
      const progress = Math.min((time - startTime) / duration, 1);
      this.animatedStats[key] = Math.floor(progress * (end - start) + start);
      if (progress < 1) requestAnimationFrame(animate);
    };
    requestAnimationFrame(animate);
  }

  formatStatus(s: ProofRow['statusDetail'] | undefined) {
    if (!s) return '';
    return s === 'AGUARDANDO_APROVAÇÃO' ? 'AGUARDANDO' : s;
  }
}
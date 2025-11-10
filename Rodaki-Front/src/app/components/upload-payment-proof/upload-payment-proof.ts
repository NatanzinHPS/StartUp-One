import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PaymentProof, PaymentProofService } from '../../services/payment-proof';
import { Router } from '@angular/router';
import { TopBar } from '../top-bar/top-bar';

@Component({
  selector: 'app-upload-payment-proof',
  imports: [CommonModule, FormsModule, TopBar],
  templateUrl: './upload-payment-proof.html',
  styleUrl: './upload-payment-proof.scss',
})
export class UploadPaymentProof {
  passengerId = 1;

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  months = [
    { value: '', label: 'Selecione o mês' },
    { value: '2025-01', label: 'Janeiro / 2025' },
    { value: '2025-02', label: 'Fevereiro / 2025' },
    { value: '2025-03', label: 'Março / 2025' },
    { value: '2025-04', label: 'Abril / 2025' },
    { value: '2025-05', label: 'Maio / 2025' }
  ];

  model: PaymentProof = {
    passengerId: this.passengerId,
    referenceMonth: null,
    amount: null,
  };

  fileBuffer: File | null = null;
  errorMessage = '';
  uploading = false;

  constructor(private service: PaymentProofService,
    private router: Router
  ) { }

  onFileSelected(file?: File | null) {
    this.errorMessage = '';
    const f = file ?? this.fileInput?.nativeElement?.files?.[0] ?? null;
    if (!f) return;

    const allowed = ['image/png', 'image/jpeg', 'image/webp', 'application/pdf'];
    if (!allowed.includes(f.type)) {
      this.errorMessage = 'Formato inválido. Apenas imagens (PNG/JPEG/WEBP) e PDF.';
      return;
    }
    if (f.size > 10 * 1024 * 1024) {
      this.errorMessage = 'Arquivo muito grande. Máx: 10MB.';
      return;
    }
    this.fileBuffer = f;
    this.model.fileName = f.name;
    this.model.fileType = f.type;

    if (f.type.startsWith('image/')) {
      const reader = new FileReader();
      reader.onload = () => { this.model.fileDataUrl = String(reader.result); };
      reader.readAsDataURL(f);
    } else {
      this.model.fileDataUrl = '';
    }
  }

  dragOver = false;
  onDrop(ev: DragEvent) {
    ev.preventDefault();
    this.dragOver = false;
    const file = ev.dataTransfer?.files?.[0] ?? null;
    if (file) this.onFileSelected(file);
  }
  onDragOver(ev: DragEvent) { ev.preventDefault(); this.dragOver = true; }
  onDragLeave() { this.dragOver = false; }

  isFormValid(): boolean {
    return !!(this.model.referenceMonth && this.model.amount && this.fileBuffer);
  }

  removeFile(event: Event) {
    event.preventDefault();
    this.fileBuffer = null;
    this.model.fileDataUrl = '';
    this.model.fileName = '';
    this.model.fileType = '';

    if (this.fileInput) {
      this.fileInput.nativeElement.value = '';
    }
  }

  async send() {
    if (!this.isFormValid()) return;
    this.uploading = true;
    try {
      await this.service.uploadProof(this.passengerId, this.model);
      alert('Comprovante enviado com sucesso!');
      this.model = { passengerId: this.passengerId, referenceMonth: null, amount: null };
      this.fileBuffer = null;
      this.model.fileDataUrl = undefined;
      this.model.fileName = undefined;
      this.model.fileType = '';

      if (this.fileInput) {
        this.fileInput.nativeElement.value = '';
      }
    } catch (e) {
      console.error(e);
      this.errorMessage = 'Falha ao enviar comprovante.';
    } finally {
      this.uploading = false;
      this.goToPassengerHome();
    }
  }

  goToPassengerHome() {
    this.router.navigate(['/passenger-home']);
  }

}
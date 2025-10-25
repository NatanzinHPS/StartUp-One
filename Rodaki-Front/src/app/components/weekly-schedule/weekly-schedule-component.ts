import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';

// Mock data model interface
export interface PassengerSchedule {
  id?: number;
  passengerId: number;
  dayOfWeek: number; // 1 = Monday ... 7 = Sunday
  schedule: 'MORNING' | 'AFTERNOON' | 'BOTH';
  isActive: boolean;
  name?: string; // added for display in UI
}


@Injectable({ providedIn: 'root' })
export class PassengerScheduleService {
  private schedules: PassengerSchedule[] = [];


  async getByPassenger(passengerId: number): Promise<PassengerSchedule[]> {
    return this.schedules.filter((s) => s.passengerId === passengerId);
  }


  async saveAll(passengerId: number, list: PassengerSchedule[]): Promise<void> {
    this.schedules = list.map((s) => ({ ...s, passengerId }));
    console.log('Mock save successful', this.schedules);
  }
}

@Component({
  selector: 'app-weekly-schedule-component',
  imports: [CommonModule, FormsModule, HttpClientModule],
  template: `
<div class="container py-3" style="max-width:480px;background-color:#F8F9FA;">
<div class="d-flex align-items-center mb-3">
<img src="assets/logo.png" alt="Rodaki" height="40" class="me-2" />
<h4 class="m-0 text-primary fw-bold">Minha Agenda Semanal</h4>
</div>


<!-- Deadline Alert -->
<div class="alert alert-warning py-2 small text-center" role="alert">
Limite para alterações diárias: <strong>06:00 AM</strong>
</div>


<!-- Weekly Configuration Section -->
<div class="card shadow-sm border-0 mb-3">
<div class="card-body">
<h6 class="fw-bold mb-3">Dias da Semana</h6>


<div *ngFor="let day of weekDays" class="d-flex align-items-center justify-content-between mb-2 p-2 rounded"
[style.background-color]="getDayColor(day.dayOfWeek)">
<div class="d-flex align-items-center">
<input type="checkbox" class="form-check-input me-2" [(ngModel)]="day.isActive" />
<span class="fw-medium">{{ day.name }}</span>
</div>


<div *ngIf="day.isActive" class="btn-group btn-group-sm" role="group">
<input type="radio" class="btn-check" name="segment-{{day.dayOfWeek}}" id="ida-{{day.dayOfWeek}}"
value="MORNING" [(ngModel)]="day.schedule" />
<label class="btn btn-outline-success" for="ida-{{day.dayOfWeek}}">Ida</label>


<input type="radio" class="btn-check" name="segment-{{day.dayOfWeek}}" id="volta-{{day.dayOfWeek}}"
value="AFTERNOON" [(ngModel)]="day.schedule" />
<label class="btn btn-outline-success" for="volta-{{day.dayOfWeek}}">Volta</label>


<input type="radio" class="btn-check" name="segment-{{day.dayOfWeek}}" id="ambas-{{day.dayOfWeek}}"
value="BOTH" [(ngModel)]="day.schedule" />
<label class="btn btn-outline-success" for="ambas-{{day.dayOfWeek}}">Ambas</label>
</div>
</div>
</div>
</div>


<!-- Payment Status Card (Placeholder) -->
<div class="card border-0 shadow-sm mb-3">
<div class="card-body text-center">
<h6 class="fw-bold mb-1 text-secondary">Status de Pagamento</h6>
<p class="text-muted small mb-0">Comprovante de pagamento atualizado</p>
</div>
</div>


<!-- Save Button -->
<button (click)="save()" class="btn w-100 text-white fw-bold" style="background-color:#0056B3;">
Salvar Agenda Semanal
</button>


</div>
`,
})
export class WeeklyScheduleComponent implements OnInit {
passengerId = 1; // mock user (Ana Clara)
weekDays: PassengerSchedule[] = [
{ dayOfWeek: 1, passengerId: 1, schedule: 'BOTH', isActive: false },
{ dayOfWeek: 2, passengerId: 1, schedule: 'BOTH', isActive: false },
{ dayOfWeek: 3, passengerId: 1, schedule: 'BOTH', isActive: false },
{ dayOfWeek: 4, passengerId: 1, schedule: 'BOTH', isActive: false },
{ dayOfWeek: 5, passengerId: 1, schedule: 'BOTH', isActive: false },
].map((d) => ({ ...d, name: this.dayName(d.dayOfWeek) } as any));


constructor(private scheduleService: PassengerScheduleService) {}


ngOnInit() {
this.scheduleService.getByPassenger(this.passengerId).then((data) => {
if (data.length) this.weekDays = data.map((d) => ({ ...d, name: this.dayName(d.dayOfWeek) } as any));
});
}


getDayColor(dayOfWeek: number): string {
const day = this.weekDays.find((d) => d.dayOfWeek === dayOfWeek);
return day?.isActive ? '#E8F5E9' : '#EEEEEE'; // greenish active or neutral gray
}


dayName(n: number): string {
return ['Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado', 'Domingo'][n - 1];
}


async save() {
await this.scheduleService.saveAll(this.passengerId, this.weekDays);
alert('Agenda semanal salva com sucesso!');
}
}

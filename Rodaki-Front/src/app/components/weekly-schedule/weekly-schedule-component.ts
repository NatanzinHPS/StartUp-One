import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';


export interface PassengerSchedule {
id?: number;
passengerId: number;
dayOfWeek: number;
schedule: 'MORNING' | 'AFTERNOON' | 'BOTH';
isActive: boolean;
name?: string;
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
standalone: true,
imports: [CommonModule, FormsModule, HttpClientModule],
templateUrl: './weekly-schedule-component.html',
styleUrls: ['./weekly-schedule-component.scss']
})
export class WeeklyScheduleComponent implements OnInit {
passengerId = 1;
weekDays: PassengerSchedule[] = [
{ dayOfWeek: 1, passengerId: 1, schedule: 'BOTH', isActive: false, name: 'Segunda' },
{ dayOfWeek: 2, passengerId: 1, schedule: 'BOTH', isActive: false, name: 'Terça' },
{ dayOfWeek: 3, passengerId: 1, schedule: 'BOTH', isActive: false, name: 'Quarta' },
{ dayOfWeek: 4, passengerId: 1, schedule: 'BOTH', isActive: false, name: 'Quinta' },
{ dayOfWeek: 5, passengerId: 1, schedule: 'BOTH', isActive: false, name: 'Sexta' },
];


constructor(private scheduleService: PassengerScheduleService) {}


ngOnInit() {
this.scheduleService.getByPassenger(this.passengerId).then((data) => {
if (data.length) this.weekDays = data;
});
}


getDayColor(dayOfWeek: number): string {
const day = this.weekDays.find((d) => d.dayOfWeek === dayOfWeek);
return day?.isActive ? '#E8F5E9' : '#EEEEEE';
}


async save() {
await this.scheduleService.saveAll(this.passengerId, this.weekDays);
alert('Agenda semanal salva com sucesso!');
}
}
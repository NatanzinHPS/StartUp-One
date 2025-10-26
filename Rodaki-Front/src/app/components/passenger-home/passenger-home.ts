import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterOutlet, Router } from '@angular/router';
import { trigger, transition, style, animate } from '@angular/animations';
import { PassengerProfile, PassengerProfileService } from '../../services/passenger-profile';
import { CheckinService, TodayCheckin } from '../../services/checkin';

@Component({
  selector: 'app-passenger-home',
  imports: [CommonModule, FormsModule],
  templateUrl: './passenger-home.html',
  styleUrls: ['./passenger-home.scss'],
  animations: [
    trigger('fadeTransition', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('350ms ease-in-out', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('250ms ease-in-out', style({ opacity: 0 }))
      ])
    ])
  ]
})

export class PassengerHome implements OnInit {

  profile: PassengerProfile | null = null;
  todayStatus: TodayCheckin = null;
  saving = false;
  deadlineText = 'Alterações até as 16:15';

  constructor(
    private checkinService: CheckinService,
    private profileService: PassengerProfileService,
    private router: Router
  ) { }

  async ngOnInit() {
    this.profile = await this.profileService.getProfile();
    this.todayStatus = await this.checkinService.getTodayStatus();
  }

  statusLabel(s: TodayCheckin) {
    switch (s) {
      case 'IDA': return 'Ida';
      case 'VOLTA': return 'Volta';
      case 'AMBAS': return 'Ambas';
      case 'AUSENTE': return 'Ausente';
      default: return '—';
    }
  }

  statusBadgeClass(s: TodayCheckin) {
    switch (s) {
      case 'IDA': return 'badge-ida';
      case 'VOLTA': return 'badge-volta';
      case 'AMBAS': return 'badge-ambas';
      case 'AUSENTE': return 'badge-absent';
      default: return '';
    }
  }

  async setStatus(s: TodayCheckin) {
    if (this.saving || this.todayStatus === s) return;
    this.saving = true;
    await this.checkinService.setTodayStatus(s);
    this.todayStatus = s;
    await new Promise(resolve => setTimeout(resolve, 150));
    this.saving = false;
  }

  goToWeeklySchedule() {
    this.router.navigate(['/weekly-schedule']);
  }

  goToUploadProof() {
    this.router.navigate(['/upload-payment-proof']);
  }

  goToEditProfile() {
    this.router.navigate(['/dashboard']);
  }
}

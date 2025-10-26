import { Routes } from '@angular/router';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadComponent: () => import('./components/login/login').then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./components/register/register').then(m => m.RegisterComponent)
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./components/dashboard/dashboard').then(m => m.DashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'passenger-home',
    loadComponent: () => import('./components/passenger-home/passenger-home').then(m => m.PassengerHome)
    // canActivate: [authGuard]
  },
  {
    path: 'weekly-schedule',
    loadComponent: () => import('./components/weekly-schedule/weekly-schedule-component').then(m => m.WeeklyScheduleComponent),
    // canActivate: [authGuard]
  },
  {
    path: 'upload-payment-proof',
    loadComponent: () => import('./components/upload-payment-proof/upload-payment-proof').then(m => m.UploadPaymentProof)
    // canActivate: [authGuard]
  },
  {
    path: 'daily-checkin',
    loadComponent: () => import('./components/daily-checkin-list/daily-checkin-list').then(m => m.DailyCheckinList)
    // canActivate: [authGuard]
  },
  {
    path: '**',
    redirectTo: '/login'
  }
];

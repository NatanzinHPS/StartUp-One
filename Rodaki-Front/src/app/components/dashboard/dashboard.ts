import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})

export class DashboardComponent implements OnInit {
  userEmail = '';
  testResult: any = null;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    if (user) {
      this.userEmail = user.email;
    }
  }

  testPassageiros(): void {
    this.testResult = null;
    this.errorMessage = '';
    
    this.http.get('/api/passageiros').subscribe({
      next: (data) => {
        this.testResult = data;
        console.log('Passageiros:', data);
      },
      error: (error) => {
        this.errorMessage = `Erro ao buscar passageiros: ${error.status} - ${error.message}`;
        console.error('Erro:', error);
      }
    });
  }

  testMotoristas(): void {
    this.testResult = null;
    this.errorMessage = '';
    
    this.http.get('/api/motoristas').subscribe({
      next: (data) => {
        this.testResult = data;
        console.log('Motoristas:', data);
      },
      error: (error) => {
        this.errorMessage = `Erro ao buscar motoristas: ${error.status} - ${error.message}`;
        console.error('Erro:', error);
      }
    });
  }

  testEnderecos(): void {
    this.testResult = null;
    this.errorMessage = '';
    
    // Ajuste o ID conforme necessário
    this.http.get('/api/enderecos/passageiro/1').subscribe({
      next: (data) => {
        this.testResult = data;
        console.log('Endereços:', data);
      },
      error: (error) => {
        this.errorMessage = `Erro ao buscar endereços: ${error.status} - ${error.message}`;
        console.error('Erro:', error);
      }
    });
  }

  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
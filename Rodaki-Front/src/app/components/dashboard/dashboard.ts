import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';
import { MotoristaService } from '../../services/motorista';
import { PassageiroService } from '../../services/passageiro';
import { Motorista, Passageiro } from '../../models/user-model';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})

export class DashboardComponent implements OnInit {
  motoristas: Motorista[] = [];
  passageiros: Passageiro[] = [];
  loadingMotoristas = true;
  loadingPassageiros = true;

  constructor(
    private authService: AuthService,
    private motoristaService: MotoristaService,
    private passageiroService: PassageiroService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadMotoristas();
    this.loadPassageiros();
  }

  loadMotoristas(): void {
    this.motoristaService.listarTodos().subscribe({
      next: (data) => {
        this.motoristas = data;
        this.loadingMotoristas = false;
      },
      error: (error) => {
        console.error('Erro ao carregar motoristas:', error);
        this.loadingMotoristas = false;
      }
    });
  }

  loadPassageiros(): void {
    this.passageiroService.listarTodos().subscribe({
      next: (data) => {
        this.passageiros = data;
        this.loadingPassageiros = false;
      },
      error: (error) => {
        console.error('Erro ao carregar passageiros:', error);
        this.loadingPassageiros = false;
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
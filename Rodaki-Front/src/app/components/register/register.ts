import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-register',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class RegisterComponent {
  nome = '';
  email = '';
  senha = '';
  role: 'ROLE_PASSAGEIRO' | 'ROLE_MOTORISTA' = 'ROLE_PASSAGEIRO';
  loading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onRegister(): void {
    if (!this.nome || !this.email || !this.senha) {
      this.errorMessage = 'Por favor, preencha todos os campos';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.authService.register({
      nome: this.nome,
      email: this.email,
      senha: this.senha,
      role: this.role
    }).subscribe({
      next: (response) => {
        console.log('Registro realizado com sucesso!', response);
        this.successMessage = 'Cadastro realizado com sucesso! Redirecionando...';
        
        setTimeout(() => {
          this.router.navigate(['/dashboard']);
        }, 2000);
      },
      error: (error) => {
        console.error('Erro ao fazer cadastro:', error);
        this.errorMessage = error.error?.message || 'Erro ao realizar cadastro. Tente novamente.';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}

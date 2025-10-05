import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})

export class LoginComponent {
  email = '';
  senha = '';
  loading = false;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onLogin(): void {
    if (!this.email || !this.senha) {
      this.errorMessage = 'Por favor, preencha todos os campos';
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.authService.login({ email: this.email, senha: this.senha })
      .subscribe({
        next: (response) => {
          console.log('Login realizado com sucesso!', response);
          this.router.navigate(['/dashboard']);
        },
        error: (error) => {
          console.error('Erro ao fazer login:', error);
          this.errorMessage = 'Credenciais inválidas. Tente novamente.';
          this.loading = false;
        },
        complete: () => {
          this.loading = false;
        }
      });
  }
}
import { Injectable } from '@angular/core';

/**
 * Estrutura do perfil do passageiro (mock)
 */
export interface PassengerProfile {
  id: number;
  name: string;
  address: string;
  phone: string;
  email: string;
  avatarUrl?: string;
}

@Injectable({
  providedIn: 'root'
})

export class PassengerProfileService {
  private profile: PassengerProfile = {
    id: 1,
    name: 'Ana Clara Santos',
    address: 'Rua das Flores, 123 - Centro',
    phone: '(11) 99999-9999',
    email: 'ana.clara@email.com',
    avatarUrl: 'assets/avatar-female.jpg'
  };

  /**
   * Obtém os dados do perfil do passageiro logado
   */
  async getProfile(): Promise<PassengerProfile> {
    return new Promise(resolve => setTimeout(() => resolve(this.profile), 200));
  }

  /**
   * Atualiza o perfil (mock local)
   */
  async updateProfile(updated: Partial<PassengerProfile>): Promise<PassengerProfile> {
    this.profile = { ...this.profile, ...updated };
    return new Promise(resolve => setTimeout(() => resolve(this.profile), 300));
  }
}

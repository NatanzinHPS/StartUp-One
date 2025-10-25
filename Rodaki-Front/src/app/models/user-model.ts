export enum Role {
  ROLE_PASSAGEIRO = 'ROLE_PASSAGEIRO',
  ROLE_MOTORISTA = 'ROLE_MOTORISTA',
  ROLE_ADMIN = 'ROLE_ADMIN'
}

export interface User {
  id: number;
  nome: string;
  email: string;
  role: Role;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  role: Role;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}

export interface Motorista {
  id?: number;
  user: User;
  veiculo: string;
  placa: string;
  capacidade: number;
  latitude: number;
  longitude: number;
}

export interface Passageiro {
  id?: number;
  user: User;
  endereco: string;
  latitude: number;
  longitude: number;
}
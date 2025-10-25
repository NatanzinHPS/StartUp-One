export enum Role {
  ROLE_PASSAGEIRO = 'ROLE_PASSAGEIRO',
  ROLE_MOTORISTA = 'ROLE_MOTORISTA',
  ROLE_ADMIN = 'ROLE_ADMIN'
}

export interface User {
  id: number;
  name: string;
  email: string;
  phone?: string;
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
  passengerIds: number[];
}

export interface Passageiro {
  id?: number;
  user: User;
  addresses: string[];
}
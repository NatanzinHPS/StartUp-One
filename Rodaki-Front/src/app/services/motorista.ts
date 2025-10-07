import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Motorista } from '../models/user-model';

@Injectable({ providedIn: 'root' })
export class MotoristaService {
  private apiUrl = '/api/motoristas';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<Motorista[]> {
    return this.http.get<Motorista[]>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<Motorista> {
    return this.http.get<Motorista>(`${this.apiUrl}/${id}`);
  }

  salvar(motorista: Motorista): Observable<Motorista> {
    return this.http.post<Motorista>(this.apiUrl, motorista);
  }

  atualizar(id: number, motorista: Motorista): Observable<Motorista> {
    return this.http.put<Motorista>(`${this.apiUrl}/${id}`, motorista);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
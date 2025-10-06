import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Passageiro } from '../models/user-model';

@Injectable({ providedIn: 'root' })
export class PassageiroService {
  private apiUrl = '/api/passageiros';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<Passageiro[]> {
    return this.http.get<Passageiro[]>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<Passageiro> {
    return this.http.get<Passageiro>(`${this.apiUrl}/${id}`);
  }

  salvar(passageiro: Passageiro): Observable<Passageiro> {
    return this.http.post<Passageiro>(this.apiUrl, passageiro);
  }

  atualizar(id: number, passageiro: Passageiro): Observable<Passageiro> {
    return this.http.put<Passageiro>(`${this.apiUrl}/${id}`, passageiro);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
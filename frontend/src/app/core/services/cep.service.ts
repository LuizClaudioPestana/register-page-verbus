import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface ViaCepResponse {
  cep?: string;
  logradouro?: string;
  complemento?: string;
  bairro?: string;
  localidade?: string;
  uf?: string;
  erro?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class CepService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.viaCepBaseUrl;

  findByCep(cep: string): Observable<ViaCepResponse> {
    const sanitizedCep = cep.replace(/\D/g, '');
    return this.http.get<ViaCepResponse>(`${this.baseUrl}/${sanitizedCep}/json/`);
  }
}
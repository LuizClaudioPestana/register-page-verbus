import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import {
  AuthResponse,
  ForgotPasswordRequest,
  ForgotPasswordResponse,
  LoginRequest,
  RegisterRequest,
  ResetPasswordRequest
} from '../../shared/models/auth.models';
import { Observable } from 'rxjs';
import { User } from '../../shared/models/user.models';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = environment.apiBaseUrl;

  register(payload: RegisterRequest): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/auth/register`, payload);
  }

  login(payload: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/login`, payload);
  }

  forgotPassword(
    payload: ForgotPasswordRequest
  ): Observable<ForgotPasswordResponse> {
    return this.http.post<ForgotPasswordResponse>(
      `${this.apiUrl}/auth/forgot-password`,
      payload
    );
  }

  resetPassword(payload: ResetPasswordRequest): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(
      `${this.apiUrl}/auth/reset-password`,
      payload
    );
  }

  me(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/users/me`);
  }
}
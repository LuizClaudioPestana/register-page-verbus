import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly storageKey = 'authapp_token';

  setToken(token: string): void {
    localStorage.setItem(this.storageKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.storageKey);
  }

  hasToken(): boolean {
    return !!this.getToken();
  }

  clear(): void {
    localStorage.removeItem(this.storageKey);
  }
}
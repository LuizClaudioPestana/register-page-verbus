import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { User } from '../../shared/models/user.models';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { TokenService } from '../../core/services/token.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  private readonly authService = inject(AuthService);
  private readonly tokenService = inject(TokenService);
  private readonly router = inject(Router);

  user: User | null = null;

  ngOnInit(): void {
    this.authService.me().subscribe({
      next: (response) => {
        this.user = response;
      }
    });
  }

  logout(): void {
    this.tokenService.clear();
    this.router.navigate(['/login']);
  }
}
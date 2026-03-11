import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.scss'
})
export class ForgotPasswordComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  loading = false;
  generatedToken: string | null = null;
  expiresAt: string | null = null;

  readonly form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]]
  });

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;

    this.authService.forgotPassword(this.form.getRawValue()).subscribe({
      next: (response) => {
        this.generatedToken = response.resetToken;
        this.expiresAt = response.expiresAt;
      },
      error: () => {
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  goToReset(): void {
    const email = this.form.controls.email.value;
    this.router.navigate(['/reset-password'], {
      queryParams: {
        email,
        token: this.generatedToken ?? ''
      }
    });
  }
}
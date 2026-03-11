import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ResetPasswordRequest } from '../../../shared/models/auth.models';

function passwordMatchValidator(
  passwordField: string,
  confirmPasswordField: string
): ValidatorFn {
  return (group: AbstractControl): ValidationErrors | null => {
    const password = group.get(passwordField)?.value;
    const confirmPassword = group.get(confirmPasswordField)?.value;

    if (!password || !confirmPassword) {
      return null;
    }

    return password === confirmPassword ? null : { passwordMismatch: true };
  };
}

@Component({
  selector: 'app-reset-password',
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
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly route = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  loading = false;

  readonly form = this.fb.nonNullable.group(
    {
      email: ['', [Validators.required, Validators.email]],
      token: ['', [Validators.required]],
      newPassword: [
        '',
        [
          Validators.required,
          Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/)
        ]
      ],
      confirmNewPassword: ['', [Validators.required]]
    },
    {
      validators: [passwordMatchValidator('newPassword', 'confirmNewPassword')]
    }
  );

  ngOnInit(): void {
    const email = this.route.snapshot.queryParamMap.get('email');
    const token = this.route.snapshot.queryParamMap.get('token');

    if (email) {
      this.form.controls.email.setValue(email);
    }

    if (token) {
      this.form.controls.token.setValue(token);
    }
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;

    const payload: ResetPasswordRequest = {
      ...this.form.getRawValue()
    };

    this.authService.resetPassword(payload).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: () => {
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  get passwordMismatch(): boolean {
    return !!this.form.errors?.['passwordMismatch'] &&
      this.form.controls.confirmNewPassword.touched;
  }
}
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatBottomSheet, MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { AuthService } from '../../../core/services/auth.service';
import { CepService } from '../../../core/services/cep.service';
import { AdditionalInfoBottomSheetComponent } from '../../../shared/components/additional-info-bottom-sheet/additional-info-bottom-sheet.component';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { debounceTime, distinctUntilChanged, filter } from 'rxjs';
import { RegisterRequest } from '../../../shared/models/auth.models';

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
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    MatBottomSheetModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly cepService = inject(CepService);
  private readonly router = inject(Router);
  private readonly bottomSheet = inject(MatBottomSheet);

  loading = false;

  readonly form = this.fb.nonNullable.group(
    {
      fullName: ['', [Validators.required, Validators.maxLength(120)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(150)]],
      password: [
        '',
        [
          Validators.required,
          Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/)
        ]
      ],
      confirmPassword: ['', [Validators.required]],
      cep: ['', [Validators.maxLength(8)]],
      street: ['', [Validators.maxLength(180)]],
      number: ['', [Validators.maxLength(20)]],
      complement: ['', [Validators.maxLength(120)]],
      neighborhood: ['', [Validators.maxLength(120)]],
      city: ['', [Validators.maxLength(120)]],
      state: ['', [Validators.maxLength(2)]],
      additionalInfo: ['', [Validators.maxLength(1000)]],
      notes: ['', [Validators.maxLength(1000)]]
    },
    {
      validators: [passwordMatchValidator('password', 'confirmPassword')]
    }
  );

  constructor() {
    this.listenCepChanges();
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;

    const payload: RegisterRequest = {
      ...this.form.getRawValue()
    };

    this.authService.register(payload).subscribe({
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

  openBottomSheet(): void {
    const ref = this.bottomSheet.open(AdditionalInfoBottomSheetComponent, {
      data: this.form.controls.additionalInfo.value
    });

    ref.afterDismissed().subscribe((result?: string) => {
      if (typeof result === 'string') {
        this.form.controls.additionalInfo.setValue(result);
      }
    });
  }

  private listenCepChanges(): void {
    this.form.controls.cep.valueChanges
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        filter((value) => value.replace(/\D/g, '').length === 8)
      )
      .subscribe((cep) => {
        this.cepService.findByCep(cep).subscribe({
          next: (response) => {
            if (response.erro) {
              return;
            }

            this.form.patchValue({
              street: response.logradouro ?? '',
              complement: response.complemento ?? '',
              neighborhood: response.bairro ?? '',
              city: response.localidade ?? '',
              state: response.uf ?? ''
            });
          }
        });
      });
  }

  get formErrorPasswordMismatch(): boolean {
    return !!this.form.errors?.['passwordMismatch'] &&
      this.form.controls.confirmPassword.touched;
  }

  get passwordHintValid(): boolean {
    const value = this.form.controls.password.value;
    return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/.test(value);
  }
}
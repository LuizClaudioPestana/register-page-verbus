import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, throwError } from 'rxjs';
import { ApiErrorResponse } from '../../shared/models/api-error.model';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const snackBar = inject(MatSnackBar);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let message = 'Ocorreu um erro inesperado.';

      if (error.error) {
        const apiError = error.error as ApiErrorResponse;

        if (apiError.details?.length) {
          message = apiError.details.join(' | ');
        } else if (apiError.message) {
          message = apiError.message;
        }
      }

      snackBar.open(message, 'Fechar', {
        duration: 5000
      });

      return throwError(() => error);
    })
  );
};
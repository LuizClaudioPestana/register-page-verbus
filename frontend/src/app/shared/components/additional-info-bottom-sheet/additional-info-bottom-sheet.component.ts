import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MAT_BOTTOM_SHEET_DATA,
  MatBottomSheetModule,
  MatBottomSheetRef
} from '@angular/material/bottom-sheet';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-additional-info-bottom-sheet',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatBottomSheetModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './additional-info-bottom-sheet.component.html',
  styleUrl: './additional-info-bottom-sheet.component.scss'
})
export class AdditionalInfoBottomSheetComponent {
  readonly control: FormControl<string>;

  constructor(
    private readonly bottomSheetRef: MatBottomSheetRef<AdditionalInfoBottomSheetComponent>,
    @Inject(MAT_BOTTOM_SHEET_DATA) public data: string | null
  ) {
    this.control = new FormControl<string>(data ?? '', {
      nonNullable: true,
      validators: [Validators.maxLength(1000)]
    });
  }

  save(): void {
    this.bottomSheetRef.dismiss(this.control.value.trim());
  }

  close(): void {
    this.bottomSheetRef.dismiss();
  }
}
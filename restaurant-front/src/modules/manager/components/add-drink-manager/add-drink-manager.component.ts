import { Component, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Ingredient } from 'src/modules/shared/models/ingredient';
import { AddManagerDrink } from 'src/modules/shared/models/item-models/add-manager-drink';
import { Select } from 'src/modules/shared/models/select';
import { ItemService } from '../../services/item-service/item.service';

@Component({
  selector: 'app-add-drink-manager',
  templateUrl: './add-drink-manager.component.html',
  styleUrls: ['./add-drink-manager.component.scss'],
})
export class AddDrinkManagerComponent implements OnInit {
  addDrinkForm: FormGroup;
  isImageSaved: boolean = false;
  url: any;

  drinkTypes: Select[] = [
    { value: 'COFFEE', viewValue: 'Coffee' },
    { value: 'COLD_DRINK', viewValue: 'Cold drink' },
    { value: 'HOT_DRINK', viewValue: 'Hot drink' },
    { value: 'ALCOHOLIC', viewValue: 'Alcoholic' },
  ];
  containers: Select[] = [
    { value: 'BOTTLE', viewValue: 'Bottle' },
    { value: 'GLASS', viewValue: 'Glass' },
    { value: 'PITCHER', viewValue: 'Pitcher' },
  ];

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private itemService: ItemService,
    private dialogRef: MatDialogRef<AddDrinkManagerComponent>
  ) {
    this.addDrinkForm = this.fb.group({
      name: [null, Validators.required],
      container: [null, Validators.required],
      drinkType: [null, Validators.required],
    });
  }

  ngOnInit(): void {}

  fileChangeEvent(event: any) {
    let reader = new FileReader();
    if (event.target.files && event.target.files.length > 0) {
      let file = event.target.files[0];
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.url = reader.result;
        this.isImageSaved = true;
      };
    }
  }

  saveDrink() {
    if (
      this.addDrinkForm.value.name === null ||
      this.addDrinkForm.get('container')?.value === '' ||
      this.addDrinkForm.get('drinkType')?.value === '' ||
      this.isImageSaved == false
    ) {
      this.toastr.error('All fields must be filled in!');
    } else {
      const newDrink: AddManagerDrink = {
        name: this.addDrinkForm.value.name,
        image: this.url.split(',')[1],
        drinkType: this.addDrinkForm.value.drinkType,
        containerType: this.addDrinkForm.value.container,
        ingredients: new Array<Ingredient>(),
      };
      this.itemService.saveMenuItem(newDrink).subscribe({
        next: (success) => {
          this.toastr.success(
            'Successfully added and approved ' + success.name
          );
          this.dialogRef.close(success);
        },
        error: (error) => {
          this.toastr.error('Unable to add new item');
          console.log(error);
        },
      });
    }
  }

  cancel() {
    this.dialogRef.close();
  }
}

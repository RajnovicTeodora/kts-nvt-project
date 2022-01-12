import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Ingredient } from 'src/modules/shared/models/ingredient';
import { AddManagerDrink } from 'src/modules/shared/models/item-models/add-manager-drink';
import { Select } from 'src/modules/shared/models/select';

@Component({
  selector: 'app-add-drink-manager',
  templateUrl: './add-drink-manager.component.html',
  styleUrls: ['./add-drink-manager.component.scss'],
})
export class AddDrinkManagerComponent implements OnInit {
  addDrinkForm: FormGroup;
  hide = true;
  file: File | undefined;
  image: string | undefined;
  imageError: string;
  isImageSaved: boolean;
  url: any;

  fileChangeEvent(event: any) {
    if (!event.target.files[0] || event.target.files[0].length == 0) {
      return;
    }

    var mimeType = event.target.files[0].type;

    if (mimeType.match(/image\/*/) == null) {
      return;
    }

    var reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);

    reader.onload = (_event) => {
      this.url = reader.result;
    };
  }

  onFileSelected(event: any) {
    console.log(typeof event);
    const file: File = event.target.files[0];

    if (file) {
      //this.fileToUpload = file.name;

      const formData = new FormData();

      formData.append('thumbnail', file);
      console.log(file);
    }
  }

  drinkTypes: Select[] = [
    { value: 'coffee', viewValue: 'Coffee' },
    { value: 'cold drink', viewValue: 'Cold drink' },
    { value: 'hot drink', viewValue: 'Hot drink' },
    { value: 'alcoholic', viewValue: 'Alcoholic' },
  ];
  containers: Select[] = [
    { value: 'bottle', viewValue: 'Bottle' },
    { value: 'glass', viewValue: 'Glass' },
    { value: 'pitcher', viewValue: 'Pitcher' },
  ];

  selectedType = '';
  selectedContainer = '';

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService
  ) {
    this.addDrinkForm = this.fb.group({
      name: [null, Validators.required],
      container: [null, Validators.required],
      drinkType: [null, Validators.required],
    });
  }

  ngOnInit(): void {}

  changeType(value: string | null) {
    if (value != null) {
      this.selectedType = value; //(<HTMLInputElement>event.target).value;
      console.log(this.selectedType, 'type');
    }
  }

  changeContainer(value: string | null) {
    if (value != null) {
      console.log('uslo');
      this.selectedContainer = value; //(<HTMLInputElement>event.target).value;
      console.log(this.selectedContainer, 'ccc');
    }
  }

  saveDrink() {
    if (
      this.addDrinkForm.value.name === null ||
      this.selectedContainer === '' ||
      this.selectedType === ''
    ) {
      //todo ovde mozda i nije null za check box
      this.toastr.error('All fields must be filled in!');
      console.log(this.addDrinkForm.value.name);
      console.log(this.selectedContainer);
      console.log(this.selectedType);
    } else {
      const newDrink: AddManagerDrink = {
        name: this.addDrinkForm.value.name,
        image: '',
        type: this.addDrinkForm.value.drinkType,
        containerType: this.addDrinkForm.value.container,
        ingredients: new Array<Ingredient>(),
      };
      console.log('super', newDrink);
    }
  }
}

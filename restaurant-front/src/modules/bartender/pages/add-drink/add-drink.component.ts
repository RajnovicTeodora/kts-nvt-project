import { Component,  OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, ReactiveFormsModule} from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DrinkType } from 'src/modules/shared/models/drink-type';
import { Container } from 'src/modules/shared/models/drink-container';

@Component({
  selector: 'app-add-drink',
  templateUrl: './add-drink.component.html',
  styleUrls: ['./add-drink.component.scss']
})

export class AddDrinkComponent implements OnInit {
  
  addDrinkForm: FormGroup ;

  hide = true; 

  drinkTypes: DrinkType[] = [
    {value: 'coffee', viewValue: 'coffee'},
    {value: 'cold drink', viewValue: 'cold drink'},
    {value: 'hot drink', viewValue: 'Hot drink'},
    {value: 'alcoholic', viewValue: 'Alcoholic'},
];
containers: Container[] = [
  {value: 'bottle', viewValue: 'Bottle'},
  {value: 'glass', viewValue: 'Glass'},
  {value: 'pitcher', viewValue: 'Pitcher'}
];

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    ) {
    this.addDrinkForm = this.fb.group({
      name: [null, Validators.required],
      container: [null, Validators.required],
      ingrediants: [null],
      type: [null, Validators.required],
    });
   }

  ngOnInit(): void {
  }

  saveDrink(){
    if(this.addDrinkForm.value.name === null || this.addDrinkForm.value.container === null || this.addDrinkForm.value.type === null){ //todo ovde mozda i nije null za check box
      this.toastr.error("All fields must be filled in!");
    }else{
       console.log("super");
      }


  }

}

import { Component,  OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, ReactiveFormsModule} from '@angular/forms';
import { MatSelect, MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DrinkType } from 'src/modules/shared/models/drink-type';
import { Container } from 'src/modules/shared/models/drink-container';
import { Drink2 } from 'src/modules/shared/models/Drink2';
import { HttpClient } from '@angular/common/http';
import { AddDrinkService } from '../../service/drinks/add-drink.service';
import { Ingredient } from 'src/modules/shared/models/ingredient';

@Component({
  selector: 'app-add-drink',
  templateUrl: './add-drink.component.html',
  styleUrls: ['./add-drink.component.scss']
})

export class AddDrinkComponent implements OnInit {
  
  addDrinkForm: FormGroup ;
  listIngredients: Ingredient[] = []
  hide = true; 
  fileName = '';

  drinkTypes: DrinkType[] = [
    {value: 'COFFEE', viewValue: 'coffee'},
    {value: 'COLD_DRINK', viewValue: 'cold drink'},
    {value: 'HOT_DRINK', viewValue: 'Hot drink'},
    {value: 'ALCOHOLIC', viewValue: 'Alcoholic'},
  ];

  containers: Container[] = [
    {value: 'BOTTLE', viewValue: 'Bottle'},
    {value: 'GLASS', viewValue: 'Glass'},
    {value: 'PITCHER', viewValue: 'Pitcher'}
  ];

  selectedContainer: string;
  selectedValue: string;
  
  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private http: HttpClient,
    private drinkService: AddDrinkService
    ) {
      this.selectedValue ="";
      this.selectedContainer ="";
      this.addDrinkForm = this.fb.group({ 
      name: [null, Validators.required],
      ingrediants: [null],
    });
   }

  ngOnInit(): void {
  }

  onFileSelected(event: any) {
    const file:File = event.target.files[0];

    if (file) {
        this.fileName = file.name;
        const formData = new FormData();
        formData.append("thumbnail", file);
    }
  }

  onaddIngredient(ingredient: Ingredient){
    this.listIngredients.push(ingredient);
  }

  saveDrink(){
    if(this.addDrinkForm.value.name === null || this.selectedContainer === "" || this.selectedValue === ""){
      this.toastr.error("All fields must be filled in!");
    }else{
      const newDrink:  Drink2 = {
        name: this.addDrinkForm.value.name,
        drinkType: this.selectedValue,
        containerType: this.selectedContainer,
        image: "aaa", //todo
        price: 0,
        ingredients: this.listIngredients
      }
      this.drinkService.addDrink(newDrink).subscribe((result)=>{console.log("uradi nesto pametno", result);});
      }
  }
}

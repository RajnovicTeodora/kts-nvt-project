import { Component,  OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DrinkBartender } from 'src/modules/shared/models/drink-bartender';
import { AddDrinkService } from '../../service/drinks/add-drink.service';
import { Ingredient } from 'src/modules/shared/models/ingredient';
import { Select } from 'src/modules/shared/models/select';

@Component({
  selector: 'app-add-drink',
  templateUrl: './add-drink.component.html',
  styleUrls: ['./add-drink.component.scss']
})

export class AddDrinkComponent implements OnInit {
  
  addDrinkForm: FormGroup ;
  listIngredients: Ingredient[] = []
  fileName = '';
  url: any;
  isImageSaved: boolean = false;

  drinkTypes: Select[] = [
    {value: 'COFFEE', viewValue: 'coffee'},
    {value: 'COLD_DRINK', viewValue: 'cold drink'},
    {value: 'HOT_DRINK', viewValue: 'Hot drink'},
    {value: 'ALCOHOLIC', viewValue: 'Alcoholic'},
  ];

  containers: Select[] = [
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
    private drinkService: AddDrinkService
    ) {
      this.selectedValue ="";
      this.selectedContainer ="";
      this.addDrinkForm = this.fb.group({ 
      name: [null, Validators.required],
      ingredients: [null],
    });
   }

  ngOnInit(): void {
  }


  onFileSelected(event: any) {
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


  onAddIngredient(ingredient: Ingredient){
    this.listIngredients.push(ingredient);
  }
  onDeleteIngredient(ingredient: any){
    const newList = this.listIngredients.filter(ing => ing.name !== ingredient.name);
    this.listIngredients = [...newList];
  }

  saveDrink(){
    if(this.addDrinkForm.value.name === null || this.selectedContainer === "" || this.selectedValue === ""  || this.isImageSaved == false){
      this.toastr.error("All fields must be filled in!");
    }else{
      const newDrink:  DrinkBartender = {
        name: this.addDrinkForm.value.name,
        drinkType: this.selectedValue,
        containerType: this.selectedContainer,
        image: this.url.split(',')[1],
        price: 0,
        ingredients: this.listIngredients
      }
      this.drinkService.addDrink(newDrink).subscribe(
        (result)=>{
          this.toastr.success("You added drink!");
      });
      }
  }
}

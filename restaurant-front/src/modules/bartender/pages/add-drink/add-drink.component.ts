import { Component,  OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DrinkBartender } from 'src/modules/shared/models/drinkBartender';
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
  hide = true; 
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
      ingrediants: [null],
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
    console.log(ingredient)
    this.listIngredients.push()
    console.log(this.listIngredients)
  }

  saveDrink(){
    if(this.addDrinkForm.value.name === null || this.selectedContainer === "" || this.selectedValue === "" || this.isImageSaved == false){
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

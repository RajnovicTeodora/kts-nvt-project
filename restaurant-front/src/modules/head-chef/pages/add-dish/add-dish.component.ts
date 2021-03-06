import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Ingredient } from 'src/modules/shared/models/ingredient';
import { AddChefDish } from 'src/modules/shared/models/item-models/add-chef-dish';
import { Select } from 'src/modules/shared/models/select';
import { DishesService } from 'src/modules/head-chef/service/dish/dishes.service';

@Component({
  selector: 'app-add-dish',
  templateUrl: './add-dish.component.html',
  styleUrls: ['./add-dish.component.scss']
})
export class AddDishComponent implements OnInit {

  addDishForm: FormGroup ;
  listIngredients: Ingredient[] = []
  fileName = '';
  url: any;
  isImageSaved: boolean = false;
  isSaved="false";

  dishTypes: Select[] = [
    {value: 'DESERT', viewValue: 'Desert'},
    {value: 'ENTREE', viewValue: 'Entree'},
    {value: 'MAIN_DISH', viewValue: 'Main dish'},
    {value: 'SALAD', viewValue: 'Salad'},
    {value: 'SOUP', viewValue: 'Soup'},
];

  selectedType: string;
  
  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private dishService: DishesService
    ) {
      this.selectedType ="";
      this.addDishForm = this.fb.group({ 
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

  saveDish(){
    if(this.addDishForm.value.name === null || this.selectedType === ""  || this.isImageSaved == false){
      this.toastr.error("All fields must be filled in!");
    }else{
      const newDish:  AddChefDish = {
        name: this.addDishForm.value.name,
        type: this.selectedType,
        image: this.url.split(',')[1],
        ingredients: this.listIngredients
      }
      this.dishService.addDish(newDish).subscribe(
      {
        next: (success) => {
          this.toastr.success(
            'Successfully added ' + success.name
          );
          this.isSaved = "true";
        },
        error: (error) => {
          this.toastr.error('Item already exists!');
          console.log(error);this.isSaved = "false";
        },
      }
      );
      }
  }

}

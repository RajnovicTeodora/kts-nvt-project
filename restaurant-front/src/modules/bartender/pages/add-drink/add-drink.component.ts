import { Component,  OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, ReactiveFormsModule} from '@angular/forms';
import { MatSelect, MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DrinkType } from 'src/modules/shared/models/drink-type';
import { Container } from 'src/modules/shared/models/drink-container';
import { Drink } from 'src/modules/shared/models/drink';
import { HttpClient } from '@angular/common/http';
import { AddDrinkService } from '../../service/add-drink.service';

@Component({
  selector: 'app-add-drink',
  templateUrl: './add-drink.component.html',
  styleUrls: ['./add-drink.component.scss']
})

export class AddDrinkComponent implements OnInit {
  
  addDrinkForm: FormGroup ;

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
    this.addDrinkForm = this.fb.group({ //todo izbaci nepotrebnoo, pobrisi komentare
      name: [null, Validators.required],
      container: [null, Validators.required],
      ingrediants: [null],
      type: [null, Validators.required],
    });
   }

  ngOnInit(): void {
  }

  // changeType(value: string|null){
  //   if(value !=null){
  //   this.selectedType = value//(<HTMLInputElement>event.target).value;
  //   console.log(this.selectedType, "type")}
  // }

  // changeContainer(value: string|null){
  //   if(value !=null){
  //   console.log("uslo")
  //   this.selectedContainer = value;//(<HTMLInputElement>event.target).value;
  //   console.log(this.selectedContainer, "ccc")}
  // }
  
  onFileSelected(event: any) {
    console.log(typeof(event));
    const file:File = event.target.files[0];

    if (file) {

        this.fileName = file.name;

        const formData = new FormData();

        formData.append("thumbnail", file);
        console.log(file)
    }
  }

  saveDrink(){
    console.log(this.selectedValue+" aaa")
    
    if(this.addDrinkForm.value.name === null || this.selectedContainer === "" || this.selectedValue === ""){ //todo ovde mozda i nije null za check box
      this.toastr.error("All fields must be filled in!");
    }else{
      const newDrink:  Drink = {
        name: this.addDrinkForm.value.name,
        drinkType: this.selectedValue,
        containerType: this.selectedContainer,
        image: "aaa",
        price: 0
      }
       this.drinkService.addDrink(newDrink).subscribe((result)=>{console.log("vratilo se", result);});
       console.log("super", newDrink);
      }


  }

}

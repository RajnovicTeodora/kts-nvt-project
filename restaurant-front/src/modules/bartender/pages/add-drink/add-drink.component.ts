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

  selectedType = "";
  selectedContainer = "";

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private http: HttpClient
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

  changeType(value: string|null){
    if(value !=null){
    this.selectedType = value//(<HTMLInputElement>event.target).value;
    console.log(this.selectedType, "type")}
  }

  changeContainer(value: string|null){
    if(value !=null){
    console.log("uslo")
    this.selectedContainer = value;//(<HTMLInputElement>event.target).value;
    console.log(this.selectedContainer, "ccc")}
  }
  
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
    
    if(this.addDrinkForm.value.name === null || this.selectedContainer === "" || this.selectedType === ""){ //todo ovde mozda i nije null za check box
      this.toastr.error("All fields must be filled in!");
      console.log(this.addDrinkForm.value.name );
      console.log(this.selectedContainer );
      console.log( this.selectedType );
    }else{
      const newDrink:  Drink = {
        name: this.addDrinkForm.value.name,
        type: this.addDrinkForm.value.drinkType,
        container: this.addDrinkForm.value.container,
        image: "aaa" 
      }
       console.log("super", newDrink);
      }


  }

}

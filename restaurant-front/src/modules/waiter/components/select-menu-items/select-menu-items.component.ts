import { BreakpointObserver } from '@angular/cdk/layout';
import {
  Component,
  EventEmitter,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { SearchMenuItemsService } from 'src/modules/shared/services/search-menu-items-service/search-menu-items.service';


@Component({
  selector: 'app-select-menu-items',
  templateUrl: './select-menu-items.component.html',
  styleUrls: ['./select-menu-items.component.scss'],
})
export class SelectMenuItemsComponent implements OnInit {
  user: UserWithToken;
  filterGroup: string;
  menuItems: any[];
  searchName : string;
  groups: any[];
  drinkTypes: any[];
  dishTypes: any[];
  item: any;
  searchInput: string;
  showModalCustomizeOrderedItem: number;
  @Output() onAddToOrderForwarded = new EventEmitter();  

  constructor(
    public router: Router,
    private searchMenuItemsService: SearchMenuItemsService,
    private toastr: ToastrService,
  ) {
    const temp = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
    this.user = temp.value;
    this.filterGroup = '...';
    this.searchName = "...";
    this.menuItems = [];
    this.groups = ["drink", "dish"];
    this.drinkTypes = [];
    this.dishTypes = [];
    this.item = "";
    this.searchInput = "";
    this.showModalCustomizeOrderedItem = -1;
  }

  ngOnInit() {
    this.search();
    this.getDrinkTypes();
    this.getDishTypes();
    }

  search() {
    this.searchMenuItemsService.searchAndFilterItem(this.filterGroup, this.searchName).subscribe(
      {
        next: (result) => {
          this.menuItems = result;
        },
        error: data => {
          this.toastr.error(data.error);       
        }
      }
    );
  }

  getDrinkTypes() {
    this.searchMenuItemsService.getDrinkTypes().subscribe({
      next: (result) => {
        this.drinkTypes = result;
      },
      error: data => {
        this.toastr.error(data.error);       
      }
    })
  }

  getDishTypes() {
    this.searchMenuItemsService.getDishTypes().subscribe({
      next: (result) => {
        this.dishTypes = result;
      },
      error: data => {
        this.toastr.error(data.error);       
      }
    })
  }


  editGroupName(group: string){
    group = group.toLowerCase();
    var firstLetter = group.charAt(0).toUpperCase();

    group = firstLetter + group.substring(1);
    group = group.replace('_', ' ');
    return group;
  }


  onSearchClicked(input:string){
    if(input !== ""){
      this.searchName = input;
      this.searchInput = input;
    }
    else {
      this.searchName = "...";
      this.searchInput = "";
    }
    this.search();


  }

  onFilterClick(group:string){
    this.filterGroup = group;
    this.searchName = "...";
    this.searchInput = "";
    this.search();
  }

  onBackClick() {
    if(this.searchName !== "..."){
      this.searchName = "...";
    }
    else if(this.filterGroup === "drink" || this.filterGroup === "dish"){
      this.filterGroup = "...";
    }
    else if(this.drinkTypes.includes(this.filterGroup)) {
      this.filterGroup = "drink";
    }
    else if(this.dishTypes.includes(this.filterGroup)) {
      this.filterGroup = "dish";
    }
    if(this.searchInput !== "") {
      this.searchInput = "";
    }
    this.search();
  }

  onCustomizeOrderedItemClicked(itemId: number){
    this.showModalCustomizeOrderedItem = itemId;
  }

  onCustomizeOrderedItemCloseClicked(item: any){
    this.showModalCustomizeOrderedItem = -1;
  }

  onAddToOrderClicked(item:any){
    this.onAddToOrderForwarded.emit(item);
    this.showModalCustomizeOrderedItem = -1;
  }

}

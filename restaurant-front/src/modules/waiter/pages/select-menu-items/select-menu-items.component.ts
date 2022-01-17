import { BreakpointObserver } from '@angular/cdk/layout';
import {
  Component,
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
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  pageSize: number;
  currentPage: number;
  data: any[];
  imagePath: string;
  user: UserWithToken;
  filterGroup: string;
  menuItems: any[];
  searchName : string;
  groups: any[];
  drinkTypes: any[];
  dishTypes: any[];
  tableId: number;
  item: any;
  searchInput: string;

  constructor(
    private fb: FormBuilder,
    private observer: BreakpointObserver,
    public router: Router,
    private searchMenuItemsService: SearchMenuItemsService,
    private toastr: ToastrService,
  ) {
    this.pageSize = 1;
    this.currentPage = 0;
    this.data = [];
    this.imagePath = 'assets/images/floor3.png';
    const temp = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
    this.user = temp.value;
    this.filterGroup = '...';
    this.searchName = "...";
    this.menuItems = [];
    this.groups = ["drink", "dish"];
    this.drinkTypes = [];
    this.dishTypes = [];
    this.tableId = -1;
    this.item = "";
    this.searchInput = "";
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

  ngAfterViewInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((res) => {
      if (res.matches) {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      } else {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
    });
  }

  editGroupName(group: string){
    group = group.toLowerCase();
    var firstLetter = group.charAt(0).toUpperCase();

    group = firstLetter + group.substring(1);
    group = group.replace('_', ' ');
    return group;
  }

  onPasswordChangeClose(item: boolean) {
    const temp = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
    this.user = temp.value;
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
}

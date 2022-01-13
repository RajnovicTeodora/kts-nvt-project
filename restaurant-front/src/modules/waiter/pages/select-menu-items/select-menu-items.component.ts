import { BreakpointObserver } from '@angular/cdk/layout';
import {
  Component,
  OnInit,
  Output,
  ViewChild,
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
  }

  ngOnInit() {
    this.search();
    }

  search() {
    this.searchMenuItemsService.searchAndFilterItem(this.filterGroup, this.searchName).subscribe(
      {
        next: (result) => {
          this.menuItems = result;
          console.log(this.menuItems)
        },
        error: data => {
          this.toastr.error(data.error);       
        }
      }
    );
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

  onPasswordChangeClose(item: boolean) {
    const temp = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
    this.user = temp.value;
  }


  onSearchClicked(input:string){
    console.log(input);
    this.searchName = input;
    this.search();

  }

  onFilterClick(group:string){

  }
}

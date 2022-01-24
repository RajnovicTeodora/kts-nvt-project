import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { Ingredient } from 'src/modules/shared/models/ingredient';
import { MenuItemWithIngredients } from 'src/modules/shared/models/menu-item-with-ingredients';
import { MenuItemWithIngredientsService } from 'src/modules/shared/services/menu-item-with-ingredients-service/menu-item-with-ingredients.service';

@Component({
  selector: 'app-customize-ordered-item',
  templateUrl: './customize-ordered-item.component.html',
  styleUrls: ['./customize-ordered-item.component.scss'],
})
export class CustomizeOrderedItemComponent implements OnInit {
  @Input() menuItemId = -1;
  @Output() onCustomizeOrderedItemClose = new EventEmitter();
  priorities: number[] = [1, 2, 3, 4, 5];
  menuItemWithIngredients: MenuItemWithIngredients;
  ingList: Array<Ingredient>;
  i: Ingredient;

  constructor(
    private menuItemService: MenuItemWithIngredientsService,
    public router: Router,
    private toastr: ToastrService
  ) {
    this.menuItemWithIngredients = new MenuItemWithIngredients(
      0,
      0,
      '',
      '',
      new Array()
    );
    this.ingList = new Array;
  }

  ngOnInit(): void {
    this.getMenuItemWithIngredients();
  }

  getMenuItemWithIngredients() {
      this.menuItemService.getMenuItem(this.menuItemId).subscribe({
        next: (result) => {
          
          this.menuItemWithIngredients = new MenuItemWithIngredients(
            result.id,
            result.price,
            result.name,
            result.type,
            result.ingredients
          );
          this.menuItemWithIngredients.container = result.container;
        },
        error: (data) => {
          this.toastr.error(data.error);
        },
      });
    
  }

  cancel() {
    this.onCustomizeOrderedItemClose.emit(true);
  }

  addToOrder() {}
}

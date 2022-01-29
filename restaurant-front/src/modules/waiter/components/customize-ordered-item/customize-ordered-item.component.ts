import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import {
  MatSelectionList,
  MatSelectionListChange,
} from '@angular/material/list';
import { MatSelect } from '@angular/material/select';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Ingredient } from 'src/modules/shared/models/ingredient';
import { MenuItemWithIngredients } from 'src/modules/shared/models/menu-item-with-ingredients';
import { OrderedItem } from 'src/modules/shared/models/ordered-item';
import { MenuItemWithIngredientsService } from 'src/modules/shared/services/menu-item-with-ingredients-service/menu-item-with-ingredients.service';

@Component({
  selector: 'app-customize-ordered-item',
  templateUrl: './customize-ordered-item.component.html',
  styleUrls: ['./customize-ordered-item.component.scss'],
})
export class CustomizeOrderedItemComponent implements OnInit {
  @ViewChild(MatSelectionList) ingredients!: MatSelectionList;
  @ViewChild('quantity') quantity: ElementRef;
  @ViewChild('priority') matSelect: MatSelect;
  @Input() menuItemId = -1;
  @Input() orderedItemDetails: MenuItemWithIngredients;
  @Input() editModeMenuItemId = -1;
  @Output() onCustomizeOrderedItemClose = new EventEmitter();
  @Output() onAddToOrder = new EventEmitter();
  @Output() onEditOrderedItem = new EventEmitter();
  priorities: number[] = [1, 2, 3, 4, 5];
  menuItemWithIngredients: MenuItemWithIngredients;
  selectedPriority: number;
  currentQ: number;
  editMode: boolean;

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
    this.currentQ = 1;
  }

  ngOnInit(): void {
    if (this.menuItemId != -1) {
      this.getMenuItemWithIngredients();
      this.currentQ = 1;
      this.editMode = false;
    } else if (this.orderedItemDetails != undefined) {
      this.editMode = true;
      this.setOrderedItemDetails();
      this.selectedPriority = this.orderedItemDetails.priority;
      this.currentQ = this.orderedItemDetails.quantity;
    }
  }

  ngAfterViewInit() {
    this.matSelect.valueChange.subscribe((value) => {
      this.selectedPriority = value;
    });
  }

  setOrderedItemDetails() {
    let searchedId = -1;
    if (this.editModeMenuItemId != -1) {
      searchedId = this.editModeMenuItemId;
    } else {
      searchedId = this.orderedItemDetails.id;
    }
    this.menuItemService.getMenuItem(searchedId).subscribe({
      next: (result) => {
        this.menuItemWithIngredients = new MenuItemWithIngredients(
          this.orderedItemDetails.id,
          this.orderedItemDetails.price,
          this.orderedItemDetails.name,
          result.type,
          result.ingredients
        );
        this.menuItemWithIngredients.ingredients.forEach((value, index) => {
          let found = false;
          this.orderedItemDetails.ingredients.forEach((value1, index1) => {
            if (value.id == value1.id) {
              this.menuItemWithIngredients.ingredients[index].isAlergen = true;
              found = true;
            }
          });
          if (found == false) {
            this.menuItemWithIngredients.ingredients[index].isAlergen = false;
          }
        });
        this.menuItemWithIngredients.quantity =
          this.orderedItemDetails.quantity;
        this.menuItemWithIngredients.priority =
          this.orderedItemDetails.priority;
        this.menuItemWithIngredients.container = result.container;
      },
      error: (data) => {
        this.toastr.error(data.error);
      },
    });
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

        this.menuItemWithIngredients.ingredients.forEach((value, index) => {
          this.menuItemWithIngredients.ingredients[index].isAlergen = true;
        });
        this.menuItemWithIngredients.priority = result.priority;
        this.menuItemWithIngredients.container = result.container;
        this.selectedPriority = result.priority;
      },
      error: (data) => {
        this.toastr.error(data.error);
      },
    });
  }

  cancel() {
    this.onCustomizeOrderedItemClose.emit(true);
  }

  addToOrder() {
    let activeIngredientsArray = Array<Ingredient>();
    if (this.menuItemWithIngredients.ingredients.length != 0) {
      if (this.ingredients.selectedOptions.selected.length == 0) {
        this.toastr.error('Must have at least one ingredient.');
      } else {
        this.ingredients.selectedOptions.selected.forEach((value) => {
          activeIngredientsArray.push(value.value);
        });
      }
    }

    let newOrderedItem = new MenuItemWithIngredients(
      this.menuItemWithIngredients.id,
      this.menuItemWithIngredients.price,
      this.menuItemWithIngredients.name,
      this.menuItemWithIngredients.type,
      activeIngredientsArray
    );
    newOrderedItem.container = this.menuItemWithIngredients.container;
    newOrderedItem.priority = this.selectedPriority;
    newOrderedItem.quantity = this.quantity.nativeElement.value;
    this.onAddToOrder.emit(newOrderedItem);
  }

  saveChanges() {
    let activeIngredientsArray = Array<Ingredient>();
    if (this.menuItemWithIngredients.ingredients.length != 0) {
      if (this.ingredients.selectedOptions.selected.length == 0) {
        this.toastr.error('Must have at least one ingredient.');
      } else {
        this.ingredients.selectedOptions.selected.forEach((value) => {
          activeIngredientsArray.push(value.value);
        });
      }
    }
    if (this.editModeMenuItemId == -1) {
      let newOrderedItem = new MenuItemWithIngredients(
        this.menuItemWithIngredients.id,
        this.menuItemWithIngredients.price,
        this.menuItemWithIngredients.name,
        this.menuItemWithIngredients.type,
        activeIngredientsArray
      );
      newOrderedItem.container = this.menuItemWithIngredients.container;
      newOrderedItem.priority = this.selectedPriority;
      newOrderedItem.quantity = this.quantity.nativeElement.value;
      this.onEditOrderedItem.emit(newOrderedItem);
    } else {
      let editedOrderedItem = new OrderedItem(
        this.selectedPriority,
        this.quantity.nativeElement.value,
        this.editModeMenuItemId,
        activeIngredientsArray
      );
      editedOrderedItem.id = this.menuItemWithIngredients.id;
      editedOrderedItem.menuItemName = this.menuItemWithIngredients.name;
      editedOrderedItem.price = this.menuItemWithIngredients.price;
      editedOrderedItem.status = 'PENDING';
      this.onEditOrderedItem.emit(editedOrderedItem);
    }
  }
}

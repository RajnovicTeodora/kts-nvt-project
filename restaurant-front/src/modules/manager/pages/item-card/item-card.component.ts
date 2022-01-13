import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Dish } from 'src/modules/shared/models/dish';
import { Drink } from 'src/modules/shared/models/drink';
import { ItemService } from '../../services/item-service/item.service';
import { SafeUrl } from '@angular/platform-browser';
import { HelperService } from 'src/modules/shared/services/helper/helper.service';

@Component({
  selector: 'app-item-card',
  templateUrl: './item-card.component.html',
  styleUrls: ['./item-card.component.scss'],
})
export class ItemCardComponent implements OnInit {
  @Input()
  itemId: string;
  item: Drink | Dish;
  itemType: string;
  image: SafeUrl;

  @Output() approveClicked = new EventEmitter();
  @Output() deleteClicked = new EventEmitter();

  constructor(
    private itemService: ItemService,
    private helperService: HelperService
  ) {}

  ngOnInit() {
    this.getItem();
  }

  getItem() {
    this.itemService.getById(this.itemId).subscribe((response) => {
      this.item = response;
      if ((response as Dish).dishType) {
        this.itemType = 'Dish';
      } else {
        this.itemType = 'Drink';
      }

      this.image = this.helperService.bypassUrlSecurity(response.image);
    });
  }

  getDish(item: Dish | Drink): Dish {
    return item as Dish;
  }

  getDrink(item: Dish | Drink): Drink {
    return item as Drink;
  }

  approveItem(id: string) {
    this.approveClicked.emit(id);
  }
  deleteItem(id: string) {
    this.deleteClicked.emit(id);
  }
}

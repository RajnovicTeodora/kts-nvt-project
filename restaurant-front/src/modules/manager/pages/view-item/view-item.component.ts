import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Dish } from 'src/modules/shared/models/dish';
import { Drink } from 'src/modules/shared/models/drink';
import { ItemService } from '../../services/item-service/item.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-view-item',
  templateUrl: './view-item.component.html',
  styleUrls: ['./view-item.component.scss'],
})
export class ViewItemComponent implements OnInit {
  item: Drink | Dish;
  itemId: string;
  itemType: string;
  image: SafeUrl;

  constructor(
    private itemService: ItemService,
    private route: ActivatedRoute,
    private domSanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.itemId = this.route.snapshot.paramMap.get('id') || '';
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

      this.image = this.domSanitizer.bypassSecurityTrustResourceUrl(
        'data:image/jpg;base64,' + response.image
      );
    });
  }

  getDish(item: Dish | Drink): Dish {
    return item as Dish;
  }

  getDrink(item: Dish | Drink): Drink {
    return item as Drink;
  }
  openDeleteDialog() {}
  openApproveDialog() {}
}

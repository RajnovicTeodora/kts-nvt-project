import { Routes } from "@angular/router";
import { AddDrinkComponent } from "./pages/add-drink/add-drink.component";
import { DrinkCardComponent } from "./pages/drink-card/drink-card.component";
import { MainPageBartenderComponent } from "./pages/main-page-bartender/main-page-bartender.component";
import { OneDrinkCardComponent } from "./pages/one-drink-card/one-drink-card.component";
import { OrderViewComponent } from "./pages/order-view/order-view.component";

export const BartenderRoutes: Routes = [
    {
        path: "add-drink",
        pathMatch: "full",
        component: AddDrinkComponent,
    },
    {
      path: "order-view",
      pathMatch: "full",
      component: OrderViewComponent,
    },
    {
      path: "drink-card",
      pathMatch: "full",
      component: DrinkCardComponent,
    },
    {
      path: "one-drink",
      pathMatch: "full",
      component: OneDrinkCardComponent,
    },
    {
      path: "main-page-bt",
      pathMatch: "full",
      component: MainPageBartenderComponent,
    },
];

import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { HomepageComponent } from "./_pages/homepage/homepage.component";
import { UserVersionsComponent } from "./_pages/user-versions/user-versions.component";
import { UserDetailsComponent } from "./_pages/user-details/user-details.component";
import { MenuComponent } from "./_components/menu/menu.component";
import { UserComponent } from "./_pages/user/user.component";
import { HttpClientModule } from "@angular/common/http";
import {
  MatTabsModule,
  MatDatepicker,
  MatDatepickerModule,
  MatNativeDateModule,
  MatFormFieldModule,
  MatInputModule,
} from "@angular/material";
import { ChartsModule } from "ng2-charts";

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    UserVersionsComponent,
    UserDetailsComponent,
    MenuComponent,
    UserComponent,
  ],
  imports: [
    ChartsModule,
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    HttpClientModule,
    MatTabsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  providers: [MatDatepickerModule],
  bootstrap: [AppComponent],
})
export class AppModule {}

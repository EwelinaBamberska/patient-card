import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomepageComponent } from "./_pages/homepage/homepage.component";
import { UserDetailsComponent } from "./_pages/user-details/user-details.component";
import { UserVersionsComponent } from "./_pages/user-versions/user-versions.component";
import { UserComponent } from "./_pages/user/user.component";

const routes: Routes = [
  {
    path: "",
    component: HomepageComponent,
  },
  {
    path: "user/:id",
    component: UserComponent,
    children: [
      {
        path: "",
        // component: UserVersionsComponent, todo
        component: UserDetailsComponent,
      },
      {
        path: ":version",
        component: UserDetailsComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

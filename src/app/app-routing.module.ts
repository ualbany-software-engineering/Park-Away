import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginpageComponent } from './loginpage/loginpage.component';
import { SearchPageComponent } from './search-page/search-page.component';
import { SignUpPageComponent } from './sign-up-page/sign-up-page.component';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';

const routes: Routes = [
 
  {path: 'Login',component : LoginpageComponent},
  {path: 'signuppage',component : SignUpPageComponent},
  {path: 'Home',component : WelcomePageComponent},
  {path: 'search',component:SearchPageComponent},
  { path: '', redirectTo: 'Home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

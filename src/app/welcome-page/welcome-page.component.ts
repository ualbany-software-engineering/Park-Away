import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDetailsService } from '../user-details.service';

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent implements OnInit {

  isSearch : boolean = true;
  public serv : any;

  constructor(private router : Router,private service: UserDetailsService) { 
    this.serv = service;
  }

  ngOnInit(): void {
console.log(this.serv.fromsignup);
  }
  clickLogin(value : boolean){
    this.isSearch = value;

  }
}

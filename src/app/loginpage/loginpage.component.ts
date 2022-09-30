import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-loginpage',
  templateUrl: './loginpage.component.html',
  styleUrls: ['./loginpage.component.css']
})

export class LoginpageComponent implements OnInit {

  type : string ="password";
  isText : boolean = false;
  eyeIcon :string = "fa-eye-slash";
  loginForm!:FormGroup;
  //constructor(private router : Router) { }
  constructor(private fb:FormBuilder) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      Emailaddress: ['',Validators.required],
      password:['', Validators.required]
    })
  }

  public hideShowPass(){
this.isText = !this.isText;
this.isText ? this.eyeIcon = "fa-eye" : this.eyeIcon = "fa-sye-slash";
this.isText ? this.type = "text" : this.type = "password";

  }
}

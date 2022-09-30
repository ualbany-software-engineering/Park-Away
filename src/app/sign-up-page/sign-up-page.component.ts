import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { UserDetailsService } from '../user-details.service';

@Component({
  selector: 'app-sign-up-page',
  templateUrl: './sign-up-page.component.html',
  styleUrls: ['./sign-up-page.component.css']
})
export class SignUpPageComponent implements OnInit {

  //constructor() { }
  type : string ="password";
  isText : boolean = false;
  eyeIcon :string = "fa-eye-slash";
  signUpForm!: FormGroup;
  constructor(private fb: FormBuilder,private service : UserDetailsService,
    private router : Router){}

  ngOnInit(): void {
    this.signUpForm= this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      phone: ['', Validators.required],
      })
  };
  public hideShowPass(){
    this.isText = !this.isText;
    this.isText ? this.eyeIcon = "fa-eye" : this.eyeIcon = "fa-sye-slash";
    this.isText ? this.type = "text" : this.type = "password";
  }
  
  onSignup(){
    if(this.signUpForm.valid){
      //perform logic for signup
      var obj ={
    
      }
      console.log(this.signUpForm.value)
    }
    else{
      //logic for throwing error
    }
    }
    Register(formGroup : FormGroup){
      debugger
      var obj ={
        "firstName" : formGroup.get('firstName')?.value,
        "lastName" : formGroup.get('lastName')?.value,
        "email" : formGroup.get('email')?.value,
        "password" : formGroup.get('password')?.value,
        "phone" : formGroup.get('phone')?.value
       }
       this.service.fromsignup = true;
       this.service.post(obj).subscribe(o =>{
        console.log("success");
        this.service.firstname = formGroup.get('firstName')?.value;
        this.router.navigate(['/search']);
        
       })
    }
 }


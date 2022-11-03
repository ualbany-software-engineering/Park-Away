import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { AlertService } from "../../services/alert/alert.service";
import { environment } from "../../../environments/environment";
import { AuthService } from "../../services/auth/auth.service";

@Component({
  selector: "app-loginpage",
  templateUrl: "./loginpage.component.html",
  styleUrls: ["./loginpage.component.css"],
})
export class LoginpageComponent implements OnInit {
  type: string = "password";
  isText: boolean = false;
  eyeIcon: string = "fa-eye-slash";
  loginForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      emailaddress: ["", Validators.required],
      password: ["", Validators.required],
    });
  }

  hideShowPass() {
    this.isText = !this.isText;
    this.isText ? (this.eyeIcon = "fa-eye") : (this.eyeIcon = "fa-sye-slash");
    this.isText ? (this.type = "text") : (this.type = "password");
  }

  login() {
    this.authService
      .login(this.loginForm.value.emailaddress, this.loginForm.value.password)
      .subscribe(
        (data: any) => {
          const urlBeforeLogin = localStorage.getItem("urlBeforeLogin");
          localStorage.setItem("user", JSON.stringify(data));
          if (urlBeforeLogin) {
            localStorage.removeItem("urlBeforeLogin");
            this.router.navigate([urlBeforeLogin]);
          } else if (data.email === environment.adminMail) {
            this.router.navigate(["/admin"]);
          } else {
            this.router.navigate(["/search"]);
          }
        },
        (error) => {
          this.alertService.updateStatus({
            success: false,
            error: true,
            message: "Bad Credentials!! Unable to login",
          });
        }
      );
  }
}

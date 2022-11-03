import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { AlertService } from "../../services/alert/alert.service";
import { UserDetailsService } from "../../services/user-details.service";

@Component({
  selector: "app-sign-up-page",
  templateUrl: "./sign-up-page.component.html",
  styleUrls: ["./sign-up-page.component.css"],
})
export class SignUpPageComponent implements OnInit {
  type: string = "password";
  isText: boolean = false;
  eyeIcon: string = "fa-eye-slash";
  signUpForm!: FormGroup;
  passwordMatch = false;
  isFormSubmitted = false;

  constructor(
    private fb: FormBuilder,
    private userDetailsService: UserDetailsService,
    private router: Router,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.signUpForm = this.fb.group({
      firstName: ["", Validators.required],
      lastName: ["", Validators.required],
      email: [
        "",
        [
          Validators.required,
          Validators.pattern("[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}"),
        ],
      ],
      password: ["", Validators.required],
      confirmPassword: ["", Validators.required],
      phone: ["", Validators.required],
    });
  }

  hideShowPass() {
    this.isText = !this.isText;
    this.isText ? (this.eyeIcon = "fa-eye") : (this.eyeIcon = "fa-sye-slash");
    this.isText ? (this.type = "text") : (this.type = "password");
  }

  register() {
    this.isFormSubmitted = true;
    if (!this.signUpForm.valid) {
      this.signUpForm.markAllAsTouched();
      return;
    }

    if (
      this.signUpForm.value.password != this.signUpForm.value.confirmPassword
    ) {
      this.passwordMatch = false;
      return;
    } else {
      this.passwordMatch = true;
    }

    this.userDetailsService.register(this.signUpForm.value).subscribe(
      (data) => {
        console.log(JSON.stringify(data));
        this.router.navigate(["/login"]);
      },
      (err) => {
        this.alertService.updateStatus({
          success: false,
          error: true,
          message: "Unable to register, please try after sometime",
        });
      }
    );
  }

  get passwordMatchError() {
    return (
      this.signUpForm.getError("mismatch") &&
      this.signUpForm.get("confirmPassword")?.touched
    );
  }
}

import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { UserService } from "../../services/user/user.service";
import { AuthService } from "../../services/auth/auth.service";
import * as moment from "moment";
import { HelperService } from "../../services/helper/helper.service";

@Component({
  selector: "app-booking-page",
  templateUrl: "./booking-page.component.html",
  styleUrls: ["./booking-page.component.css"],
})
export class BookingPageComponent implements OnInit {
  bookingId = "";
  isUserLoggedIn: boolean | undefined;
  userDetail: any;
  profileData: any;
  searchPayload: any;
  selectedParking: any;
  locationName = "";
  totalCharges = 0;

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private helperService: HelperService
  ) {}

  ngOnInit(): void {
    this.searchPayload = this.helperService.searchPayload;
    this.selectedParking = this.helperService.selectedParking;
    this.locationName = this.helperService.locationName;

    if (!this.searchPayload || !this.selectedParking) {
      this.router.navigate(["/search"]);
    }

    this.authService.userLoggedinStatus();
    this.authService.getUserLoginStatus$.subscribe((loginStatus) => {
      this.isUserLoggedIn = loginStatus;
    });

    if (this.isUserLoggedIn) {
      this.userService.getProfile().subscribe((profileData) => {
        this.profileData = profileData;
      });
    }

    this.getBookingHours();
  }

  redirectToLogin() {
    localStorage.setItem("urlBeforeLogin", this.router.url);
    this.router.navigate(["/login"]);
  }

  getBookingHours() {
    const now = moment(this.searchPayload.startTime);
    const end = moment(this.searchPayload.endTime);
    const duration = moment.duration(end.diff(now));
    const days = duration.asHours();
    this.totalCharges = days * parseInt(this.selectedParking.hourlyPrice);
  }

  onBookNowClick() {
    if (!this.isUserLoggedIn) {
      this.redirectToLogin();
    } else {
      const booking = {
        parkingId: this.selectedParking.parkingId,
        startTime: this.searchPayload.startTime,
        endTime: this.searchPayload.endTime,
      };
      this.userService.saveBooking(booking).subscribe((data) => {
        this.router.navigate(["/mybooking"]);
      });
    }
  }
}

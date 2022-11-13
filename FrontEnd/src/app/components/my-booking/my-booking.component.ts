import { Component, OnInit } from "@angular/core";
import { UserService } from "../../services/user/user.service";

@Component({
  selector: "app-my-booking",
  templateUrl: "./my-booking.component.html",
  styleUrls: ["./my-booking.component.css"],
})
export class MyBookingComponent implements OnInit {
  bookingData: any;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.getProfile();
  }

  getProfile() {
    this.userService.getBookings().subscribe((bookingData) => {
      this.bookingData = bookingData;
    });
  }
}

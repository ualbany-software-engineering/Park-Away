import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: "root",
})
export class UserService {
  constructor(private http: HttpClient) {}

  getProfile() {
    return this.http.get("user/profile");
  }

  getBookings() {
    return this.http.get("user/booking?page=0&size=100");
  }

  saveBooking(payload: any) {
    return this.http.post("user/booking", payload);
  }
}

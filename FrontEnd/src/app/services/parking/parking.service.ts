import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class ParkingService {
  constructor(private http: HttpClient) {}

  searchParking(data: any) {
    return this.http.post("parking/search?page=0&size=1000", data);
  }

  getParkingByAirport() {
    return this.http.get("parking/airport?page=0&size=1000");
  }

  getParkingByCruise() {
    return this.http.get("parking/cruise?page=0&size=1000");
  }

  getParkingByEvent() {
    return this.http.get("parking/event?page=0&size=1000");
  }
}

import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ParkingService } from "../../services/parking/parking.service";
import { AdminService } from "../../services/admin/admin.service";
import { AuthService } from "../../services/auth/auth.service";
import { NgbDateStruct } from "@ng-bootstrap/ng-bootstrap";
import { HelperService } from "src/app/services/helper/helper.service";
import { Router } from "@angular/router";
import * as moment from "moment";

@Component({
  selector: "app-search-page",
  templateUrl: "./search-page.component.html",
  styleUrls: ["./search-page.component.css"],
})
export class SearchPageComponent implements OnInit {
  locationData: any;
  searchForm: FormGroup;
  locationName: string = "";
  noRecordFound = false;
  lat:any;
  long:any;
  noData = false;
  showData = false;
  specialParkingData : any;


  preferredTime = [
    "00:00:00",
    "01:00:00",
    "02:00:00",
    "03:00:00",
    "04:00:00",
    "05:00:00",
    "06:00:00",
    "07:00:00",
    "08:00:00",
    "09:00:00",
    "10:00:00",
    "11:00:00",
    "12:00:00",
    "13:00:00",
    "14:00:00",
    "15:00:00",
    "16:00:00",
    "17:00:00",
    "18:00:00",
    "19:00:00",
    "20:00:00",
    "21:00:00",
    "22:00:00",
    "23:00:00",
  ];

  inTime = this.preferredTime;
  outTime = this.preferredTime;

  inMinDate: NgbDateStruct = {} as NgbDateStruct;
  outMinDate: NgbDateStruct = {} as NgbDateStruct;

  constructor(
    private adminService: AdminService,
    private fb: FormBuilder,
    private parkingService: ParkingService,
    private authService: AuthService,
    private helperService: HelperService,
    private router: Router
  ) {
    this.searchForm = this.fb.group({
      locationId: ["", Validators.required],
      startTime: ["", Validators.required],
      endTime: ["", Validators.required],
      parkingStartTime: ["", Validators.required],
      parkingEndTime: ["", Validators.required],
    });
  }

  ngOnInit(): void {
    this.authService.userLoggedinStatus();
    this.getLocation();
    this.adminService.getLocation().subscribe((locationData) => {
      this.locationData = locationData;
    });

    this.searchForm.get("locationId")?.valueChanges.subscribe((locationId) => {
      this.locationName = this.locationData.data.filter(
        (loc: any) => loc.locationId == locationId
      )[0].locationName;
    });

    const current = new Date();
    this.inMinDate = {
      year: current.getFullYear(),
      month: current.getMonth() + 1,
      day: current.getDate(),
    };

    this.searchForm.get("startTime")?.valueChanges.subscribe((data) => {
      this.outMinDate = data;
    });

    this.searchForm.get("endTime")?.valueChanges.subscribe((data) => {
      if (this.compareDate(this.searchForm.value.startTime, data)) {
        const startTime = parseInt(
          this.searchForm.value.parkingStartTime.split(":")[0]
        );
        this.outTime = [];
        for (let i = startTime + 1; i < 24; i++) {
          if(i<=9)
          this.outTime.push("0"+i.toString() + ":00:00");
          else
          this.outTime.push(i.toString() + ":00:00");
        }
      }
    });
  }

  compareDate(date1: any, date2: any) {
    const startTime = moment(date1);
    const endTime = moment(date2);
    const duration = moment.duration(endTime.diff(startTime));
    const days = duration.asDays();
    return days == 0 ? true : false;
  }

  searchParking() {
    if (!this.searchForm.valid) {
      this.searchForm.markAllAsTouched();
      return;
    }

    const formField = {
      locationId: this.searchForm.value.locationId,
      startTime: this.formatDate(
        this.searchForm.value.startTime,
        this.searchForm.value.parkingStartTime
      ),
      endTime: this.formatDate(
        this.searchForm.value.endTime,
        this.searchForm.value.parkingEndTime
      ),
      latitude: this.lat,
      longitude: this.long,
    };

    this.parkingService
      .searchParking(formField)
      .subscribe((parkingData: any) => {
        if (parkingData.data.length > 0) {
          this.helperService.searchResult = parkingData.data;
          this.helperService.locationName = this.locationName;
          this.helperService.searchPayload = formField;
          this.router.navigate(["/parkings"]);
        } else {
          this.noRecordFound = true;
        }
      });
  }

  formatDate(date: any, time: string) {
    return (
      date.year +
      "-" +
      date.month +
      "-" +
      (date.day < 10 ? "0" + date.day : date.day) +
      "T" +
      time
    );
  }

  isParkingStartTimedisabled() {
    if (this.searchForm.value.startTime) return false;
    return true;
  }

  isParkingEndDateDisabled() {
    if (
      this.searchForm.value.startTime &&
      this.searchForm.value.parkingStartTime
    )
      return false;
    return true;
  }

  isParkingEndTimeDisabled() {
    if (
      this.searchForm.value.startTime &&
      this.searchForm.value.parkingStartTime &&
      this.searchForm.value.endTime
    )
      return false;
    return true;
  }
  getLocation() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position: GeolocationPosition) => {
        if (position) {
          console.log("Latitude: " + position.coords.latitude +
            "Longitude: " + position.coords.longitude);
          this.lat = position.coords.latitude;
          this.long = position.coords.longitude;
          console.log(this.lat);
          console.log(this.long);
        }
      },
        (error: GeolocationPositionError) => console.log(error));
    } else {
      alert("Geolocation is not supported by this browser.");
    }
  }
  airportParking() {
    //console.log("Checked");
    this.parkingService
      .getParkingByAirport()
      .subscribe((parkingData: any) => {
        if (parkingData.data.length > 0) {
          this.showData=true;
          this.specialParkingData=parkingData.data;
        } else {
          this.noData = true;
        }
      });
  }

  cruiseParking() {
    //console.log("Checked");
    this.parkingService
      .getParkingByCruise()
      .subscribe((parkingData: any) => {
        if (parkingData.data.length > 0) {
          this.showData=true;
          this.specialParkingData=parkingData.data;
        } else {
          this.noData = true;
        }
      });
  }

  eventParking() {
    //console.log("Checked");
    this.parkingService
      .getParkingByEvent()
      .subscribe((parkingData: any) => {
        if (parkingData.data.length > 0) {
          this.showData=true;
          this.specialParkingData=parkingData.data;
        } else {
          this.noData = true;
        }
      });
  }
}

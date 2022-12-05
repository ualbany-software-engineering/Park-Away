import { Component, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  UntypedFormControl,
  Validators,
} from "@angular/forms";
import { AdminService } from "../../services/admin/admin.service";
import { DeleteButtonComponent } from "./delete-button.component";
import { EditButtonComponent } from "./edit-button.component";

@Component({
  selector: "app-admin",
  templateUrl: "./admin.component.html",
  styleUrls: ["./admin.component.css"],
})
export class AdminComponent implements OnInit {
  selectedItem: string = "Facility";

  adminItems = ["Facility", "Location", "Parking"];

  facilityForm: FormGroup;
  locationForm: FormGroup;
  parkingForm: FormGroup;
  facilityParkingForm: FormGroup;

  facilityData: any;
  locationData: any;
  parkingData: any;
  facilityParkingData: any;

  frameworkComponents: any;

  columnDefs: any;

  isEditEnabled = false;

  selectedParking: any;

  facilityColumnDef = [
    { field: "facilityId", width: 300 },
    { field: "facilityName", width: 570 },
  ];
  locationColumnDef = [
    { field: "locationId", width: 300 },
    { field: "locationName", width: 570 },
  ];
  parkingColumnDef = [
    { field: "parkingId", width: 100 },
    { field: "parkingName" },
    { field: "hourlyPrice", width: 130 },
    { field: "longitude", width: 130 },
    { field: "latitude", width: 130 },
    { field: "category", width: 130 },
    { field: "capacity", width: 100 },
    { field: "imageLink", width: 150 },
    { field: "facility", width: 180 },
    { field: "location.locationName", headerName: "Location Name" },
  ];

  parkingFacilityCoulmnDef = [
    { field: "facilityId", width: 300 },
    { field: "facilityName", width: 470 },
    {
      field: "Delete",
      cellRenderer: "deleteButton",
      cellRendererParams: {
        onClick: this.onDeleteButtonClick.bind(this),
        label: "Delete",
      },
      width: 300,
    },
  ];

  arrayFormatter(params: any) {
    return params.value.join(", ");
  }

  constructor(private fb: FormBuilder, private adminService: AdminService) {
    this.facilityForm = this.fb.group({
      facilityName: ["", Validators.required],
      facilityId: [""],
    });

    this.locationForm = this.fb.group({
      locationId: [""],
      locationName: ["", Validators.required],
    });

    this.parkingForm = this.fb.group({
      parkingId: [""],
      parkingName: ["", Validators.required],
      hourlyPrice: ["", Validators.required],
      capacity: ["", Validators.required],
      longitude: ["", Validators.required],
      latitude: ["", Validators.required],
      category:[""],
      imageLink: ["", Validators.required],
      location: this.fb.group({
        locationId: ["", Validators.required],
        locationName: ["", Validators.required],
      }),
      availability: [""],
      facilities: this.fb.array([]),
    });

    this.facilityParkingForm = this.fb.group({
      id: [""],
      parking: this.fb.group({
        parkingId: ["", Validators.required],
      }),
      facility: this.fb.group({
        facilityId: ["", Validators.required],
      }),
    });

    this.frameworkComponents = {
      editButton: EditButtonComponent,
      deleteButton: DeleteButtonComponent,
    };
  }

  ngOnInit(): void {
    this.formatColumnDef([...this.facilityColumnDef]);
    this.getFacility();
    this.facilityParkingData = [];
    this.facilityParkingForm
      .get("parking")
      ?.get("parkingId")
      ?.valueChanges.subscribe((data) => {
        if (data) {
          this.selectedParking = data;
          this.adminService.getAllFacilities(data).subscribe((facility) => {
            this.facilityParkingData = facility;
          });
        } else {
          this.facilityParkingData = [];
        }
      });
  }

  selectItem(selectedItem: string) {
    if (this.selectedItem === selectedItem) {
      return;
    }
    this.selectedItem = selectedItem;
    switch (this.selectedItem) {
      case this.adminItems[0]: {
        this.formatColumnDef([...this.facilityColumnDef]);
        if (!this.facilityData) {
          this.getFacility();
        }
        break;
      }
      case this.adminItems[1]: {
        this.formatColumnDef([...this.locationColumnDef]);
        if (!this.locationData) {
          this.getLocation();
        }
        break;
      }
      case this.adminItems[2]: {
        this.formatColumnDef([...this.parkingColumnDef]);
        if (!this.parkingData) {
          this.getParking();
          this.getLocation();
        }
        break;
      }
      default: {
        break;
      }
    }
  }

  formatColumnDef(data: any) {
    this.columnDefs = [];
    data.push({
      field: "Edit",
      cellRenderer: "editButton",
      cellRendererParams: {
        onClick: this.onEditButtonClick.bind(this),
        label: "Edit",
      },
      width: 100,
    });
    data.push({
      field: "Delete",
      cellRenderer: "deleteButton",
      cellRendererParams: {
        onClick: this.onDeleteButtonClick.bind(this),
        label: "Delete",
      },
      width: 100,
    });

    this.columnDefs = data;
  }

  onEditButtonClick(data: any) {
    this.isEditEnabled = true;
    switch (this.selectedItem) {
      case this.adminItems[0]: {
        this.facilityForm.patchValue(data);
        break;
      }
      case this.adminItems[1]: {
        this.locationForm.patchValue(data);
        break;
      }
      case this.adminItems[2]: {
        this.parkingForm.patchValue(data);
        break;
      }
      default: {
        break;
      }
    }
  }

  onDeleteButtonClick(data: any) {
    switch (this.selectedItem) {
      case this.adminItems[0]: {
        this.deleteFacility(data.facilityId);
        break;
      }
      case this.adminItems[1]: {
        this.deleteLocation(data.locationId);
        break;
      }
      case this.adminItems[2]: {
        if (data?.parkingId) {
          this.deleteParking(data.parkingId);
        } else {
          this.deleteParkingFcility(
            data.facilityId,
            this.facilityParkingForm.value.parking.parkingId
          );
        }
        break;
      }
      default: {
        break;
      }
    }
  }

  getFacility() {
    this.adminService.getFacility().subscribe((facilityData) => {
      this.facilityData = facilityData;
    });
  }

  getLocation() {
    this.adminService.getLocation().subscribe((locationData) => {
      this.locationData = locationData;
    });
  }

  getParking() {
    this.adminService.getParking().subscribe((parkingData) => {
      this.parkingData = parkingData;
      this.parkingData.data.forEach((parking: any) => {
        let tempFacility = "";
        parking.facilities.forEach((x: any) => {
          tempFacility = tempFacility + x.facilityName + ",";
        });
        parking.facility = tempFacility;
      });
    });
  }

  submitFacility() {
    if (this.isEditEnabled) {
      this.editFacility(this.facilityForm.value);
      return;
    }
    this.adminService
      .createFacility(this.facilityForm.value)
      .subscribe((data) => {
        this.facilityForm.reset();
        this.getFacility();
        this.isEditEnabled = false;
      });
  }

  submitLocation() {
    if (this.isEditEnabled) {
      this.editLocation(this.locationForm.value);
      return;
    }
    this.adminService
      .createLocation(this.locationForm.value)
      .subscribe((data) => {
        this.locationForm.reset();
        this.getLocation();
        this.isEditEnabled = false;
      });
  }

  submitParking() {
    if (this.isEditEnabled) {
      this.editParking(this.parkingForm.value);
      return;
    }
    this.adminService
      .createParking(this.parkingForm.value)
      .subscribe((data) => {
        this.parkingForm.reset();
        this.getParking();
        this.isEditEnabled = false;
      });
  }

  editFacility(facility: any) {
    this.adminService
      .updateFacility(this.facilityForm.value)
      .subscribe((data) => {
        this.facilityForm.reset();
        this.isEditEnabled = false;
        this.getFacility();
      });
  }

  editLocation(location: any) {
    this.adminService
      .updateLocation(this.locationForm.value)
      .subscribe((data) => {
        this.locationForm.reset();
        this.isEditEnabled = false;
        this.getLocation();
      });
  }

  editParking(parking: any) {
    this.adminService
      .updateParking(this.parkingForm.value)
      .subscribe((data) => {
        this.parkingForm.reset();
        this.isEditEnabled = false;
        this.getParking();
      });
  }

  deleteFacility(facilityId: any) {
    this.adminService.deleteFacility(facilityId).subscribe((data) => {
      this.facilityData = [];
      this.getFacility();
    });
  }

  deleteLocation(locationId: any) {
    this.adminService.deleteLocation(locationId).subscribe((data) => {
      this.locationData = [];
      this.getLocation();
    });
  }

  deleteParking(parkingId: any) {
    this.adminService.deleteParking(parkingId).subscribe((data) => {
      this.parkingData = [];
      this.getParking();
    });
  }

  submitParkingFacility() {
    this.adminService
      .addFacilityToParking(
        this.facilityParkingForm.get("parking.parkingId")?.value,
        this.facilityParkingForm.value
      )
      .subscribe((data) => {
        this.facilityParkingForm.reset();
        this.getParking();
        this.adminService
          .getAllFacilities(this.selectedParking)
          .subscribe((facility) => {
            this.facilityParkingData = facility;
          });
        this.isEditEnabled = false;
      });
  }

  deleteParkingFcility(facilityId: number, parkingId: number) {
    this.adminService.deleteFacilityFromParking(parkingId,facilityId).subscribe((data) => {
      this.getParking();
      this.facilityParkingForm.reset();
    });
  }
}

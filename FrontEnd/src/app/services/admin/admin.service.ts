import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class AdminService {
  constructor(private http: HttpClient) {}

  getFacility() {
    return this.http.get("facility?page=0&size=100");
  }

  getLocation() {
    return this.http.get("location?page=0&size=100");
  }

  getParking() {
    return this.http.get("parking?page=0&size=100");
  }

  createFacility(data: any) {
    return this.http.post("facility", data);
  }

  createLocation(data: any) {
    return this.http.post("location", data);
  }

  createParking(data: any) {
    return this.http.post("parking", data);
  }

  updateFacility(data: any) {
    return this.http.put(`facility/${data.facilityId}`, data);
  }

  updateLocation(data: any) {
    return this.http.put(`location/${data.locationId}`, data);
  }

  updateParking(data: any) {
    return this.http.put(`parking/${data.parkingId}`, data);
  }

  deleteFacility(id: number) {
    return this.http.delete(`facility/${id}`);
  }

  deleteLocation(id: number) {
    return this.http.delete(`location/${id}`);
  }

  deleteParking(id: number) {
    return this.http.delete(`parking/${id}`);
  }

  addFacilityToParking(id: number, data: any) {
    return this.http.post(`parking/${id}/facility`, data);
  }

  getAllFacilities(id: number) {
    return this.http.get(`parking/${id}/facility`);
  }

  deleteFacilityFromParking(id: number,fid: number) {
    return this.http.delete(`parking/${id}/facility/${fid}`);
  }
}

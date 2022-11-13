import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class HelperService {
  searchResult: any;
  locationName: string = "";
  searchPayload: any;
  selectedParking: any;
  constructor() {}
}

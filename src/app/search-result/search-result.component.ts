import { Component, Input, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { HelperService } from "src/app/services/helper/helper.service";

@Component({
  selector: "app-search-result",
  templateUrl: "./search-result.component.html",
  styleUrls: ["./search-result.component.css"],
})
export class SearchResultComponent implements OnInit {
  locationName: string = "";
  searchResult: any;

  constructor(private helperService: HelperService, private router: Router) {}

  ngOnInit(): void {
    this.searchResult = this.helperService.searchResult;
    this.locationName = this.helperService.locationName;

    if (!this.searchResult || this.searchResult.length == 0) {
      this.router.navigate(["/search"]);
    }
  }

  onBookItNow(selectedParking: any) {
    this.helperService.selectedParking = selectedParking;
    this.router.navigate(["/booking"]);
  }
}

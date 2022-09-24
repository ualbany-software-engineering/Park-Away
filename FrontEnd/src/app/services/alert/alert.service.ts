import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class AlertService {
  private updateStatus$ = new BehaviorSubject<any>({});
  public getStatus$ = this.updateStatus$.asObservable();

  constructor() {}

  updateStatus(status: any) {
    this.updateStatus$.next(status);
  }
}

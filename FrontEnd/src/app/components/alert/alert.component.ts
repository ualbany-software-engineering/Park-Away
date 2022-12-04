import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { AlertService } from "../../services/alert/alert.service";

@Component({
  selector: "app-alert",
  templateUrl: "./alert.component.html",
  styleUrls: ["./alert.component.css"],
})
export class AlertComponent implements OnInit {
  @Output() onTimeOut = new EventEmitter<any>();
  status: any;
  constructor(private alertService: AlertService) {}

  ngOnInit(): void {
    this.alertService.getStatus$.subscribe((status) => {
      this.status = status;
      this.startTimer();
    });
  }

  startTimer() {
    setTimeout(() => {
      this.onAlertClose();
    }, 5000);
  }

  onAlertClose() {
    this.onTimeOut.emit();
  }
}

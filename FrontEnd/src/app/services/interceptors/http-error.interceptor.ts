import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpErrorResponse,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, throwError } from "rxjs";
import { catchError, tap } from "rxjs/operators";
import { AlertService } from "../alert/alert.service";

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
  status = {
    success: false,
    error: true,
    message: "",
  };
  excludeUrl = ["/login", "/signup"];
  constructor(private alertService: AlertService, private router: Router) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap((data) => {
        // this.alertService.updateStatus({
        //   success: true,
        //   error: false,
        //   message: "Success!!!",
        // });
      }),
      catchError((error: HttpErrorResponse) => {
        let errorMsg = "";
        if (error.error instanceof ErrorEvent) {
          console.log("this is client side error");
          errorMsg = `Error: ${error.error.message}`;
        } else {
          console.log("this is server side error");

          errorMsg = `Error Code: ${error.status},  Message: ${error.message}`;
          if (error.status.toString().charAt(0) == "5") {
            if (this.excludeUrl.indexOf(this.router.url) == -1) {
              this.router.navigate(["/error"]);
            }
          }

          if (this.excludeUrl.indexOf(this.router.url) == -1) {
            if (request.method == "GET") {
              this.status.message =
                "Something went wrong! Unable to fetch record.";
              this.alertService.updateStatus(this.status);
            } else if (request.method == "POST" || request.method == "PUT") {
              this.status.message =
                "Something went wrong! Unable to save record.";
              this.alertService.updateStatus(this.status);
            } else if (request.method == "DELETE") {
              this.status.message =
                "Something went wrong! Unable to delete record.";
              this.alertService.updateStatus(this.status);
            }
          }
        }
        return throwError(errorMsg);
      })
    );
  }
}

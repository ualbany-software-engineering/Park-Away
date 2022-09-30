import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {

  fromsignup : boolean = false;
  firstname : any = "";
  constructor(private http:HttpClient) { }
  private BaseUrl : string = 'http://localhost:8089/api/';
  public post(data: any) { 
    const url = this.BaseUrl + 'CreateUserDetails';
    return this.http.post(url, data); 
    } 
}

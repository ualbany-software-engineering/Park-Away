import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {

  fromsignup : boolean = false;
  firstname : any =""
  constructor(private http:HttpClient) { }

  private  createurl : string ='http://localhost:8089/api/CreateUserDetails';
  public post(data: any) { 
    return this.http.post(this.createurl, data); 
    } 
}

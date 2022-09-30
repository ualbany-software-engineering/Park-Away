import { Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

  mindate :Date = new Date();
  model : any;
  model1 : any;
  public options: string[] = ["Albany", "New York", "Troy"];
  selectedQuantity : any;
  constructor() { }

  ngOnInit(): void {
  }
  praneetha(){
    console.log("clicked");
  }
}

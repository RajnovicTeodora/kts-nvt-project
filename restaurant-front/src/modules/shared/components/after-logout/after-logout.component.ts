import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-after-logout',
  templateUrl: './after-logout.component.html',
  styleUrls: ['./after-logout.component.scss']
})
export class AfterLogoutComponent implements OnInit {

  constructor(public router: Router) { }

  ngOnInit(): void {
  }

  onLoginOpenClicked(item:boolean){
    this.router.navigate(['/login']);
  }
}

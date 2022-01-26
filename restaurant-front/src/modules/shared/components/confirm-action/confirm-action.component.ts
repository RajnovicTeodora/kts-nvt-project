import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-confirm-action',
  templateUrl: './confirm-action.component.html',
  styleUrls: ['./confirm-action.component.scss']
})
export class ConfirmActionComponent implements OnInit {
  @Input() confirmActionTitle : string;
  @Output() onConfirmActionCancelled = new EventEmitter();
  @Output() onConfirmActionConfirmed = new EventEmitter();

  constructor(
    public router: Router,
  ) {}

  ngOnInit(): void {}

  cancel() {
    this.onConfirmActionCancelled.emit(true);
  }

  confirm() {
    this.onConfirmActionConfirmed.emit(true);
  }

}

import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-additional-notes',
  templateUrl: './additional-notes.component.html',
  styleUrls: ['./additional-notes.component.scss']
})
export class AdditionalNotesComponent implements OnInit {
  @Output() onAdditionalNotesClose = new EventEmitter();
  @Output() onAdditionalNotesConfirm = new EventEmitter();
  @ViewChild('notes') notes: ElementRef;
  @Input() currentNotes: string;

  constructor(
    public router: Router,
  ) {}

  ngOnInit(): void {}

  cancel() {
    this.onAdditionalNotesClose.emit(true);
  }

  confirm() {
    this.onAdditionalNotesConfirm.emit(this.notes.nativeElement.value);
  }

}

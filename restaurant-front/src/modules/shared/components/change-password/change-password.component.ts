import { Component, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EventEmitter } from '@angular/core';
import { UserLogin } from '../../models/user-login';
import { UserService } from '../../services/user-service/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
  @Output()
  onClose: EventEmitter<boolean> = new EventEmitter();

  changePasswordForm: FormGroup ;

  hide = true; 

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private userService: UserService
  ) { 
    this.changePasswordForm = this.fb.group({
      password: [null, Validators.required],
    });

  }

  ngOnInit(): void {
  }

  confirm(){
    const currentUser = UserService.getLoggedIn();
    const loggedUser:  UserLogin = {
      username: currentUser.username,
      password: this.changePasswordForm.value.password,
    }

    this.userService.changePassword(loggedUser).subscribe(
      (result) => {
        this.toastr.success("Successful password change!");
        currentUser.setLoggedFirstTimeFalse;
        localStorage.setItem("currentUser", JSON.stringify(currentUser));
        this.changePasswordForm.reset();
        this.onClose.emit(true);
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

}

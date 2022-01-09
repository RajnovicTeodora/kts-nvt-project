import { Component, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EventEmitter } from '@angular/core';
import { UserLogin } from '../../models/user-login';
import { UserService } from '../../services/user-service/user.service';
import { UserWithToken } from '../../models/user-with-token';
import { BehaviorSubject } from 'rxjs';
import { UserList } from '../../models/user-list';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
})
export class ChangePasswordComponent implements OnInit {
  @Output() onClose = new EventEmitter();

  changePasswordForm: FormGroup;

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

  ngOnInit(): void {}

  confirm() {
    const currentUser = UserService.getLoggedIn();
    const loggedUser: UserLogin = {
      username: currentUser.username,
      password: this.changePasswordForm.value.password,
    };

    this.userService.changePassword(loggedUser).subscribe(
      (result) => {
        this.toastr.success('Successful password change!');
        const newUser = new UserWithToken(
          currentUser.token,
          currentUser.expiresIn,
          currentUser.username,
          currentUser.userType,
          false,
          this.changePasswordForm.value.password
        );
        localStorage.setItem('currentUser', JSON.stringify(newUser));
        ////TODOOOOOOOOOOOOOOOO ko sve mora da menja sifru i onda ograniciti ovo ispod
        let users = new BehaviorSubject<UserList>(
          JSON.parse(localStorage.getItem(currentUser.userType+"_LIST")!)
        );
        users.value.list.forEach((value, index) => {
          if (value.username == currentUser.username) users.value.list.splice(index, 1);
        });
        users.value.list.push(newUser);
        localStorage.setItem(currentUser.userType+"_LIST", JSON.stringify(users.value));
        ///////////////////
        this.onClose.emit(true);
        this.changePasswordForm.reset();
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }
}

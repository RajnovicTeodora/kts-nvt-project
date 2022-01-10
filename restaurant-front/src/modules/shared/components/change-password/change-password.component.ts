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
  title: string;
  showOnlyConfirmButton: boolean;

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
    const currentUser = this.userService.getLoggedIn();
    if(currentUser.loggedInFirstTime){
      this.title = "Change password for first login";
      this.showOnlyConfirmButton = true;
    }else{
      this.title = "Change password";
      this.showOnlyConfirmButton = false;
    }
  }

  ngOnInit(): void {}

  cancel(){
    this.onClose.emit(true);
  }

  confirm() {
    const currentUser = this.userService.getLoggedIn();
    const loggedUser: UserLogin = {
      username: currentUser.username,
      password: this.changePasswordForm.value.password,
    };

    if(this.showOnlyConfirmButton){
    this.userService.changePasswordFirst(loggedUser).subscribe(
      (result) => {
        this.handleResult(currentUser);
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
    }else{
      this.userService.changePassword(loggedUser).subscribe(
        (result) => {
          this.handleResult(currentUser);
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    }
  }

  handleResult(currentUser: UserWithToken){
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
    if(['WAITER', 'BARTENDER', 'CHEF', 'HEAD_CHEF'].includes(newUser.userType)){
      this.updateLocalStorageUserLIst(newUser);
    }
    this.onClose.emit(true);
    this.changePasswordForm.reset();
  }

  updateLocalStorageUserLIst(newUser: UserWithToken){
    let listName = newUser.userType+"_LIST";
    if(newUser.userType === 'HEAD_CHEF'){
      listName = 'CHEF_LIST';
    }
    let users = new BehaviorSubject<UserList>(
      JSON.parse(localStorage.getItem(listName)!)
    );
    users.value.list.forEach((value, index) => {
      if (value.username == newUser.username) users.value.list.splice(index, 1);
    });
    users.value.list.push(newUser);
    localStorage.setItem(listName, JSON.stringify(users.value));
  
  }

}


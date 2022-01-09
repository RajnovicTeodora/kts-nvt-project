import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { UserLogin } from 'src/modules/shared/models/user-login';
import { AuthenticationService } from 'src/modules/auth/services/authentication-service/authentication.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  loginForm: FormGroup ;

  hide = true; 

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private authService: AuthenticationService
  ) { 
    this.loginForm = this.fb.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });

  }

  ngOnInit() {  }

  login(){       
    if(this.loginForm.value.username === null || this.loginForm.value.password === null){
      this.toastr.error("All fields must be filled in!");
    }else{
      const loggedUser:  UserLogin = {
        username: this.loginForm.value.username,
        password: this.loginForm.value.password,
      }

      this.authService.login(loggedUser).subscribe(
        (result) => {
          this.toastr.success("Successful login!");
          localStorage.setItem("currentUser", JSON.stringify(result));
          this.loginForm.reset();
          if(result.userType === "ADMIN") {
            this.router.navigate(['/admin-dashboard']);
          }else if(result.userType === "MANAGER") {
            this.router.navigate(['/manager-dashboard']);
          }else if(result.userType === "HEAD_CHEF") {
            this.router.navigate(['/headChef-dashboard']);
          }else if(result.userType === "CHEF") {
            this.router.navigate(['/chef-dashboard']);
          }else if(result.userType === "BARTENDER") {
            this.router.navigate(['/bartender-dashboard']);
          }else if(result.userType === "WAITER") {
            this.router.navigate(['/waiter-dashboard']);
          }
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
      
    }
  }

}

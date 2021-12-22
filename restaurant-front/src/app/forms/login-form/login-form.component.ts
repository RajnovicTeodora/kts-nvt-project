import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UserLogin } from 'src/app/model/user-login';
import { AuthenticationService } from 'src/app/services/authentication-service/authentication.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  loggedUser: UserLogin = new UserLogin('','');

  loginForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.email, Validators.required ]),
    password: new FormControl('', [Validators.required, Validators.min(3) ])
  });

  hide = true; 

  constructor(
    public router: Router,
    private toastr: ToastrService,
    private authService: AuthenticationService
  ) {  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'password': new FormControl(null, [Validators.required, Validators.minLength(8)])
    });
  }

  login(){
    if(this.loginForm.get('email')?.value != null && this.loginForm.get('password')?.value !== null){           

      this.loggedUser = new UserLogin(
        this.loginForm.value.email,
        this.loginForm.value.password
      )
      
      this.authService.login(this.loggedUser).subscribe(
        {
          next: data => {
            this.loginForm.reset();
            if(data.userType === "ADMIN") {
              this.router.navigate(['/admin-dashboard']);
            }else if(data.userType === "MANAGER") {
              this.router.navigate(['/manager-dashboard']);
            }else if(data.userType === "HEAD_CHEF") {
              this.router.navigate(['/headChef-dashboard']);
            }else if(data.userType === "CHEF") {
              this.router.navigate(['/chef-dashboard']);
            }else if(data.userType === "BARTENDER") {
              this.router.navigate(['/bartender-dashboard']);
            }else if(data.userType === "WAITER") {
              this.router.navigate(['/waiter-dashboard']);
            }
            else {
              this.router.navigate(['/login']);
            }
          },
          error: data => {
            if (data.error && typeof data.error === "string") 
              this.toastr.error(data.error);
            else
              this.toastr.error("An error occured while logging in!");
          }
        }
      );
    }


  }

}

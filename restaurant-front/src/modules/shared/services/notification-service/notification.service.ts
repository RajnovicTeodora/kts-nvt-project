import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Notification } from '../../models/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private headers = new HttpHeaders({ 'Content-Type': 'text' });

  constructor(private http: HttpClient) { }

  getActiveNotificationsForEmployee(username: string): Observable<Array<Notification>> {
    return this.http.get<Array<Notification>>(
      `${environment.baseUrl}/${environment.notification}/getActiveNotificationsForEmployee/${username}`
    );
  }

  getAllNotificationsForEmployee(username: string): Observable<Array<Notification>> {
    return this.http.get<Array<Notification>>(
      `${environment.baseUrl}/${environment.notification}/getAllNotificationsForEmployee/${username}`
    );
  }

  setNotificationInactive(notificationId: number): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.notification}/setNotificationInactive/${notificationId}`, {
        responseType : "text"
      }
    );
  }
}

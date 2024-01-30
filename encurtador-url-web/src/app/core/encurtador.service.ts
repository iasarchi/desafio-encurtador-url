import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EncurtadorService {
  constructor(private http: HttpClient) {
  }

  shortUrl(url: string, customAlias?: string): Observable<any> {
    const body = {
      url,
      customAlias
    }
    return this.http.post("http://localhost:8080/shortener", body).pipe(
      catchError(err => {
        window.alert(err.error.description)
        return throwError(() => new Error(err.error.description))
      })
    )

  }

  getTopTenUrl(): Observable<any> {
    return this.http.get("http://localhost:8080/shortener/topten")
  }
}

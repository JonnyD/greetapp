import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGreet } from 'app/shared/model/greet.model';

type EntityResponseType = HttpResponse<IGreet>;
type EntityArrayResponseType = HttpResponse<IGreet[]>;

@Injectable({ providedIn: 'root' })
export class GreetService {
    private resourceUrl = SERVER_API_URL + 'api/greets';

    constructor(private http: HttpClient) {}

    create(greet: IGreet): Observable<EntityResponseType> {
        return this.http.post<IGreet>(this.resourceUrl, greet, { observe: 'response' });
    }

    update(greet: IGreet): Observable<EntityResponseType> {
        return this.http.put<IGreet>(this.resourceUrl, greet, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGreet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGreet[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

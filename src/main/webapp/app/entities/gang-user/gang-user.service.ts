import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGangUser } from 'app/shared/model/gang-user.model';

type EntityResponseType = HttpResponse<IGangUser>;
type EntityArrayResponseType = HttpResponse<IGangUser[]>;

@Injectable({ providedIn: 'root' })
export class GangUserService {
    private resourceUrl = SERVER_API_URL + 'api/gang-users';

    constructor(private http: HttpClient) {}

    create(gangUser: IGangUser): Observable<EntityResponseType> {
        return this.http.post<IGangUser>(this.resourceUrl, gangUser, { observe: 'response' });
    }

    update(gangUser: IGangUser): Observable<EntityResponseType> {
        return this.http.put<IGangUser>(this.resourceUrl, gangUser, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGangUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGangUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

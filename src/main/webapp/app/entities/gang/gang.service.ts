import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGang } from 'app/shared/model/gang.model';

type EntityResponseType = HttpResponse<IGang>;
type EntityArrayResponseType = HttpResponse<IGang[]>;

@Injectable({ providedIn: 'root' })
export class GangService {
    private resourceUrl = SERVER_API_URL + 'api/gangs';

    constructor(private http: HttpClient) {}

    create(gang: IGang): Observable<EntityResponseType> {
        return this.http.post<IGang>(this.resourceUrl, gang, { observe: 'response' });
    }

    update(gang: IGang): Observable<EntityResponseType> {
        return this.http.put<IGang>(this.resourceUrl, gang, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGang>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGang[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

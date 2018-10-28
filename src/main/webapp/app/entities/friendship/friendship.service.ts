import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFriendship } from 'app/shared/model/friendship.model';

type EntityResponseType = HttpResponse<IFriendship>;
type EntityArrayResponseType = HttpResponse<IFriendship[]>;

@Injectable({ providedIn: 'root' })
export class FriendshipService {
    private resourceUrl = SERVER_API_URL + 'api/friendships';

    constructor(private http: HttpClient) {}

    create(friendship: IFriendship): Observable<EntityResponseType> {
        return this.http.post<IFriendship>(this.resourceUrl, friendship, { observe: 'response' });
    }

    update(friendship: IFriendship): Observable<EntityResponseType> {
        return this.http.put<IFriendship>(this.resourceUrl, friendship, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFriendship>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFriendship[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGreetInvitation } from 'app/shared/model/greet-invitation.model';

type EntityResponseType = HttpResponse<IGreetInvitation>;
type EntityArrayResponseType = HttpResponse<IGreetInvitation[]>;

@Injectable({ providedIn: 'root' })
export class GreetInvitationService {
    private resourceUrl = SERVER_API_URL + 'api/greet-invitations';

    constructor(private http: HttpClient) {}

    create(greetInvitation: IGreetInvitation): Observable<EntityResponseType> {
        return this.http.post<IGreetInvitation>(this.resourceUrl, greetInvitation, { observe: 'response' });
    }

    update(greetInvitation: IGreetInvitation): Observable<EntityResponseType> {
        return this.http.put<IGreetInvitation>(this.resourceUrl, greetInvitation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGreetInvitation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGreetInvitation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

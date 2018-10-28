import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { RSVP } from 'app/shared/model/rsvp.model';
import { RSVPService } from './rsvp.service';
import { RSVPComponent } from './rsvp.component';
import { RSVPDetailComponent } from './rsvp-detail.component';
import { RSVPUpdateComponent } from './rsvp-update.component';
import { RSVPDeletePopupComponent } from './rsvp-delete-dialog.component';
import { IRSVP } from 'app/shared/model/rsvp.model';

@Injectable({ providedIn: 'root' })
export class RSVPResolve implements Resolve<IRSVP> {
    constructor(private service: RSVPService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((rSVP: HttpResponse<RSVP>) => rSVP.body);
        }
        return Observable.of(new RSVP());
    }
}

export const rSVPRoute: Routes = [
    {
        path: 'rsvp',
        component: RSVPComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.rSVP.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rsvp/:id/view',
        component: RSVPDetailComponent,
        resolve: {
            rSVP: RSVPResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.rSVP.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rsvp/new',
        component: RSVPUpdateComponent,
        resolve: {
            rSVP: RSVPResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.rSVP.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rsvp/:id/edit',
        component: RSVPUpdateComponent,
        resolve: {
            rSVP: RSVPResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.rSVP.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rSVPPopupRoute: Routes = [
    {
        path: 'rsvp/:id/delete',
        component: RSVPDeletePopupComponent,
        resolve: {
            rSVP: RSVPResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.rSVP.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

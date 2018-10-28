import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Gang } from 'app/shared/model/gang.model';
import { GangService } from './gang.service';
import { GangComponent } from './gang.component';
import { GangDetailComponent } from './gang-detail.component';
import { GangUpdateComponent } from './gang-update.component';
import { GangDeletePopupComponent } from './gang-delete-dialog.component';
import { IGang } from 'app/shared/model/gang.model';

@Injectable({ providedIn: 'root' })
export class GangResolve implements Resolve<IGang> {
    constructor(private service: GangService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((gang: HttpResponse<Gang>) => gang.body);
        }
        return Observable.of(new Gang());
    }
}

export const gangRoute: Routes = [
    {
        path: 'gang',
        component: GangComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.gang.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gang/:id/view',
        component: GangDetailComponent,
        resolve: {
            gang: GangResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.gang.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gang/new',
        component: GangUpdateComponent,
        resolve: {
            gang: GangResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.gang.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gang/:id/edit',
        component: GangUpdateComponent,
        resolve: {
            gang: GangResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.gang.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gangPopupRoute: Routes = [
    {
        path: 'gang/:id/delete',
        component: GangDeletePopupComponent,
        resolve: {
            gang: GangResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.gang.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

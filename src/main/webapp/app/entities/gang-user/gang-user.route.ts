import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { GangUser } from 'app/shared/model/gang-user.model';
import { GangUserService } from './gang-user.service';
import { GangUserComponent } from './gang-user.component';
import { GangUserDetailComponent } from './gang-user-detail.component';
import { GangUserUpdateComponent } from './gang-user-update.component';
import { GangUserDeletePopupComponent } from './gang-user-delete-dialog.component';
import { IGangUser } from 'app/shared/model/gang-user.model';

@Injectable({ providedIn: 'root' })
export class GangUserResolve implements Resolve<IGangUser> {
    constructor(private service: GangUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((gangUser: HttpResponse<GangUser>) => gangUser.body);
        }
        return Observable.of(new GangUser());
    }
}

export const gangUserRoute: Routes = [
    {
        path: 'gang-user',
        component: GangUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.gangUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gang-user/:id/view',
        component: GangUserDetailComponent,
        resolve: {
            gangUser: GangUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.gangUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gang-user/new',
        component: GangUserUpdateComponent,
        resolve: {
            gangUser: GangUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.gangUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gang-user/:id/edit',
        component: GangUserUpdateComponent,
        resolve: {
            gangUser: GangUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.gangUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gangUserPopupRoute: Routes = [
    {
        path: 'gang-user/:id/delete',
        component: GangUserDeletePopupComponent,
        resolve: {
            gangUser: GangUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.gangUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Greet } from 'app/shared/model/greet.model';
import { GreetService } from './greet.service';
import { GreetComponent } from './greet.component';
import { GreetDetailComponent } from './greet-detail.component';
import { GreetUpdateComponent } from './greet-update.component';
import { GreetDeletePopupComponent } from './greet-delete-dialog.component';
import { IGreet } from 'app/shared/model/greet.model';

@Injectable({ providedIn: 'root' })
export class GreetResolve implements Resolve<IGreet> {
    constructor(private service: GreetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((greet: HttpResponse<Greet>) => greet.body);
        }
        return Observable.of(new Greet());
    }
}

export const greetRoute: Routes = [
    {
        path: 'greet',
        component: GreetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.greet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'greet/:id/view',
        component: GreetDetailComponent,
        resolve: {
            greet: GreetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.greet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'greet/new',
        component: GreetUpdateComponent,
        resolve: {
            greet: GreetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.greet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'greet/:id/edit',
        component: GreetUpdateComponent,
        resolve: {
            greet: GreetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.greet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const greetPopupRoute: Routes = [
    {
        path: 'greet/:id/delete',
        component: GreetDeletePopupComponent,
        resolve: {
            greet: GreetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.greet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

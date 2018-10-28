import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Friendship } from 'app/shared/model/friendship.model';
import { FriendshipService } from './friendship.service';
import { FriendshipComponent } from './friendship.component';
import { FriendshipDetailComponent } from './friendship-detail.component';
import { FriendshipUpdateComponent } from './friendship-update.component';
import { FriendshipDeletePopupComponent } from './friendship-delete-dialog.component';
import { IFriendship } from 'app/shared/model/friendship.model';

@Injectable({ providedIn: 'root' })
export class FriendshipResolve implements Resolve<IFriendship> {
    constructor(private service: FriendshipService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((friendship: HttpResponse<Friendship>) => friendship.body);
        }
        return Observable.of(new Friendship());
    }
}

export const friendshipRoute: Routes = [
    {
        path: 'friendship',
        component: FriendshipComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.friendship.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'friendship/:id/view',
        component: FriendshipDetailComponent,
        resolve: {
            friendship: FriendshipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.friendship.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'friendship/new',
        component: FriendshipUpdateComponent,
        resolve: {
            friendship: FriendshipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.friendship.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'friendship/:id/edit',
        component: FriendshipUpdateComponent,
        resolve: {
            friendship: FriendshipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.friendship.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const friendshipPopupRoute: Routes = [
    {
        path: 'friendship/:id/delete',
        component: FriendshipDeletePopupComponent,
        resolve: {
            friendship: FriendshipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.friendship.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

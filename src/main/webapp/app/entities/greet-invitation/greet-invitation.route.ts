import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { GreetInvitation } from 'app/shared/model/greet-invitation.model';
import { GreetInvitationService } from './greet-invitation.service';
import { GreetInvitationComponent } from './greet-invitation.component';
import { GreetInvitationDetailComponent } from './greet-invitation-detail.component';
import { GreetInvitationUpdateComponent } from './greet-invitation-update.component';
import { GreetInvitationDeletePopupComponent } from './greet-invitation-delete-dialog.component';
import { IGreetInvitation } from 'app/shared/model/greet-invitation.model';

@Injectable({ providedIn: 'root' })
export class GreetInvitationResolve implements Resolve<IGreetInvitation> {
    constructor(private service: GreetInvitationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((greetInvitation: HttpResponse<GreetInvitation>) => greetInvitation.body);
        }
        return Observable.of(new GreetInvitation());
    }
}

export const greetInvitationRoute: Routes = [
    {
        path: 'greet-invitation',
        component: GreetInvitationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.greetInvitation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'greet-invitation/:id/view',
        component: GreetInvitationDetailComponent,
        resolve: {
            greetInvitation: GreetInvitationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.greetInvitation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'greet-invitation/new',
        component: GreetInvitationUpdateComponent,
        resolve: {
            greetInvitation: GreetInvitationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.greetInvitation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'greet-invitation/:id/edit',
        component: GreetInvitationUpdateComponent,
        resolve: {
            greetInvitation: GreetInvitationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.greetInvitation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const greetInvitationPopupRoute: Routes = [
    {
        path: 'greet-invitation/:id/delete',
        component: GreetInvitationDeletePopupComponent,
        resolve: {
            greetInvitation: GreetInvitationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'greetappApp.greetInvitation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

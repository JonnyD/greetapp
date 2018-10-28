import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GreetappSharedModule } from 'app/shared';
import { GreetappAdminModule } from 'app/admin/admin.module';
import {
    GreetInvitationComponent,
    GreetInvitationDetailComponent,
    GreetInvitationUpdateComponent,
    GreetInvitationDeletePopupComponent,
    GreetInvitationDeleteDialogComponent,
    greetInvitationRoute,
    greetInvitationPopupRoute
} from './';

const ENTITY_STATES = [...greetInvitationRoute, ...greetInvitationPopupRoute];

@NgModule({
    imports: [GreetappSharedModule, GreetappAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GreetInvitationComponent,
        GreetInvitationDetailComponent,
        GreetInvitationUpdateComponent,
        GreetInvitationDeleteDialogComponent,
        GreetInvitationDeletePopupComponent
    ],
    entryComponents: [
        GreetInvitationComponent,
        GreetInvitationUpdateComponent,
        GreetInvitationDeleteDialogComponent,
        GreetInvitationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GreetappGreetInvitationModule {}

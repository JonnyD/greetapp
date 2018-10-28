import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GreetappSharedModule } from 'app/shared';
import { GreetappAdminModule } from 'app/admin/admin.module';
import {
    GangUserComponent,
    GangUserDetailComponent,
    GangUserUpdateComponent,
    GangUserDeletePopupComponent,
    GangUserDeleteDialogComponent,
    gangUserRoute,
    gangUserPopupRoute
} from './';

const ENTITY_STATES = [...gangUserRoute, ...gangUserPopupRoute];

@NgModule({
    imports: [GreetappSharedModule, GreetappAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GangUserComponent,
        GangUserDetailComponent,
        GangUserUpdateComponent,
        GangUserDeleteDialogComponent,
        GangUserDeletePopupComponent
    ],
    entryComponents: [GangUserComponent, GangUserUpdateComponent, GangUserDeleteDialogComponent, GangUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GreetappGangUserModule {}

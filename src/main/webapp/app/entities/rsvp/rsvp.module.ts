import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GreetappSharedModule } from 'app/shared';
import { GreetappAdminModule } from 'app/admin/admin.module';
import {
    RSVPComponent,
    RSVPDetailComponent,
    RSVPUpdateComponent,
    RSVPDeletePopupComponent,
    RSVPDeleteDialogComponent,
    rSVPRoute,
    rSVPPopupRoute
} from './';

const ENTITY_STATES = [...rSVPRoute, ...rSVPPopupRoute];

@NgModule({
    imports: [GreetappSharedModule, GreetappAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RSVPComponent, RSVPDetailComponent, RSVPUpdateComponent, RSVPDeleteDialogComponent, RSVPDeletePopupComponent],
    entryComponents: [RSVPComponent, RSVPUpdateComponent, RSVPDeleteDialogComponent, RSVPDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GreetappRSVPModule {}

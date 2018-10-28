import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GreetappSharedModule } from 'app/shared';
import { GreetappAdminModule } from 'app/admin/admin.module';
import {
    GreetComponent,
    GreetDetailComponent,
    GreetUpdateComponent,
    GreetDeletePopupComponent,
    GreetDeleteDialogComponent,
    greetRoute,
    greetPopupRoute
} from './';

const ENTITY_STATES = [...greetRoute, ...greetPopupRoute];

@NgModule({
    imports: [GreetappSharedModule, GreetappAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GreetComponent, GreetDetailComponent, GreetUpdateComponent, GreetDeleteDialogComponent, GreetDeletePopupComponent],
    entryComponents: [GreetComponent, GreetUpdateComponent, GreetDeleteDialogComponent, GreetDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GreetappGreetModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GreetappSharedModule } from 'app/shared';
import {
    GangComponent,
    GangDetailComponent,
    GangUpdateComponent,
    GangDeletePopupComponent,
    GangDeleteDialogComponent,
    gangRoute,
    gangPopupRoute
} from './';

const ENTITY_STATES = [...gangRoute, ...gangPopupRoute];

@NgModule({
    imports: [GreetappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GangComponent, GangDetailComponent, GangUpdateComponent, GangDeleteDialogComponent, GangDeletePopupComponent],
    entryComponents: [GangComponent, GangUpdateComponent, GangDeleteDialogComponent, GangDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GreetappGangModule {}

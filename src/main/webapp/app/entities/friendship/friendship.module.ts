import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GreetappSharedModule } from 'app/shared';
import { GreetappAdminModule } from 'app/admin/admin.module';
import {
    FriendshipComponent,
    FriendshipDetailComponent,
    FriendshipUpdateComponent,
    FriendshipDeletePopupComponent,
    FriendshipDeleteDialogComponent,
    friendshipRoute,
    friendshipPopupRoute
} from './';

const ENTITY_STATES = [...friendshipRoute, ...friendshipPopupRoute];

@NgModule({
    imports: [GreetappSharedModule, GreetappAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FriendshipComponent,
        FriendshipDetailComponent,
        FriendshipUpdateComponent,
        FriendshipDeleteDialogComponent,
        FriendshipDeletePopupComponent
    ],
    entryComponents: [FriendshipComponent, FriendshipUpdateComponent, FriendshipDeleteDialogComponent, FriendshipDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GreetappFriendshipModule {}

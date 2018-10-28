import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GreetappGangModule } from './gang/gang.module';
import { GreetappGangUserModule } from './gang-user/gang-user.module';
import { GreetappGreetModule } from './greet/greet.module';
import { GreetappRSVPModule } from './rsvp/rsvp.module';
import { GreetappGreetInvitationModule } from './greet-invitation/greet-invitation.module';
import { GreetappActivityModule } from './activity/activity.module';
import { GreetappFriendshipModule } from './friendship/friendship.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GreetappGangModule,
        GreetappGangUserModule,
        GreetappGreetModule,
        GreetappRSVPModule,
        GreetappGreetInvitationModule,
        GreetappActivityModule,
        GreetappFriendshipModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GreetappEntityModule {}

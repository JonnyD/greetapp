import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGreetInvitation } from 'app/shared/model/greet-invitation.model';

@Component({
    selector: 'jhi-greet-invitation-detail',
    templateUrl: './greet-invitation-detail.component.html'
})
export class GreetInvitationDetailComponent implements OnInit {
    greetInvitation: IGreetInvitation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ greetInvitation }) => {
            this.greetInvitation = greetInvitation;
        });
    }

    previousState() {
        window.history.back();
    }
}

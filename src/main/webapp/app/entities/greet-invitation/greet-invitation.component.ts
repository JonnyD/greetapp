import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGreetInvitation } from 'app/shared/model/greet-invitation.model';
import { Principal } from 'app/core';
import { GreetInvitationService } from './greet-invitation.service';

@Component({
    selector: 'jhi-greet-invitation',
    templateUrl: './greet-invitation.component.html'
})
export class GreetInvitationComponent implements OnInit, OnDestroy {
    greetInvitations: IGreetInvitation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private greetInvitationService: GreetInvitationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.greetInvitationService.query().subscribe(
            (res: HttpResponse<IGreetInvitation[]>) => {
                this.greetInvitations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGreetInvitations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGreetInvitation) {
        return item.id;
    }

    registerChangeInGreetInvitations() {
        this.eventSubscriber = this.eventManager.subscribe('greetInvitationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

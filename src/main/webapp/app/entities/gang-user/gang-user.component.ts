import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGangUser } from 'app/shared/model/gang-user.model';
import { Principal } from 'app/core';
import { GangUserService } from './gang-user.service';

@Component({
    selector: 'jhi-gang-user',
    templateUrl: './gang-user.component.html'
})
export class GangUserComponent implements OnInit, OnDestroy {
    gangUsers: IGangUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private gangUserService: GangUserService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.gangUserService.query().subscribe(
            (res: HttpResponse<IGangUser[]>) => {
                this.gangUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGangUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGangUser) {
        return item.id;
    }

    registerChangeInGangUsers() {
        this.eventSubscriber = this.eventManager.subscribe('gangUserListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

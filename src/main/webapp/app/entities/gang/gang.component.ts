import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGang } from 'app/shared/model/gang.model';
import { Principal } from 'app/core';
import { GangService } from './gang.service';

@Component({
    selector: 'jhi-gang',
    templateUrl: './gang.component.html'
})
export class GangComponent implements OnInit, OnDestroy {
    gangs: IGang[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private gangService: GangService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.gangService.query().subscribe(
            (res: HttpResponse<IGang[]>) => {
                this.gangs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGangs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGang) {
        return item.id;
    }

    registerChangeInGangs() {
        this.eventSubscriber = this.eventManager.subscribe('gangListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

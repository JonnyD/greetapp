import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRSVP } from 'app/shared/model/rsvp.model';
import { Principal } from 'app/core';
import { RSVPService } from './rsvp.service';

@Component({
    selector: 'jhi-rsvp',
    templateUrl: './rsvp.component.html'
})
export class RSVPComponent implements OnInit, OnDestroy {
    rSVPS: IRSVP[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rSVPService: RSVPService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rSVPService.query().subscribe(
            (res: HttpResponse<IRSVP[]>) => {
                this.rSVPS = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRSVPS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRSVP) {
        return item.id;
    }

    registerChangeInRSVPS() {
        this.eventSubscriber = this.eventManager.subscribe('rSVPListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGreet } from 'app/shared/model/greet.model';
import { Principal } from 'app/core';
import { GreetService } from './greet.service';

@Component({
    selector: 'jhi-greet',
    templateUrl: './greet.component.html'
})
export class GreetComponent implements OnInit, OnDestroy {
    greets: IGreet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private greetService: GreetService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.greetService.query().subscribe(
            (res: HttpResponse<IGreet[]>) => {
                this.greets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGreets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGreet) {
        return item.id;
    }

    registerChangeInGreets() {
        this.eventSubscriber = this.eventManager.subscribe('greetListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

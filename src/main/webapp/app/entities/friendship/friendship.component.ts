import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFriendship } from 'app/shared/model/friendship.model';
import { Principal } from 'app/core';
import { FriendshipService } from './friendship.service';

@Component({
    selector: 'jhi-friendship',
    templateUrl: './friendship.component.html'
})
export class FriendshipComponent implements OnInit, OnDestroy {
    friendships: IFriendship[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private friendshipService: FriendshipService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.friendshipService.query().subscribe(
            (res: HttpResponse<IFriendship[]>) => {
                this.friendships = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFriendships();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFriendship) {
        return item.id;
    }

    registerChangeInFriendships() {
        this.eventSubscriber = this.eventManager.subscribe('friendshipListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

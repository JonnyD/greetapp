import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFriendship } from 'app/shared/model/friendship.model';
import { FriendshipService } from './friendship.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-friendship-update',
    templateUrl: './friendship-update.component.html'
})
export class FriendshipUpdateComponent implements OnInit {
    private _friendship: IFriendship;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private friendshipService: FriendshipService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ friendship }) => {
            this.friendship = friendship;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.friendship.id !== undefined) {
            this.subscribeToSaveResponse(this.friendshipService.update(this.friendship));
        } else {
            this.subscribeToSaveResponse(this.friendshipService.create(this.friendship));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFriendship>>) {
        result.subscribe((res: HttpResponse<IFriendship>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get friendship() {
        return this._friendship;
    }

    set friendship(friendship: IFriendship) {
        this._friendship = friendship;
    }
}

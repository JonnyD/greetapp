import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGangUser } from 'app/shared/model/gang-user.model';
import { GangUserService } from './gang-user.service';
import { IGang } from 'app/shared/model/gang.model';
import { GangService } from 'app/entities/gang';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-gang-user-update',
    templateUrl: './gang-user-update.component.html'
})
export class GangUserUpdateComponent implements OnInit {
    private _gangUser: IGangUser;
    isSaving: boolean;

    gangs: IGang[];

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private gangUserService: GangUserService,
        private gangService: GangService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ gangUser }) => {
            this.gangUser = gangUser;
        });
        this.gangService.query().subscribe(
            (res: HttpResponse<IGang[]>) => {
                this.gangs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.gangUser.id !== undefined) {
            this.subscribeToSaveResponse(this.gangUserService.update(this.gangUser));
        } else {
            this.subscribeToSaveResponse(this.gangUserService.create(this.gangUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGangUser>>) {
        result.subscribe((res: HttpResponse<IGangUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGangById(index: number, item: IGang) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get gangUser() {
        return this._gangUser;
    }

    set gangUser(gangUser: IGangUser) {
        this._gangUser = gangUser;
    }
}

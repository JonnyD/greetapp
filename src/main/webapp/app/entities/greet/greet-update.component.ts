import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGreet } from 'app/shared/model/greet.model';
import { GreetService } from './greet.service';
import { IUser, UserService } from 'app/core';
import { IGang } from 'app/shared/model/gang.model';
import { GangService } from 'app/entities/gang';

@Component({
    selector: 'jhi-greet-update',
    templateUrl: './greet-update.component.html'
})
export class GreetUpdateComponent implements OnInit {
    private _greet: IGreet;
    isSaving: boolean;

    users: IUser[];

    gangs: IGang[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private greetService: GreetService,
        private userService: UserService,
        private gangService: GangService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ greet }) => {
            this.greet = greet;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.gangService.query().subscribe(
            (res: HttpResponse<IGang[]>) => {
                this.gangs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.greet.id !== undefined) {
            this.subscribeToSaveResponse(this.greetService.update(this.greet));
        } else {
            this.subscribeToSaveResponse(this.greetService.create(this.greet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGreet>>) {
        result.subscribe((res: HttpResponse<IGreet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGangById(index: number, item: IGang) {
        return item.id;
    }
    get greet() {
        return this._greet;
    }

    set greet(greet: IGreet) {
        this._greet = greet;
    }
}

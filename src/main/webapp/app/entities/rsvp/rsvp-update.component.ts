import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRSVP } from 'app/shared/model/rsvp.model';
import { RSVPService } from './rsvp.service';
import { IGreet } from 'app/shared/model/greet.model';
import { GreetService } from 'app/entities/greet';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-rsvp-update',
    templateUrl: './rsvp-update.component.html'
})
export class RSVPUpdateComponent implements OnInit {
    private _rSVP: IRSVP;
    isSaving: boolean;

    greets: IGreet[];

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private rSVPService: RSVPService,
        private greetService: GreetService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rSVP }) => {
            this.rSVP = rSVP;
        });
        this.greetService.query().subscribe(
            (res: HttpResponse<IGreet[]>) => {
                this.greets = res.body;
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
        if (this.rSVP.id !== undefined) {
            this.subscribeToSaveResponse(this.rSVPService.update(this.rSVP));
        } else {
            this.subscribeToSaveResponse(this.rSVPService.create(this.rSVP));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRSVP>>) {
        result.subscribe((res: HttpResponse<IRSVP>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGreetById(index: number, item: IGreet) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get rSVP() {
        return this._rSVP;
    }

    set rSVP(rSVP: IRSVP) {
        this._rSVP = rSVP;
    }
}

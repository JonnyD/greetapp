import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGreetInvitation } from 'app/shared/model/greet-invitation.model';
import { GreetInvitationService } from './greet-invitation.service';
import { IGreet } from 'app/shared/model/greet.model';
import { GreetService } from 'app/entities/greet';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-greet-invitation-update',
    templateUrl: './greet-invitation-update.component.html'
})
export class GreetInvitationUpdateComponent implements OnInit {
    private _greetInvitation: IGreetInvitation;
    isSaving: boolean;

    greets: IGreet[];

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private greetInvitationService: GreetInvitationService,
        private greetService: GreetService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ greetInvitation }) => {
            this.greetInvitation = greetInvitation;
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
        if (this.greetInvitation.id !== undefined) {
            this.subscribeToSaveResponse(this.greetInvitationService.update(this.greetInvitation));
        } else {
            this.subscribeToSaveResponse(this.greetInvitationService.create(this.greetInvitation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGreetInvitation>>) {
        result.subscribe((res: HttpResponse<IGreetInvitation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get greetInvitation() {
        return this._greetInvitation;
    }

    set greetInvitation(greetInvitation: IGreetInvitation) {
        this._greetInvitation = greetInvitation;
    }
}

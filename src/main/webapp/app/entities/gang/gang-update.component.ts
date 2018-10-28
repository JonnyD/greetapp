import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGang } from 'app/shared/model/gang.model';
import { GangService } from './gang.service';

@Component({
    selector: 'jhi-gang-update',
    templateUrl: './gang-update.component.html'
})
export class GangUpdateComponent implements OnInit {
    private _gang: IGang;
    isSaving: boolean;

    constructor(private gangService: GangService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ gang }) => {
            this.gang = gang;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.gang.id !== undefined) {
            this.subscribeToSaveResponse(this.gangService.update(this.gang));
        } else {
            this.subscribeToSaveResponse(this.gangService.create(this.gang));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGang>>) {
        result.subscribe((res: HttpResponse<IGang>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get gang() {
        return this._gang;
    }

    set gang(gang: IGang) {
        this._gang = gang;
    }
}

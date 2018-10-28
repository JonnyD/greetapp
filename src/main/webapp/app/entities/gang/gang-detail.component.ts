import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGang } from 'app/shared/model/gang.model';

@Component({
    selector: 'jhi-gang-detail',
    templateUrl: './gang-detail.component.html'
})
export class GangDetailComponent implements OnInit {
    gang: IGang;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gang }) => {
            this.gang = gang;
        });
    }

    previousState() {
        window.history.back();
    }
}

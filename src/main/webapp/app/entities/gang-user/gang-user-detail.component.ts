import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGangUser } from 'app/shared/model/gang-user.model';

@Component({
    selector: 'jhi-gang-user-detail',
    templateUrl: './gang-user-detail.component.html'
})
export class GangUserDetailComponent implements OnInit {
    gangUser: IGangUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gangUser }) => {
            this.gangUser = gangUser;
        });
    }

    previousState() {
        window.history.back();
    }
}

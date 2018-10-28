import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRSVP } from 'app/shared/model/rsvp.model';

@Component({
    selector: 'jhi-rsvp-detail',
    templateUrl: './rsvp-detail.component.html'
})
export class RSVPDetailComponent implements OnInit {
    rSVP: IRSVP;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rSVP }) => {
            this.rSVP = rSVP;
        });
    }

    previousState() {
        window.history.back();
    }
}

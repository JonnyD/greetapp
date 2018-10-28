import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGreet } from 'app/shared/model/greet.model';

@Component({
    selector: 'jhi-greet-detail',
    templateUrl: './greet-detail.component.html'
})
export class GreetDetailComponent implements OnInit {
    greet: IGreet;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ greet }) => {
            this.greet = greet;
        });
    }

    previousState() {
        window.history.back();
    }
}

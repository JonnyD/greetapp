import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFriendship } from 'app/shared/model/friendship.model';

@Component({
    selector: 'jhi-friendship-detail',
    templateUrl: './friendship-detail.component.html'
})
export class FriendshipDetailComponent implements OnInit {
    friendship: IFriendship;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ friendship }) => {
            this.friendship = friendship;
        });
    }

    previousState() {
        window.history.back();
    }
}

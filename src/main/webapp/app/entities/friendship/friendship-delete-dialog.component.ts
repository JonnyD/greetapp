import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFriendship } from 'app/shared/model/friendship.model';
import { FriendshipService } from './friendship.service';

@Component({
    selector: 'jhi-friendship-delete-dialog',
    templateUrl: './friendship-delete-dialog.component.html'
})
export class FriendshipDeleteDialogComponent {
    friendship: IFriendship;

    constructor(private friendshipService: FriendshipService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.friendshipService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'friendshipListModification',
                content: 'Deleted an friendship'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-friendship-delete-popup',
    template: ''
})
export class FriendshipDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ friendship }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FriendshipDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.friendship = friendship;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

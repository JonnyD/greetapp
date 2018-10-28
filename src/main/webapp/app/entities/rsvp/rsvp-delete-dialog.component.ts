import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRSVP } from 'app/shared/model/rsvp.model';
import { RSVPService } from './rsvp.service';

@Component({
    selector: 'jhi-rsvp-delete-dialog',
    templateUrl: './rsvp-delete-dialog.component.html'
})
export class RSVPDeleteDialogComponent {
    rSVP: IRSVP;

    constructor(private rSVPService: RSVPService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rSVPService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rSVPListModification',
                content: 'Deleted an rSVP'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rsvp-delete-popup',
    template: ''
})
export class RSVPDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rSVP }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RSVPDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.rSVP = rSVP;
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

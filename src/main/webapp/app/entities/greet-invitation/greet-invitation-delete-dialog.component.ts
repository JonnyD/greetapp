import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGreetInvitation } from 'app/shared/model/greet-invitation.model';
import { GreetInvitationService } from './greet-invitation.service';

@Component({
    selector: 'jhi-greet-invitation-delete-dialog',
    templateUrl: './greet-invitation-delete-dialog.component.html'
})
export class GreetInvitationDeleteDialogComponent {
    greetInvitation: IGreetInvitation;

    constructor(
        private greetInvitationService: GreetInvitationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.greetInvitationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'greetInvitationListModification',
                content: 'Deleted an greetInvitation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-greet-invitation-delete-popup',
    template: ''
})
export class GreetInvitationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ greetInvitation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GreetInvitationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.greetInvitation = greetInvitation;
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

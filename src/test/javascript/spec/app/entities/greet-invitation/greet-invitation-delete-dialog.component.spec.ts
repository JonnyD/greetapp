/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GreetappTestModule } from '../../../test.module';
import { GreetInvitationDeleteDialogComponent } from 'app/entities/greet-invitation/greet-invitation-delete-dialog.component';
import { GreetInvitationService } from 'app/entities/greet-invitation/greet-invitation.service';

describe('Component Tests', () => {
    describe('GreetInvitation Management Delete Component', () => {
        let comp: GreetInvitationDeleteDialogComponent;
        let fixture: ComponentFixture<GreetInvitationDeleteDialogComponent>;
        let service: GreetInvitationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GreetInvitationDeleteDialogComponent]
            })
                .overrideTemplate(GreetInvitationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GreetInvitationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GreetInvitationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});

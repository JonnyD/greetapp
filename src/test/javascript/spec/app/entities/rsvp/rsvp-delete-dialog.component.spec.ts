/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GreetappTestModule } from '../../../test.module';
import { RSVPDeleteDialogComponent } from 'app/entities/rsvp/rsvp-delete-dialog.component';
import { RSVPService } from 'app/entities/rsvp/rsvp.service';

describe('Component Tests', () => {
    describe('RSVP Management Delete Component', () => {
        let comp: RSVPDeleteDialogComponent;
        let fixture: ComponentFixture<RSVPDeleteDialogComponent>;
        let service: RSVPService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [RSVPDeleteDialogComponent]
            })
                .overrideTemplate(RSVPDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RSVPDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RSVPService);
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

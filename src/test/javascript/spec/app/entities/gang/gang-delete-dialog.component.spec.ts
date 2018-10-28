/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GreetappTestModule } from '../../../test.module';
import { GangDeleteDialogComponent } from 'app/entities/gang/gang-delete-dialog.component';
import { GangService } from 'app/entities/gang/gang.service';

describe('Component Tests', () => {
    describe('Gang Management Delete Component', () => {
        let comp: GangDeleteDialogComponent;
        let fixture: ComponentFixture<GangDeleteDialogComponent>;
        let service: GangService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GangDeleteDialogComponent]
            })
                .overrideTemplate(GangDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GangDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GangService);
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

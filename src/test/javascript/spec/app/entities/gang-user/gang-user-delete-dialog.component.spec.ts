/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GreetappTestModule } from '../../../test.module';
import { GangUserDeleteDialogComponent } from 'app/entities/gang-user/gang-user-delete-dialog.component';
import { GangUserService } from 'app/entities/gang-user/gang-user.service';

describe('Component Tests', () => {
    describe('GangUser Management Delete Component', () => {
        let comp: GangUserDeleteDialogComponent;
        let fixture: ComponentFixture<GangUserDeleteDialogComponent>;
        let service: GangUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GangUserDeleteDialogComponent]
            })
                .overrideTemplate(GangUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GangUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GangUserService);
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

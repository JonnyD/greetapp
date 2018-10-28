/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GreetappTestModule } from '../../../test.module';
import { FriendshipDeleteDialogComponent } from 'app/entities/friendship/friendship-delete-dialog.component';
import { FriendshipService } from 'app/entities/friendship/friendship.service';

describe('Component Tests', () => {
    describe('Friendship Management Delete Component', () => {
        let comp: FriendshipDeleteDialogComponent;
        let fixture: ComponentFixture<FriendshipDeleteDialogComponent>;
        let service: FriendshipService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [FriendshipDeleteDialogComponent]
            })
                .overrideTemplate(FriendshipDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FriendshipDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FriendshipService);
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

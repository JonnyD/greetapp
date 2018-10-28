/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { FriendshipUpdateComponent } from 'app/entities/friendship/friendship-update.component';
import { FriendshipService } from 'app/entities/friendship/friendship.service';
import { Friendship } from 'app/shared/model/friendship.model';

describe('Component Tests', () => {
    describe('Friendship Management Update Component', () => {
        let comp: FriendshipUpdateComponent;
        let fixture: ComponentFixture<FriendshipUpdateComponent>;
        let service: FriendshipService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [FriendshipUpdateComponent]
            })
                .overrideTemplate(FriendshipUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FriendshipUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FriendshipService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Friendship(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.friendship = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Friendship();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.friendship = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

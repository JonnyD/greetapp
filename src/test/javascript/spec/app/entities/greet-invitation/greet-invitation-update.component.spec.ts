/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { GreetInvitationUpdateComponent } from 'app/entities/greet-invitation/greet-invitation-update.component';
import { GreetInvitationService } from 'app/entities/greet-invitation/greet-invitation.service';
import { GreetInvitation } from 'app/shared/model/greet-invitation.model';

describe('Component Tests', () => {
    describe('GreetInvitation Management Update Component', () => {
        let comp: GreetInvitationUpdateComponent;
        let fixture: ComponentFixture<GreetInvitationUpdateComponent>;
        let service: GreetInvitationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GreetInvitationUpdateComponent]
            })
                .overrideTemplate(GreetInvitationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GreetInvitationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GreetInvitationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GreetInvitation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.greetInvitation = entity;
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
                    const entity = new GreetInvitation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.greetInvitation = entity;
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

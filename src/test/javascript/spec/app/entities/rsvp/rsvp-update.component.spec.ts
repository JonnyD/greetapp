/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { RSVPUpdateComponent } from 'app/entities/rsvp/rsvp-update.component';
import { RSVPService } from 'app/entities/rsvp/rsvp.service';
import { RSVP } from 'app/shared/model/rsvp.model';

describe('Component Tests', () => {
    describe('RSVP Management Update Component', () => {
        let comp: RSVPUpdateComponent;
        let fixture: ComponentFixture<RSVPUpdateComponent>;
        let service: RSVPService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [RSVPUpdateComponent]
            })
                .overrideTemplate(RSVPUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RSVPUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RSVPService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RSVP(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rSVP = entity;
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
                    const entity = new RSVP();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rSVP = entity;
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

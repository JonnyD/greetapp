/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { GreetUpdateComponent } from 'app/entities/greet/greet-update.component';
import { GreetService } from 'app/entities/greet/greet.service';
import { Greet } from 'app/shared/model/greet.model';

describe('Component Tests', () => {
    describe('Greet Management Update Component', () => {
        let comp: GreetUpdateComponent;
        let fixture: ComponentFixture<GreetUpdateComponent>;
        let service: GreetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GreetUpdateComponent]
            })
                .overrideTemplate(GreetUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GreetUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GreetService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Greet(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.greet = entity;
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
                    const entity = new Greet();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.greet = entity;
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
